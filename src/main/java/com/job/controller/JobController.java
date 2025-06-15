package com.job.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.job.entity.Job;
import com.job.service.JobService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public String showJobsPage(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            Model model) {

        // Get all distinct values for filters (locations, categories)
        List<String> locations = jobService.findDistinctLocations();
        List<String> categories = jobService.findDistinctCategories();

        // Get filtered jobs based on search parameters
        List<Job> filteredJobs = jobService.findFilteredJobs(search, location, type, category);
        List <Job> jobs=jobService.findAll();
        
        // Get featured jobs (latest 3 active jobs)
        List<Job> featuredJobs = jobService.findFeaturedJobs(3);

        // Add attributes to the model
        model.addAttribute("jobs", jobs);
        model.addAttribute("totalJobs", jobs.size());
        model.addAttribute("featuredJobs", featuredJobs);
        model.addAttribute("locations", locations);
        model.addAttribute("categories", categories);
        
        // Add search parameters back to model to maintain form state
        model.addAttribute("search", search);
        model.addAttribute("selectedLocation", location);
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedCategory", category);

        return "user/job";
    }
    
    @GetMapping("/viewAll")
    public String viewAllJobs(Model model, HttpServletRequest request) {
        // Check admin authentication (typically handled by Spring Security)
        
        List<Job> allJobs = jobService.findAll();
        model.addAttribute("jobs", allJobs);

        
        // Add flash message if exists
        if (request.getSession().getAttribute("flashMessage") != null) {
            model.addAttribute("flashMessage", request.getSession().getAttribute("flashMessage"));
            request.getSession().removeAttribute("flashMessage");
        }
        
        return "admin/jobs";
    }
}