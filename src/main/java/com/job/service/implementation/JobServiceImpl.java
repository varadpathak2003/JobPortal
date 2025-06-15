package com.job.service.implementation;

import java.util.*;

import org.springframework.stereotype.Component;

import com.job.entity.Job;
import com.job.repository.JobRepository;
import com.job.service.JobService;

 

@Component
public class JobServiceImpl implements JobService{
 
	JobRepository jobRepository;
	List <Job> allJobs;
	
	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository=jobRepository;
		this.allJobs=jobRepository.findAll();
	}
	
	@Override
	public List<String> findDistinctLocations() {
		List <String > locations=new ArrayList<>();
		
		for (Job j : allJobs) {
			locations.add(j.getLocation());
		}
		return locations;
	}

	@Override
	public List<String> findDistinctCategories() {
		List <String > categories=new ArrayList<>();	
		for (Job j : allJobs) {
			categories.add(j.getCategory());
		}
		return categories;
	}

	@Override
	public List<Job> findFilteredJobs(String search, String location, String type, String category) {
		List <Job> jobs=jobRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndLocationAndTypeAndCategory(search, search, location, type, category);
		return jobs;
	}

	@Override
	public List<Job> findFeaturedJobs(int i) {
		
		return null;
	}

	@Override
	public Job save(Job job) {
		return this.jobRepository.save(job);
	}
	
	@Override
	public Optional<Job> findById(Long id) {
		
		return jobRepository.findById(id);
	}

	@Override
	public void update(Job job) {
		Optional<Job> opt=jobRepository.findById(job.getId());
		Job j=opt.get();
		j.setApplications(job.getApplications());
		j.setCategory(job.getCategory());
		j.setCompany(job.getCompany());
		j.setDeadline(job.getDeadline());
		j.setDescription(job.getDescription());
		j.setLocation(job.getLocation());
		j.setSalary(job.getSalary());
		j.setStatus(job.getStatus());
		j.setTitle(job.getTitle());
		j.setType(job.getType());
		jobRepository.save(j);
		
	}

	@Override
	public void delete(Job job) {
		jobRepository.delete(job);
		
	}

	@Override
	public List<Job> findAll() {
		return jobRepository.findAll();
	}

	@Override
	public Map<String, Long> getJobStatistics() {
	    Map<String, Long> stats = new HashMap<>();
	    stats.put("total", jobRepository.count());
	    stats.put("active", jobRepository.countByStatus("ACTIVE"));
	    stats.put("expired", jobRepository.countByStatus("EXPIRED"));
	    return stats;
	}

}


