package com.example.sktestpost.infra.adapter.out;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.sktestpost.application.port.out.S3Port;
import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.S3Exception;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Adapter implements S3Port {

	private final AmazonS3 amazonS3;
	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Override
	public String upload(MultipartFile file) {
		// 파일명
		String fileName = createFileName(Objects.requireNonNull(file.getOriginalFilename()));

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(file.getContentType());

		// S3에 업로드
		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, file.getInputStream(), objectMetadata));
		} catch (IOException e) {
			throw new S3Exception("image upload exception", ErrorCode.EXCEPTION_ON_IMAGE_UPLOAD);
		}

		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	public String createFileName(String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(".");
		String fileExtension = originalFileName.substring(fileExtensionIndex);
		String fileName = originalFileName.substring(0, fileExtensionIndex);
		String random = String.valueOf(UUID.randomUUID());

		return fileName + "_" + random + fileExtension;
	}

	@Override
	public void deleteFile(String fileUrl) {
		String[] deleteUrl = fileUrl.split("/", 4);
		amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, deleteUrl[3]));
	}
}
