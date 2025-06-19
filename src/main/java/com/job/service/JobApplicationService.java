package com.job.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.job.entity.Job;
import com.job.entity.JobApplication;
import com.job.entity.JobApplication.Status;
import com.job.entity.User;

@Service
public interface JobApplicationService {
	Optional <JobApplication> findByUserAndJob(User user,Job job);
	JobApplication save(JobApplication jobApplication);
	
	List <JobApplication> findAll();
	Map<String, Long> getApplicationStatistics();
	int countByUserAndStatus(User user, Status pending);
	Optional<JobApplication> findById(Integer id);
}


