package com.job.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.job.entity.Job;
import com.job.entity.JobApplication;
import com.job.entity.JobApplication.Status;
import com.job.entity.User;
import com.job.repository.UserRepository;
import com.job.service.JobApplicationService;
import com.job.service.JobService;
import com.job.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
	
	UserService userService;
	JobService jobService;
	JobApplicationService applicationService;
	
	UserController(UserService userService,JobService jobService,JobApplicationService applicationService){
		this.userService=userService;
		this.jobService=jobService;
		this.applicationService=applicationService;
	}
	
	@RequestMapping("register")
	public String register(Model m) {
		m.addAttribute("user",new User());
		return "user/register";
	}
	
	@RequestMapping("login")
	public String login(Model m) {
		return "user/login";
	}
	
	@PostMapping("/loginuser")
	public String loginUser(@RequestParam String email,
	                        @RequestParam String password,
	                        HttpSession session,
	                        Model model) {

	    Optional<User> userOpt = userService.findByEmailAndPassword(email, password);

	    if (userOpt.isPresent()) {
	        User user = userOpt.get();
	        session.setAttribute("userId", user.getId());
	        session.setAttribute("userName", user.getName());
	        return "redirect:/jobs";  // redirect to the jobs page after successful login
	    } else {
	        model.addAttribute("message", "Invalid email or password.");
	        model.addAttribute("messageClass", "alert-danger");
	        model.addAttribute("email", email);  // to retain entered email
	        return "login";
	    }
	}
	
	@GetMapping("/apply/{id}")
	public String showApplicationForm(@PathVariable Long id, Model model, HttpSession session) {
	    Job job = jobService.findById(id).get();
	    JobApplication application = new JobApplication();
	    application.setJob(job);
	    
	    model.addAttribute("job", job);  // Add job separately
	    model.addAttribute("application", application);
	    
	    return "user/apply";
	}
	
	@PostMapping("/apply/submit")
	public String submitApplication(@ModelAttribute("application") JobApplication application,
	                                @RequestParam("resume") MultipartFile resume,
	                                HttpSession session,
	                                RedirectAttributes redirectAttributes,
	                                Model model) {

		Long userId = ((Number) session.getAttribute("userId")).longValue();
	    if (userId == null) {
	        return "redirect:/login";
	    }
	    User user=userService.findById(userId).get();
	    // Get Job
	    Job job = jobService.findById(application.getJob().getId()).get();
	    if (job == null) {
	        redirectAttributes.addFlashAttribute("message", "Invalid job selected.");
	        return "redirect:/jobs";
	    }

	    if (resume.isEmpty()) {
	        model.addAttribute("message", "Please upload your resume.");
	        model.addAttribute("application", application);
	        return "user/apply";
	    }

	    String originalFilename = resume.getOriginalFilename();
	    String ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
	    List<String> allowed = List.of("pdf", "doc", "docx");

	    if (!allowed.contains(ext.toLowerCase())) {
	        model.addAttribute("message", "Invalid resume format. Only PDF/DOC/DOCX allowed.");
	        model.addAttribute("application", application);
	        return "user/apply";
	    }

	    // Check for duplicate
	 // Before your file upload logic
	    if (!applicationService.findByUserAndJob(user, job).isEmpty()) {
	        redirectAttributes.addFlashAttribute("message", "You have already applied for this job.");
	        return "redirect:/user/apply/" + job.getId(); // Redirect to job details
	    }



	    // Save file
	    String filename = "resume_" + userId + "_" + job.getId() + "_" + System.currentTimeMillis() + "." + ext;
	    Path uploadDir = Paths.get("uploads", "resumes");
	    try {
	        Files.createDirectories(uploadDir);
	        resume.transferTo(uploadDir.resolve(filename));
	    } catch (IOException e) {
	        model.addAttribute("message", "Failed to upload resume.");
	        model.addAttribute("application", application);
	        return "user/apply";
	    }

	    // Populate and save
	    application.setJob(job);
	    application.setUser(userService.findById(userId).orElseThrow());
	    application.setResumeFilename(filename);
	    application.setStatus(Status.PENDING);

	    applicationService.save(application);

	    redirectAttributes.addFlashAttribute("message", "Application submitted successfully.");
	    return viewApplications(model);
	}

	
	@GetMapping("viewAll")
	public String viewApplications(Model model) {
		List <JobApplication> applications=applicationService.findAll();
		model.addAttribute("applications", applications);
		return "user/viewApplications";
	}

}
