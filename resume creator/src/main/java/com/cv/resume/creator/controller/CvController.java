package com.cv.resume.creator.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.service.CvService;

@Controller
@RequestMapping("/cv")
public class CvController {

    private final CvService resumeService;

    public CvController(CvService resumeService) {
        this.resumeService = resumeService;
    }

    // REST API to create a new Resume
    @PostMapping
    @ResponseBody
    public CV createResume(@RequestBody CV resume) {
        return resumeService.saveResume(resume);
    }

    // REST API to get a Resume by ID
    @GetMapping("/{id}")
    @ResponseBody
    public CV getResume(@PathVariable Long id) {
        return resumeService.getResumeById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found!"));
    }

    // Thymeleaf template rendering for Resume view
    @GetMapping("/{id}/view")
    public String getResumeView(@PathVariable Long id, Model model) {
        Optional<CV> resume = resumeService.getResumeById(id);
        if (resume.isPresent()) {
            model.addAttribute("resume", resume.get());
            return "resume"; // Points to src/main/resources/templates/resume.html
        } else {
            throw new RuntimeException("Resume not found!");
        }
    }

    // Redirect `/resumes/{id}` to `/resumes/{id}/view`
    @GetMapping("/{id}/redirect")
    public String redirectToView(@PathVariable Long id) {
        return "redirect:/resumes/" + id + "/view";
    }

    // REST API to update a Resume
    @PutMapping("/{id}")
    @ResponseBody
    public CV updateResume(@PathVariable Long id, @RequestBody CV updatedResume) {
        return resumeService.updateResume(id, updatedResume);
    }

    // REST API to delete a Resume by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Long id) {
        try {
            resumeService.deleteResumeById(id);
            return ResponseEntity.ok("ID " + id + " has been deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}

