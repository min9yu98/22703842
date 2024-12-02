package com.example.sktestpost.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface S3Port {
	String upload(MultipartFile file);

	void deleteFile(String fileUrl);
}
