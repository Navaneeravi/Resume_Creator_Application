package com.cv.resume.creator.controller;

import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.service.CvService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CvControllerUnitTests {

    @Mock
    private CvService cvService;

    @InjectMocks
    private CvController cvController;

    public CvControllerUnitTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateResume() {
        CV mockResume = new CV();
        mockResume.setName("Navaneethan");

        when(cvService.saveResume(mockResume)).thenReturn(mockResume);
        CV createdResume = cvController.createResume(mockResume);

        assertNotNull(createdResume);
        assertEquals("Navaneethan", createdResume.getName());
    }

    @Test
    public void testGetResume() {
        CV mockResume = new CV();
        mockResume.setId(2L);

        when(cvService.getResumeById(2L)).thenReturn(Optional.of(mockResume));
        CV fetchedResume = cvController.getResume(2L);

        assertNotNull(fetchedResume);
        assertEquals(2L, fetchedResume.getId());
    }

    @Test
    public void testDeleteResume() {
        doNothing().when(cvService).deleteResumeById(3L);
        ResponseEntity<String> response = cvController.deleteResume(3L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Resume with ID 3 has been deleted.", response.getBody());
    }
}
