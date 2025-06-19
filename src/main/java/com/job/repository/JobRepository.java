package com.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Job;
import com.job.entity.User;
import com.job.entity.JobApplication.Status;

public interface JobRepository  extends JpaRepository<Job, Long>{
	
	List<Job> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndLocationAndTypeAndCategory(
		    String titleSearch,
		    String descSearch,
		    String location,
		    String type,
		    String category
		);

	Long countByStatus(String string);

	

}
