package com.job.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.job.entity.Job;
import com.job.service.JobApplicationService;
import com.job.service.JobService;
import com.job.service.UserService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	JobService jobService;
	UserService userService;
	JobApplicationService applicationService;
	
	
	AdminController(JobService jobService,UserService userService,JobApplicationService applicationService ){
		this.applicationService=applicationService;
		this.jobService=jobService;
		this.userService=userService;
	}

	@RequestMapping("jobs")
    public String addJobForm(Model model) {
    	model.addAttribute("job",new Job());
    	return "admin/addJob";
    }
	
	
	
	@RequestMapping("/jobs/edit/{id}")
	public String editJobFrom(@PathVariable Long id,Model m) {
		Optional<Job> job=jobService.findById(id);
		m.addAttribute("job", job.get());
		return "admin/editJob";
	}
	
	@RequestMapping("jobs/editjob")
	public String editJob(@ModelAttribute Job job) {
		jobService.update(job);
		return "admin/addJob";
	}
	
	@RequestMapping("jobs/add")
	public String addJob(@ModelAttribute Job job) {
		System.out.println("---------------------"+job.getId());
		jobService.save(job);
		return "admin/addJob";
	}
	
	@GetMapping("/jobs/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Optional<Job> job = jobService.findById(id);
            
        
        model.addAttribute("job", job.get());
        return "admin/viewJob";
    } 
	
	@RequestMapping("jobs/delete/{id}")
	public String deleteJob(@PathVariable Long id,RedirectAttributes redirectAttributes) {
	    try {
	    	Optional <Job> job=jobService.findById(id);
	        jobService.delete(job.get()); // Your service method to delete
	        redirectAttributes.addFlashAttribute("successMessage", "Job deleted successfully!");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Job could not be deleted.");
	    }
	    return "redirect:/jobs/viewAll";
	}
	
	    @GetMapping("/dashboard")
	    public String showDashboard(Model model) {
	        // Total Users
	        long totalUsers = userService.countAllUsers();
	        
	        // Job Statistics
	        Map<String, Long> jobStats = jobService.getJobStatistics();
	        long totalJobs = jobStats.get("total");
	        long activeJobs = jobStats.get("active");
	        long expiredJobs = jobStats.get("expired");
	        
	        // Application Statistics
	        Map<String, Long> appStats = applicationService.getApplicationStatistics();
	        long totalApps = appStats.get("total");
	        long pendingApps = appStats.get("pending");
	        long acceptedApps = appStats.get("accepted");
	        long rejectedApps = appStats.get("rejected");
	        
	        // Add attributes to model
	        model.addAttribute("totalUsers", totalUsers);
	        model.addAttribute("totalJobs", totalJobs);
	        model.addAttribute("activeJobs", activeJobs);
	        model.addAttribute("expiredJobs", expiredJobs);
	        model.addAttribute("totalApps", totalApps);
	        model.addAttribute("pendingApps", pendingApps);
	        model.addAttribute("acceptedApps", acceptedApps);
	        model.addAttribute("rejectedApps", rejectedApps);
	        
	        return "admin/dashboard";
	
	}
	

}