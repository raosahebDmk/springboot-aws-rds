package com.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;

@Configuration
public class AppConfig {

	private Gson gson = new Gson(); 
	
	@Value("${cloud.aws.creentials.access-key}")
	private String accesskey;  
	@Value("${cloud.aws.creentials.secret-key}")
	private String secretkey;  
	
	@Bean
	public DataSource datasouce(){
		AwsSecrets secret = getSecret();
		
		return DataSourceBuilder
				.create()
				.url("jdbc:"+secret.getEngine()+"://"+secret.getHost()+":"+secret.getPort()+"/mysql_db")
				.username(secret.getUsername())
				.password(secret.getPassword())
				.build();
	}
	
	
	private AwsSecrets getSecret() {

	    String secretName = "mysql-db-credential";
	    String region = "ap-south-1";

	    AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
	    		.withRegion(region)
	    		.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey)))
	    		.build();
	            
	    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
	    	    
	    GetSecretValueResult getSecretValueResponse;

	    try {
	        getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
	    } catch (Exception e) {
	        throw e;
	    }

	    if(getSecretValueResponse.getSecretString() != null) {
		    String secret = getSecretValueResponse.getSecretString();
		    return gson.fromJson(secret, AwsSecrets.class);
	    }	    
	    return null;	    
	}	
}
