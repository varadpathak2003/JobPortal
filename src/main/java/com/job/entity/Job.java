package com.job.entity;

import jakarta.persistence.*;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String status;

    private String description;
    
    private String company;
    
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private String location;

    private Double salary;
    
    private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String category;

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// If you want to show all applications for this job
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<JobApplication> applications;

    // Constructors
    public Job() {
    }

    public Job(String title, String description, String location, Double salary) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
    }

    // Getters and setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Set<JobApplication> getApplications() {
        return applications;
    }

    public void setApplications(Set<JobApplication> applications) {
        this.applications = applications;
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
