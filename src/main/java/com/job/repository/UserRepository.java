package com.job.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.job.entity.JobApplication.Status;
import com.job.entity.JobApplication;
import com.job.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional <User> findByEmailAndPassword(String email,String password);
	
	 Optional<User> findByApplicationsId(Integer applicationId);
	//Optional<User> findByJobApplication(JobApplication application);

	
}
