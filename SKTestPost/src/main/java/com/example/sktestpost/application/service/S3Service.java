package com.example.sktestpost.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sktestpost.application.port.out.S3Port;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Port s3Port;

	public String uploadImage(MultipartFile file) {
		return s3Port.upload(file);
	}

	public void deletePostFile(String fileUrl) {
		s3Port.deleteFile(fileUrl);
	}
}
