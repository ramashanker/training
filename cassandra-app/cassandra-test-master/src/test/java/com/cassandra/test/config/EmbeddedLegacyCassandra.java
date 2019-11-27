package com.cassandra.test.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.io.FSWriteError;
import org.apache.cassandra.service.CassandraDaemon;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.CqlOperations;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmbeddedLegacyCassandra {
    public static final long DEFAULT_STARTUP_TIMEOUT = 20000L;
    public static final String DEFAULT_TMP_DIR = "target/embeddedCassandra";
    public static final String DEFAULT_CASSANDRA_YML_FILE = "cu-cassandra.yaml";
    public static final String CASSANDRA_RNDPORT_YML_FILE = "cu-cassandra-rndport.yaml";
    public static final String DEFAULT_LOG4J_CONFIG_FILE = "/log4j-embedded-cassandra.properties";
    private static final String INTERNAL_CASSANDRA_KEYSPACE = "system";
    private static final String INTERNAL_CASSANDRA_AUTH_KEYSPACE = "system_auth";
    private static final String INTERNAL_CASSANDRA_DISTRIBUTED_KEYSPACE = "system_distributed";
    private static final String INTERNAL_CASSANDRA_SCHEMA_KEYSPACE = "system_schema";
    private static final String INTERNAL_CASSANDRA_TRACES_KEYSPACE = "system_traces";
    private static final Set<String> systemKeyspaces = new HashSet(Arrays.asList("system",
                                                                                 "system_auth",
                                                                                 "system_distributed",
                                                                                 "system_schema",
                                                                                 "system_traces"));
    private static Logger log = LoggerFactory.getLogger(org.cassandraunit.utils.EmbeddedCassandraServerHelper.class);
    private static CassandraDaemon cassandraDaemon = null;
    private static String launchedYamlFile;
    private static Cluster cluster;
    private static Session session;

    public EmbeddedLegacyCassandra() {
    }

    public static Predicate<String> nonSystemKeyspaces() {
        return (keyspace) -> {
            return !systemKeyspaces.contains(keyspace);
        };
    }

    public static void startEmbeddedCassandra() throws TTransportException, IOException, InterruptedException, ConfigurationException {
        startEmbeddedCassandra(20000L);
    }

    public static void startEmbeddedCassandra(long timeout) throws TTransportException, ConfigurationException, IOException {
        startEmbeddedCassandra("cu-cassandra.yaml", timeout);
    }

    public static void startEmbeddedCassandra(String yamlFile) throws TTransportException, IOException, ConfigurationException {
        startEmbeddedCassandra(yamlFile, 20000L);
    }

    public static void startEmbeddedCassandra(String yamlFile,
                                              long timeout) throws TTransportException, IOException, ConfigurationException {
        startEmbeddedCassandra(yamlFile, "target/embeddedCassandra", timeout);
    }

    public static void startEmbeddedCassandra(String yamlFile,
                                              String tmpDir) throws TTransportException, IOException, ConfigurationException {
        startEmbeddedCassandra(yamlFile, tmpDir, 20000L);
    }

    public static void startEmbeddedCassandra(String yamlFile,
                                              String tmpDir,
                                              long timeout) throws TTransportException, IOException, ConfigurationException {
        if (cassandraDaemon == null) {
            if (!StringUtils.startsWith(yamlFile, "/")) {
                yamlFile = "/" + yamlFile;
            }

            rmdir(tmpDir);
            File file = copy(yamlFile, tmpDir).toFile();
            readAndAdaptYaml(file);
            startEmbeddedCassandra(file, tmpDir, timeout);
        }
    }

    public static void startEmbeddedCassandra(File file, long timeout) throws TTransportException, IOException, ConfigurationException {
        startEmbeddedCassandra(file, "target/embeddedCassandra", timeout);
    }

    public static void startEmbeddedCassandra(File file, String tmpDir, long timeout) throws IOException, ConfigurationException {
        if (cassandraDaemon == null) {
            checkConfigNameForRestart(file.getAbsolutePath());
            log.debug("Starting cassandra...");
            log.debug("Initialization needed");
            System.setProperty("cassandra.config", "file:" + file.getAbsolutePath());
            System.setProperty("cassandra-foreground", "true");
            System.setProperty("cassandra.native.epoll.enabled", "false");
            System.setProperty("cassandra.unsafesystem", "true");
            if (System.getProperty("log4j.configuration") == null) {
                copy("/log4j-embedded-cassandra.properties", tmpDir);
                System.setProperty("log4j.configuration", "file:" + tmpDir + "/log4j-embedded-cassandra.properties");
            }

            DatabaseDescriptor.daemonInitialization();
            cleanupAndLeaveDirs();
            CountDownLatch startupLatch = new CountDownLatch(1);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                cassandraDaemon = new CassandraDaemon();
                cassandraDaemon.activate();
                startupLatch.countDown();
            });

            try {
                if (!startupLatch.await(timeout, TimeUnit.MILLISECONDS)) {
                    log.error("Cassandra daemon did not start after " + timeout + " ms. Consider increasing the timeout");
                    throw new AssertionError("Cassandra daemon did not start within timeout");
                }

                Runtime.getRuntime()
                       .addShutdownHook(new Thread(() -> {
                           if (session != null) {
                               session.close();
                           }

                           if (cluster != null) {
                               cluster.close();
                           }
                       }));
            } catch (InterruptedException var10) {
                log.error("Interrupted waiting for Cassandra daemon to start:", var10);
                throw new AssertionError(var10);
            } finally {
                executor.shutdown();
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void stopEmbeddedCassandra() {
        log.warn("EmbeddedCassandraServerHelper.stopEmbeddedCassandra() is now deprecated, previous version was not fully operating");
        cassandraDaemon.deactivate();
    }

    public static void cleanEmbeddedCassandra() {
        if (session != null) {
            dropKeyspaces();
        }
    }

    public static void cleanDataEmbeddedCassandra(String keyspace, String... excludedTables) {
        if (session != null) {
            cleanDataWithNativeDriver(keyspace, excludedTables);
        }
    }

    public static Cluster getCluster() {
        initCluster();
        return cluster;
    }

    public static Session getSession() {
        initSession();
        return session;
    }

    public static String getClusterName() {
        return DatabaseDescriptor.getClusterName();
    }

    public static String getHost() {
        return DatabaseDescriptor.getRpcAddress()
                                 .getHostName();
    }

    public static int getRpcPort() {
        return DatabaseDescriptor.getRpcPort();
    }

    public static int getNativeTransportPort() {
        return DatabaseDescriptor.getNativeTransportPort();
    }

    public static void mkdirs() {
        DatabaseDescriptor.createAllDirectories();
    }

    private static void checkConfigNameForRestart(String yamlFile) {
        boolean wasPreviouslyLaunched = launchedYamlFile != null;
        if (wasPreviouslyLaunched && !launchedYamlFile.equals(yamlFile)) {
            throw new UnsupportedOperationException("We can't launch two Cassandra configurations in the same JVM instance");
        } else {
            launchedYamlFile = yamlFile;
        }
    }

    private static synchronized void initCluster() {
        if (cluster == null) {
            QueryOptions queryOptions = new QueryOptions();
            queryOptions.setRefreshSchemaIntervalMillis(0);
            queryOptions.setRefreshNodeIntervalMillis(0);
            queryOptions.setRefreshNodeListIntervalMillis(0);
            cluster = Cluster.builder()
                             .addContactPoints(new String[] { getHost() })
                             .withPort(getNativeTransportPort())
                             .withoutJMXReporting()
                             .withQueryOptions(queryOptions)
                             .build();
        }
    }

    private static synchronized void initSession() {
        if (session == null) {
            initCluster();
            session = cluster.connect();
        }
    }

    private static void cleanDataWithNativeDriver(String keyspace, String... excludedTables) {
        HashSet<String> excludedTableList = new HashSet(Arrays.asList(excludedTables));
        cluster.getMetadata()
               .getKeyspace(keyspace)
               .getTables()
               .stream()
               .map((table) -> {
                   return table.getName();
               })
               .filter((tableName) -> {
                   return !excludedTableList.contains(tableName);
               })
               .map((tableName) -> {
                   return keyspace + "." + tableName;
               })
               .forEach(CqlOperations.truncateTable(session));
    }

    private static void dropKeyspaces() {
        dropKeyspacesWithNativeDriver();
    }

    private static void dropKeyspacesWithNativeDriver() {
        cluster.getMetadata()
               .getKeyspaces()
               .stream()
               .map(KeyspaceMetadata::getName)
               .filter(nonSystemKeyspaces())
               .forEach(CqlOperations.dropKeyspace(session));
    }

    private static void deleteRecursive(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] children = dir.listFiles();
                if (children != null) {
                    File[] var2 = children;
                    int var3 = children.length;

                    for (int var4 = 0; var4 < var3; ++var4) {
                        File child = var2[var4];
                        deleteRecursive(child);
                    }
                }
            }

            try {
                Files.delete(dir.toPath());
            } catch (Throwable var6) {
                throw new FSWriteError(var6, dir);
            }
        }
    }

    private static void rmdir(String dir) {
        deleteRecursive(new File(dir));
    }

    private static Path copy(String resource, String directory) throws IOException {
        mkdir(directory);
        String fileName = resource.substring(resource.lastIndexOf("/") + 1);
        InputStream from = org.cassandraunit.utils.EmbeddedCassandraServerHelper.class.getResourceAsStream(resource);
        Path copyName = Paths.get(directory, fileName);
        Files.copy(from, copyName, new CopyOption[0]);
        return copyName;
    }

    private static void mkdir(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists() && !dirFile.mkdirs()) {
            throw new FSWriteError(new IOException("Failed to mkdirs " + dir), dir);
        }
    }

    private static void cleanupAndLeaveDirs() throws IOException {
        mkdirs();
        cleanup();
        mkdirs();
        final CommitLog commitLog = CommitLog.instance;

        // this is the custom Windows workaround: we catch FSWriteError
        try {
            commitLog.stopUnsafe(true);
        } catch (final FSWriteError ae) {
        }
        commitLog.resetConfiguration();
        commitLog.restartUnsafe();
    }

    private static void cleanup() {
        List<String> directories = new ArrayList(Arrays.asList(DatabaseDescriptor.getAllDataFileLocations()));
        directories.add(DatabaseDescriptor.getCommitLogLocation());
        Iterator var1 = directories.iterator();

        while (var1.hasNext()) {
            String dirName = (String) var1.next();
            File dir = new File(dirName);
            if (!dir.exists()) {
                throw new RuntimeException("No such directory: " + dir.getAbsolutePath());
            }

            rmdir(dirName);
        }
    }

    private static void readAndAdaptYaml(File cassandraConfig) throws IOException {
        String yaml = readYamlFileToString(cassandraConfig);
        Pattern portPattern = Pattern.compile("^([a-z_]+)_port:\\s*([0-9]+)\\s*$", 8);
        Matcher portMatcher = portPattern.matcher(yaml);
        StringBuffer sb = new StringBuffer();

        boolean replaced;
        String replacement;
        for (replaced = false; portMatcher.find(); portMatcher.appendReplacement(sb, replacement)) {
            String portName = portMatcher.group(1);
            int portValue = Integer.parseInt(portMatcher.group(2));
            if (portValue == 0) {
                portValue = findUnusedLocalPort();
                replacement = portName + "_port: " + portValue;
                replaced = true;
            } else {
                replacement = portMatcher.group(0);
            }
        }

        portMatcher.appendTail(sb);
        if (replaced) {
            writeStringToYamlFile(cassandraConfig, sb.toString());
        }
    }

    private static String readYamlFileToString(File yamlFile) throws IOException {
        UnicodeReader reader = new UnicodeReader(new FileInputStream(yamlFile));
        Throwable var2 = null;

        try {
            StringBuilder sb = new StringBuilder();
            char[] cbuf = new char[1024];

            for (int readden = reader.read(cbuf); readden >= 0; readden = reader.read(cbuf)) {
                sb.append(cbuf, 0, readden);
            }

            String var6 = sb.toString();
            return var6;
        } catch (Throwable var15) {
            var2 = var15;
            throw var15;
        } finally {
            if (reader != null) {
                if (var2 != null) {
                    try {
                        reader.close();
                    } catch (Throwable var14) {
                        var2.addSuppressed(var14);
                    }
                } else {
                    reader.close();
                }
            }
        }
    }

    private static void writeStringToYamlFile(File yamlFile, String yaml) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(yamlFile), "utf-8");
        Throwable var3 = null;

        try {
            writer.write(yaml);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (writer != null) {
                if (var3 != null) {
                    try {
                        writer.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    writer.close();
                }
            }
        }
    }

    private static int findUnusedLocalPort() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        Throwable var1 = null;

        int var2;
        try {
            var2 = serverSocket.getLocalPort();
        } catch (Throwable var11) {
            var1 = var11;
            throw var11;
        } finally {
            if (serverSocket != null) {
                if (var1 != null) {
                    try {
                        serverSocket.close();
                    } catch (Throwable var10) {
                        var1.addSuppressed(var10);
                    }
                } else {
                    serverSocket.close();
                }
            }
        }

        return var2;
    }
}
