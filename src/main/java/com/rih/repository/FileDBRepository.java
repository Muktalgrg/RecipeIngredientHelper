package com.rih.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rih.entity.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long>{
	
	

}
