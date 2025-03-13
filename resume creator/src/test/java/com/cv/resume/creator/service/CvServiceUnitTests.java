package com.cv.resume.creator.service;

import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.repository.CvRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CvServiceUnitTests {

    @Mock
    private CvRepository cvRepository;

    @InjectMocks
    private CvService cvService;

    public CvServiceUnitTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveResume() {
        CV mockResume = new CV();
        mockResume.setName("John Doe");

        when(cvRepository.save(mockResume)).thenReturn(mockResume);
        CV savedResume = cvService.saveResume(mockResume);

        assertNotNull(savedResume);
        assertEquals("John Doe", savedResume.getName());
    }

    @Test
    public void testGetResumeById() {
        CV mockResume = new CV();
        mockResume.setId(1L);

        when(cvRepository.findById(1L)).thenReturn(Optional.of(mockResume));
        Optional<CV> fetchedResume = cvService.getResumeById(1L);

        assertTrue(fetchedResume.isPresent());
        assertEquals(1L, fetchedResume.get().getId());
    }

    @Test
    public void testUpdateResumeNotFound() {
        when(cvRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            cvService.updateResume(1L, new CV())
        );
        assertEquals("Resume not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testDeleteResumeById() {
        when(cvRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cvRepository).deleteById(1L);

        assertDoesNotThrow(() -> cvService.deleteResumeById(1L));
        verify(cvRepository, times(1)).deleteById(1L);
    }
}
