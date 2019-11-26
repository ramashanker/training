package com.cassandra.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = { "application.properties" })
@EnableCassandraRepositories(basePackages = { "com.casandra.test.repository" })
@EntityScan("com.casandra.test.domain.cassandra")
public class CassandraConfig {

	@Value("${cassandra.username}")
	private String userName;

	@Value("${cassandra.password}")
	private String password;

	@Value("${keyspace}")
	private String keySpace;

	@Value("${contactpoints}")
	private String contactpoints;

	@Value("${port}")
	private String port;

	@Bean
	public CassandraClusterFactoryBean cluster() {

		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(contactpoints);
//		if (userName != null && userName.trim().length() > 0) {
//			cluster.setUsername(userName);
//			cluster.setPassword(password);
//		}

		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() throws ClassNotFoundException {
		BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
		mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), keySpace));
		mappingContext.setInitialEntitySet(
				CassandraEntityClassScanner.scan(new String[] { "com.casandra.test.domain.cassandra" }));
		return mappingContext;
	}

	@Bean
	public CassandraConverter converter() throws ClassNotFoundException {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(keySpace);
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
		return session;
	}

	@Bean
	public CassandraOperations cassandraTemplate() throws Exception {
		return new CassandraTemplate(session().getObject());
	}
}