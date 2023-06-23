package com.rih.utility;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rih.entity.FileDB;
import com.rih.repository.FileDBRepository;

@Service
public class FileStorageService {
	
	private FileDBRepository fileDBRepository;

	public FileStorageService(FileDBRepository fileDBRepository) {
		super();
		this.fileDBRepository = fileDBRepository;
	}
	
	public FileDB store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		return fileDBRepository.save(fileDB);
	}
	
	  public FileDB getFile(Long id) {
		    return fileDBRepository.findById(id).get();
		  }

}
