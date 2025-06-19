package com.job.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.job.entity.JobApplication;
import com.job.entity.User;
import com.job.repository.UserRepository;
import com.job.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Override
	public User save(User user) {
	
		return userRepository.save(user);
	}
	@Override
	public Optional<User> findByEmailAndPassword(String email,String password) {
		return userRepository.findByEmailAndPassword(email,password);
	}
	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public long countAllUsers() {
		
		return userRepository.count();
	}
	@Override
	public Optional<User> findByJobApplicationId(Integer id) {
		return userRepository.findByApplicationsId(id);
	}

}
