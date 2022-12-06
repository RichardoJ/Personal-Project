package com.example.assignmentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class BlobConfig {

    @Value("DefaultEndpointsProtocol=https;AccountName=studentassignments;AccountKey=J0gUTPRS2YQGojhPXVwDIn4IkHup/lpNShO1ByKyLjv0sweUx/T2Qad9Mu/XqAdjk0gyIGM/xEtR+AStOzeC9A==;EndpointSuffix=core.windows.net")
    private String connectionString;

    @Value("studentassignments")
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
