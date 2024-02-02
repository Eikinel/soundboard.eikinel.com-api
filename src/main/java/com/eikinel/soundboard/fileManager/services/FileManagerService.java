package com.eikinel.soundboard.fileManager.services;

import com.eikinel.soundboard.fileManager.exceptions.FileManagerException;
import com.eikinel.soundboard.fileManager.exceptions.FileNotFoundException;
import com.eikinel.soundboard.fileManager.models.FileManagerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileManagerService {
	private final Path fileStorageLocation;

	@Autowired
	public FileManagerService(FileManagerProperties FileManagerProperties) {
		this.fileStorageLocation = Paths.get(FileManagerProperties.getUploadDir())
						.toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileManagerException("Could not create the directory where the uploaded files would be stored.", ex);
		}
	}

	public String store(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileManagerException("Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileManagerException("Could not store file " + fileName, ex);
		}
	}

	public Resource load(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName, ex);
		}
	}
}
