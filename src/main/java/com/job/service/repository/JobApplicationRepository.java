package com.job.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.job.entity.Job;
import com.job.entity.JobApplication;
import com.job.entity.JobApplication.Status;
import com.job.entity.User;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
	
	Optional <JobApplication> findByUserAndJob(User user,Job job);

	Long countByStatus(Status pending);

}
