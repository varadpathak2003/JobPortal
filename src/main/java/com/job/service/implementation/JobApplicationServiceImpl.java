package com.job.service.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.job.entity.Job;
import com.job.entity.JobApplication;
import com.job.entity.JobApplication.Status;
import com.job.entity.User;
import com.job.repository.JobRepository;
import com.job.service.JobApplicationService;
import com.job.service.repository.JobApplicationRepository;

@Component
public class JobApplicationServiceImpl implements JobApplicationService {
	@Autowired
	JobApplicationRepository applicationRepository;

	@Override
	public Optional<JobApplication> findByUserAndJob(User user, Job job) {
		return applicationRepository.findByUserAndJob(user, job) ;
	}

	@Override
	public JobApplication save(JobApplication jobApplication) {
	
		return applicationRepository.save(jobApplication);
	}

	@Override
	public List<JobApplication> findAll() {
		return applicationRepository.findAll();
	}

	@Override
	public Map<String, Long> getApplicationStatistics() {
	    Map<String, Long> stats = new HashMap<>();
	    
	    // Total applications
	    long totalApps = applicationRepository.count();
	    stats.put("total", totalApps);
	    
	    // Count by status
	    stats.put("pending", applicationRepository.countByStatus(Status.PENDING));
	    stats.put("accepted", applicationRepository.countByStatus(Status.ACCEPTED));
	    stats.put("rejected", applicationRepository.countByStatus(Status.REJECTED));
	    
	    return stats;
	}

}
