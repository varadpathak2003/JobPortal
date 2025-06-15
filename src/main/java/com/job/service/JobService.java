package com.job.service;

import java.util.*;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.job.entity.Job;


@Service
@Component
public interface JobService {

	List<String> findDistinctLocations();

	List<String> findDistinctCategories();

	List<Job> findFilteredJobs(String search, String location, String type, String category);

	List<Job> findFeaturedJobs(int i);

	Job save(Job job);
	
	Optional<Job> findById(Long id);

	void update(Job job);

	void delete(Job job);

	List<Job> findAll();

	Map<String, Long> getJobStatistics();

}
