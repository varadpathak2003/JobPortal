package com.job.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.job.entity.JobApplication;
import com.job.entity.User;

@Component
@Service
public interface UserService {
	public User save(User user);

	public Optional<User> findByEmailAndPassword(String email,String password);

	public Optional<User> findById(Long id);

	public long countAllUsers();

	public  Optional<User>  findByJobApplicationId(Integer id);
}
