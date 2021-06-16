package com.example.demo;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;


@Component
public class CloudinaryConfig {

private Cloudinary cloudinary;

@Autowired
 public CloudinaryConfig(
	@Value("${cloudinary.apiKey}")	String key, 
	@Value("${cloudinary.apiSecret}")	String secret, 
	@Value("${cloudinary.cloudName}")	String cloud ) {
	 
	cloudinary = Singleton.getCloudinary();
	 cloudinary.config.apiKey=key;
	 cloudinary.config.apiSecret=secret;
	 cloudinary.config.cloudName=cloud;
	 
 }
 
 public Map upload(Object file,Map options) {
	try {
		return cloudinary.uploader().upload(file, options);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	 
 }
 
 public String createUrl(String name, int width, int height,String action){

     return cloudinary.url()
             .transformation(new Transformation()
                     .width(width).height(height)
                     .border("2px_solid_back").crop(action))
             .imageTag(name);

 }
 
}
