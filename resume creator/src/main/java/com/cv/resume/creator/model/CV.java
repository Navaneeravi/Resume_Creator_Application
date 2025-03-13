package com.cv.resume.creator.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String linkedin;
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<CareerObjective> careerObjective;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Project> project;

    @ElementCollection
    private List<String> courses;

    @ElementCollection
    private List<String> academicAchievements;

    @ElementCollection
    private List<String> extracurricularActivities;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Education> educationList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Skill> skills;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<WorkExperience> workExperiences;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

	public List<String> getAcademicAchievements() {
		return academicAchievements;
	}

	public void setAcademicAchievements(List<String> academicAchievements) {
		this.academicAchievements = academicAchievements;
	}

	public List<String> getExtracurricularActivities() {
		return extracurricularActivities;
	}

	public void setExtracurricularActivities(List<String> extracurricularActivities) {
		this.extracurricularActivities = extracurricularActivities;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(List<WorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

	public List<CareerObjective> getCareerObjective() {
		return careerObjective;
	}

	public void setCareerObjective(List<CareerObjective> careerObjective) {
		this.careerObjective = careerObjective;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public List<Education> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<Education> educationList) {
		this.educationList = educationList;
	}
	
	
	
	
	
	

	

    
}
