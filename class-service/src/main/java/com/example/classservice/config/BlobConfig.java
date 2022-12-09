package com.example.classservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class BlobConfig {
    
    @Value("DefaultEndpointsProtocol=https;AccountName=studentmodules;AccountKey=rsNk5ZNMmgc7PURLXSrutb42BAnlxjY0/S67sSQY0QXZlSQ4LKcOEuyvFL6bOR5PyaaJl+N84bxR+ASt6u3vcg==;EndpointSuffix=core.windows.net")
    private String connectionString;

    @Value("modules")
    private String containerName;

    @Bean
    public BlobServiceClient blobServiceClient() {

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        return blobServiceClient;

    }

    @Bean
    public BlobContainerClient blobContainerClient() {

        BlobContainerClient blobContainerClient = blobServiceClient()
                .getBlobContainerClient(containerName);

        return blobContainerClient;

    }
}
