package com.cv.resume.creator.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.repository.CvRepository;

@Service
public class CvService {

    private final CvRepository resumeRepository;

    public CvService(CvRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // Create or update a Resume
    public CV saveResume(CV resume) {
        return resumeRepository.save(resume);
    }

    // Get Resume by ID
    public Optional<CV> getResumeById(Long id) {
        return resumeRepository.findById(id);
    }

    // Update an existing Resume
    public CV updateResume(Long id, CV updatedResume) {
        return resumeRepository.findById(id)
                .map(existingResume -> {
                    // Update basic details
                    existingResume.setName(updatedResume.getName());
                    existingResume.setEmail(updatedResume.getEmail());
                    existingResume.setLinkedin(updatedResume.getLinkedin());
                    existingResume.setPhone(updatedResume.getPhone());

                    // Update related entities (add checks for null)
                    if (updatedResume.getEducationList() != null && !updatedResume.getEducationList().isEmpty()) {
                        existingResume.setEducationList(updatedResume.getEducationList());
                    } else {
                        existingResume.setEducationList(existingResume.getEducationList()); // If no new education list provided, keep the existing one
                    }

                    if (updatedResume.getCourses() != null && !updatedResume.getCourses().isEmpty()) {
                        existingResume.setCourses(updatedResume.getCourses());
                    } else {
                        existingResume.setCourses(existingResume.getCourses()); // If no new courses provided, keep the existing ones
                    }

                    if (updatedResume.getAcademicAchievements() != null && !updatedResume.getAcademicAchievements().isEmpty()) {
                        existingResume.setAcademicAchievements(updatedResume.getAcademicAchievements());
                    } else {
                        existingResume.setAcademicAchievements(existingResume.getAcademicAchievements());
                    }

                    if (updatedResume.getExtracurricularActivities() != null && !updatedResume.getExtracurricularActivities().isEmpty()) {
                        existingResume.setExtracurricularActivities(updatedResume.getExtracurricularActivities());
                    } else {
                        existingResume.setExtracurricularActivities(existingResume.getExtracurricularActivities());
                    }

                    if (updatedResume.getSkills() != null && !updatedResume.getSkills().isEmpty()) {
                        existingResume.setSkills(updatedResume.getSkills());
                    } else {
                        existingResume.setSkills(existingResume.getSkills());
                    }

                    if (updatedResume.getWorkExperiences() != null && !updatedResume.getWorkExperiences().isEmpty()) {
                        existingResume.setWorkExperiences(updatedResume.getWorkExperiences());
                    } else {
                        existingResume.setWorkExperiences(existingResume.getWorkExperiences());
                    }
                    
                    if (updatedResume.getCareerObjective() != null && !updatedResume.getCareerObjective().isEmpty()) {
                        existingResume.setCareerObjective(updatedResume.getCareerObjective());
                    } else {
                        existingResume.setCareerObjective(existingResume.getCareerObjective());
                    }
                    if (updatedResume.getProject() != null && !updatedResume.getProject().isEmpty()) {
                        existingResume.setProject(updatedResume.getProject());
                    } else {
                        existingResume.setProject(existingResume.getProject());
                    }

                    // Save and return the updated resume
                    return resumeRepository.save(existingResume);
                })
                .orElseThrow(() -> new RuntimeException("Resume not found with ID: " + id));
    }

    // Delete Resume by ID
    public void deleteResumeById(Long id) {
        if (!resumeRepository.existsById(id)) {
            throw new RuntimeException("Resume not found with ID: " + id);
        }
        resumeRepository.deleteById(id);
    }
}
