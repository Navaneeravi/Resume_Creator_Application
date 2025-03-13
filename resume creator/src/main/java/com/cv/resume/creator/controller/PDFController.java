package com.cv.resume.creator.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.repository.CvRepository;
import com.cv.resume.creator.service.PDFGeneratorService;

@RestController
public class PDFController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private CvRepository resumeRepository;

    @GetMapping("/cv/{id}/pdf")
    public ResponseEntity<byte[]> generateResumePDF(@PathVariable Long id) {
        Optional<CV> resumeOptional = resumeRepository.findById(id);
        if (resumeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CV resume = resumeOptional.get();
        byte[] pdfBytes = pdfGeneratorService.generateResumePDF(resume);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume_" + id + ".pdf")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(pdfBytes);
    }
}

