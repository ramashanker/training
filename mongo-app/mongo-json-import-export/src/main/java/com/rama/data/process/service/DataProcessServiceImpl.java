package com.rama.data.process.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.bson.Document;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.rama.data.process.exception.DataProcessException;
import com.rama.data.process.response.DataDownloadResponse;
import com.rama.data.process.response.DataUploadResponse;

@Service
public class DataProcessServiceImpl implements DataProcessService {

	private MongoTemplate mongoTemplate;

	public DataProcessServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public DataDownloadResponse downloadProcess(String path, String dbname) throws DataProcessException {
		DataDownloadResponse downloadResponse = new DataDownloadResponse();
		Map<Object, Object> fileEntry = new HashMap<>();
		int entryCount;
		int collectionCount = 0;
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry());
		final DocumentCodec codec = new DocumentCodec(codecRegistry, new BsonTypeClassMap());
		Set<String> collections = mongoTemplate.getCollectionNames();
		for (String collectionName : collections) {
			entryCount = 0;
			collectionCount++;
			String filepath = path + "\\" + collectionName + ".json";
			MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
				for (Document doc : collection.find()) {
					writer.write(doc.toJson(codec));
					writer.newLine();
					entryCount++;
				}
				fileEntry.put(collectionName, entryCount);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		downloadResponse.setFileEntry(fileEntry);
		downloadResponse.setCollectionCount(collectionCount);
		return downloadResponse;
	}

	@Override
	public DataUploadResponse uploadProcess(String path, String dbname) throws DataProcessException {
		DataUploadResponse uploadResponse = new DataUploadResponse();
		List<String> exception = new ArrayList<>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		int filecount = 0;

		for (File file : listOfFiles) {
			if (file.isFile()) {
				String fileNameWithoutExtension = FilenameUtils.removeExtension(file.getName());
				MongoCollection<Document> collection = mongoTemplate.getCollection(fileNameWithoutExtension);
				int countDocument = 0;
				try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
					String json;
					while ((json = reader.readLine()) != null) {
						collection.insertOne(Document.parse(json));
						countDocument++;
					}
					if (countDocument == 0) {
						mongoTemplate.createCollection(fileNameWithoutExtension);
					}
					filecount++;
				} catch (Exception ex) {
					exception.add(ex.getMessage());
					ex.printStackTrace();

				}
			}
		}
		uploadResponse.setExceptionMessage(exception);
		uploadResponse.setFilecount(filecount);
		return uploadResponse;

	}
}
