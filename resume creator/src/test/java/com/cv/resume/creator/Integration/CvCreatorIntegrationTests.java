package com.cv.resume.creator.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cv.resume.creator.controller.CvController;
import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.service.CvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CvCreatorIntegrationTests {

    private MockMvc mockMvc;

    @Mock
    private CvService cvService;

    @InjectMocks
    private CvController cvController;

    private ObjectMapper objectMapper;

    private CV cv;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cvController).build();
        objectMapper = new ObjectMapper();

        // Initialize a sample CV
        cv = new CV();
        cv.setId(1L);
        cv.setName("John Doe");
        cv.setEmail("john.doe@example.com");
        cv.setPhone("123456789");
        cv.setLinkedin("https://linkedin.com/in/johndoe");
        // Add other fields as needed (like skills, work experiences, etc.)
    }

    @Test
    void createCv() throws Exception {
        when(cvService.saveResume(any(CV.class))).thenReturn(cv);

        mockMvc.perform(post("/cv")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cv)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("123456789"));

        verify(cvService, times(1)).saveResume(any(CV.class));
    }

    @Test
    void getCv() throws Exception {
        when(cvService.getResumeById(1L)).thenReturn(Optional.of(cv));

        mockMvc.perform(get("/cv/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("123456789"));

        verify(cvService, times(1)).getResumeById(1L);
    }

    @Test
    void updateCv() throws Exception {
        when(cvService.updateResume(eq(1L), any(CV.class))).thenReturn(cv);

        cv.setName("John Updated");
        mockMvc.perform(put("/cv/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cv)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(cvService, times(1)).updateResume(eq(1L), any(CV.class));
    }

    @Test
    void deleteCv() throws Exception {
        doNothing().when(cvService).deleteResumeById(1L);

        mockMvc.perform(delete("/cv/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Resume with ID 1 has been deleted."));

        verify(cvService, times(1)).deleteResumeById(1L);
    }

    @Test
    void getCvView() throws Exception {
        when(cvService.getResumeById(1L)).thenReturn(Optional.of(cv));

        mockMvc.perform(get("/cv/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("resume"));  // Points to src/main/resources/templates/resume.html

        verify(cvService, times(1)).getResumeById(1L);
    }

//    @Test
//    void getCvViewWithInvalidId() throws Exception {
//        when(cvService.getResumeById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/cv/1/view"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Resume not found!"));
//
//        verify(cvService, times(1)).getResumeById(1L);
//    }
}


























//out 4 2 is failure

//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.cv.resume.creator.controller.CvController;
//import com.cv.resume.creator.model.CV;
//import com.cv.resume.creator.service.CvService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//class CvCreatorIntegrationTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private CvService resumeService;
//
//    @InjectMocks
//    private CvController resumeController;
//
//    private ObjectMapper objectMapper;
//
//    private CV resume;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(resumeController).build();
//        objectMapper = new ObjectMapper();
//
//        // Initialize sample Resume
//        resume = new CV();
//        resume.setId(1L);
//        resume.setName("John Doe");
//        resume.setEmail("john.doe@example.com");
//        resume.setPhone("123456789");
//        resume.setLinkedin("linkedin.com/in/john");;
//    }
//
//    // 1. Test for creating a resume
////    @Test
////    void testCreateResume() throws Exception {
////        when(resumeService.saveResume(any(CV.class))).thenReturn(resume);
////
////        mockMvc.perform(post("/cv")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(resume)))
////                .andExpect(status().isCreated())
////                .andExpect(jsonPath("$.name").value("John Doe"))
////                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
////                .andExpect(jsonPath("$.phone").value("123456789"))
////                .andExpect(jsonPath("$.linkedin").value("linkedin.com/in/john"));
////
////        verify(resumeService, times(1)).saveResume(any(CV.class));
////    }
//    
//    @Test
//    void testcreateResume() throws Exception {
//        // Mock the service to return the resume when saved
//        when(resumeService.saveResume(any(CV.class))).thenReturn(resume);
//
//        // Perform the HTTP POST request to create a resume
//        mockMvc.perform(post("/cv")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(resume))) // Convert resume object to JSON
//                    .andExpect(status().isCreated()) // Expecting 201 Created status
//                    .andExpect(jsonPath("$.name").value("John Doe")) // Assert name in the response JSON
//                    .andExpect(jsonPath("$.email").value("john.doe@example.com")) // Assert email in the response JSON
//                    .andExpect(jsonPath("$.phone").value("123456789")) // Assert phone number
//                    .andExpect(jsonPath("$.linkedin").value("linkedin.com/in/john")); // Assert LinkedIn URL
//        
//
//        // Verify that the saveResume method was called exactly once
//        verify(resumeService, times(1)).saveResume(any(CV.class));
//    }
//
//
//    // 2. Test for retrieving a resume by ID
//    @Test
//    void testGetResumeById() throws Exception {
//        when(resumeService.getResumeById(1L)).thenReturn(Optional.of(resume));
//
//        mockMvc.perform(get("/cv/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
//                .andExpect(jsonPath("$.phone").value("123456789"))
//                .andExpect(jsonPath("$.linkedin").value("linkedin.com/in/john"));;
//
//        verify(resumeService, times(1)).getResumeById(1L);
//    }
//
//    // 3. Test for updating a resume
//    @Test
//    void testUpdateResume() throws Exception {
//        resume.setName("John Updated");
//
//        when(resumeService.updateResume(eq(1L), any(CV.class))).thenReturn(resume);
//
//        mockMvc.perform(put("/cv/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(resume)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Updated"));
//
//        verify(resumeService, times(1)).updateResume(eq(1L), any(CV.class));
//    }
//
//    // 4. Test for deleting a resume
//    @Test
//    void testDeleteResume() throws Exception {
//        doNothing().when(resumeService).deleteResumeById(1L);
//
//        mockMvc.perform(delete("/cv/1"))
//                .andExpect(status().isNoContent());
//
//        verify(resumeService, times(1)).deleteResumeById(1L);
//    }
//
//    // 5. Test for handling resume not found
////    @Test
////    void testGetResumeByIdNotFound() throws Exception {
////        when(resumeService.getResumeById(1L)).thenReturn(Optional.empty());
////
////        mockMvc.perform(get("/cv/1"))
////                .andExpect(status().isNotFound())
////                .andExpect(content().string("Resume not found"));
////
////        verify(resumeService, times(1)).getResumeById(1L);
////    }
//}





//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.cv.resume.creator.controller.CvController;
//import com.cv.resume.creator.model.CV;
//import com.cv.resume.creator.service.CvService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//class CvCreatorIntegrationTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private CvService cvService;
//
//    @InjectMocks
//    private CvController cvController;
//
//    private ObjectMapper objectMapper;
//
//    private CV cv;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(cvController).build();
//        objectMapper = new ObjectMapper();
//
//        // Initialize sample Resume
//        cv = new CV();
//        cv.setId(1L);
//        cv.setName("Navanee Ravi");
//        cv.setEmail("navanee.ravi@example.com");
//        cv.setPhone("7530087211");
//        cv.setLinkedin("https://linkedin.com/in/navaneeravi");
//    }
//
//    // 1. Test for creating a resume
//    @Test
//    void testCreateResume() throws Exception {
//        when(cvService.saveResume(any(CV.class))).thenReturn(cv);
//
//        mockMvc.perform(post("/cv")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(cv)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("Navanee Ravi"))
//                .andExpect(jsonPath("$.email").value("navanee.ravi@example.com"))
//                .andExpect(jsonPath("$.phone").value("7530087211"))
//                .andExpect(jsonPath("$.linkedin").value("https://linkedin.com/in/navaneeravi"));
//
//        verify(cvService, times(1)).saveResume(any(CV.class));
//    }
//
//    // 2. Test for retrieving a resume by ID
//    @Test
//    void testGetResumeById() throws Exception {
//        when(cvService.getResumeById(1L)).thenReturn(Optional.of(cv));
//
//        mockMvc.perform(get("/cv/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Navanee Ravi"))
//                .andExpect(jsonPath("$.email").value("navanee.ravi@example.com"))
//                .andExpect(jsonPath("$.phone").value("7530087211"))
//                .andExpect(jsonPath("$.linkedin").value("https://linkedin.com/in/navaneeravi"));
//
//        verify(cvService, times(1)).getResumeById(1L);
//    }
//
//    // 3. Test for updating a resume
//    @Test
//    void testUpdateResume() throws Exception {
//        cv.setName("John Updated");
//
//        when(cvService.updateResume(eq(1L), any(CV.class))).thenReturn(cv);
//
//        mockMvc.perform(put("/cv/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(cv)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Navanee Updated"));
//
//        verify(cvService, times(1)).updateResume(eq(1L), any(CV.class));
//    }
//
//    // 4. Test for deleting a resume
//    @Test
//    void testDeleteResume() throws Exception {
//        doNothing().when(cvService).deleteResumeById(1L);
//
//        mockMvc.perform(delete("/cv/1"))
//                .andExpect(status().isNoContent());
//
//        verify(cvService, times(1)).deleteResumeById(1L);
//    }

    // 5. Test for handling resume not found
//    @Test
//    void testGetResumeByIdNotFound() throws Exception {
//        when(cvService.getResumeById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/cv/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Resume not found"));
//
//        verify(cvService, times(1)).getResumeById(1L);
//    }
//}


//import com.cv.resume.creator.configuration.TestSecurityConfig;

//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CvCreatorIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testHomePageLoads() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
//    }
//
//    @Test
//    public void testCreateResume() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/cv")
//                .contentType("application/json")
//                .content("{\"name\":\"John Doe\", \"email\":\"john@example.com\"}"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetResumeNotFound() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/cv/999"))
//                .andExpect(status().is4xxClientError());
//    }
//}
//




//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CvCreatorIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testGetResumeById() throws Exception {
//        mockMvc.perform(get("/cv/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1));
//    }
//
//    @Test
//    public void testCreateResume() throws Exception {
//        String newResume = """
//                "name": "senthil",
//  "email": "senthil@gmail.com",
//  "linkedin" :"http//senthil.com",
//  "phone": "1234567895",
//  "careerObjective": [
//    {
//      "description": "Detail-oriented Java Full-Stack Developer with a strong foundation in Core Java Programming, seeking a challenging Java Developer role."
//    }
//  ],
//  "project": [
//    {
//      "title": "Online Food Delivery System",
//      "description": "The Online Food Delivery System is a web-based application developed "
//    }
//  ],
//  "courses": [
//    "Java Full Stack"
//  ],
//  "academicAchievements": [
//    "Best Thesis Award"
//  ],
//  "extracurricularActivities": [
//    "Drama Club",
//    "Chess Club"
//  ],
//  "educationList": [
//    {
//      "university": "K.L.N College of Engineering",
//      "degree": "B.E Computer Science and Engineering",
//      "graduationYear": 2023
//    }
//  ],
//  "skills": [
//    {
//      "name": "Java"
//    },
//    {
//      "name": "Spring Boot"
//    },
//    {
//      "name": "MySql"
//    },
//    {
//      "name": "HTML"
//    },
//    {
//      "name": "CSS"
//    },
//    {
//      "name": "JavaScript"
//    }
//  ],
//  "workExperiences": [
//    {
//      "description": "Palle  - Java Trainee"
//    }
//  ]
//                """;
//
//        mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newResume))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("John Doe"));
//    }
//}



//import com.cv.resume.creator.model.CV;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CvCreatorIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper; // For serializing/deserializing JSON
//
//    @Test
//    public void testCreateResume() throws Exception {
//        CV newResume = new CV();
//        newResume.setName("Navaneethan");
//        newResume.setEmail("navanee@gmail.com");
//        newResume.setPhone("7530087211");
//        newResume.setLinkedin("https://linkedin.com/in/navanee ravi");
//
//        String resumeJson = objectMapper.writeValueAsString(newResume);
//
//        mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Navaneethan"))
//                .andExpect(jsonPath("$.email").value("navanee@gmail.com"));
//    }
//
//    @Test
//    public void testGetResumeById() throws Exception {
//        Long existingResumeId = 1L;
//
//        mockMvc.perform(get("/cv/{id}", existingResumeId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(existingResumeId))
//                .andExpect(jsonPath("$.name").exists());
//    }
//
//    @Test
//    public void testUpdateResume() throws Exception {
//        Long existingResumeId = 1L;
//
//        CV updatedResume = new CV();
//        updatedResume.setName("Navaneethan");
//        updatedResume.setEmail("navaneeravi@gmail.com");
//        updatedResume.setPhone("7530087211");
//        updatedResume.setLinkedin("https://linkedin.com/in/navanee ravi");
//
//        String updatedResumeJson = objectMapper.writeValueAsString(updatedResume);
//
//        mockMvc.perform(put("/cv/{id}", existingResumeId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedResumeJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Navaneethan"))
//                .andExpect(jsonPath("$.email").value("navaneeravi@gmail.com"));
//    }
//
//    @Test
//    public void testDeleteResume() throws Exception {
//        Long existingResumeId = 1L;
//
//        mockMvc.perform(delete("/cv/{id}", existingResumeId))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Resume with ID " + existingResumeId + " has been deleted."));
//    }
//
//    @Test
//    public void testGetResumeByIdNotFound() throws Exception {
//        Long nonExistentId = 999L;
//
//        mockMvc.perform(get("/cv/{id}", nonExistentId))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Resume not found!"));
//    }
//}
//
//






//import com.cv.resume.creator.model.CV;
//import com.cv.resume.creator.model.Skill;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CvCreatorIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private CV sampleResume;
//
//    @BeforeEach
//    public void setUp() {
//        // Create a sample CV object for testing
//        sampleResume = new CV();
//        sampleResume.setName("John Doe");
//        sampleResume.setEmail("johndoe@example.com");
//        sampleResume.setPhone("1234567890");
//        sampleResume.setLinkedin("https://linkedin.com/in/johndoe");
//
//        // Add skills
//        List<Skill> skills = new ArrayList<>();
//        Skill skill = new Skill();
//        skill.setName("Java");
//        skills.add(skill);
//        sampleResume.setSkills(skills);
//
//        // Add courses
//        List<String> courses = List.of("Spring Boot", "REST API Development");
//        sampleResume.setCourses(courses);
//    }
//
//    @Test
//    public void testCreateResume() throws Exception {
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
//    }
//
//    @Test
//    public void testGetResumeById() throws Exception {
//        // Create a resume first
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        String response = mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        CV createdResume = objectMapper.readValue(response, CV.class);
//
//        // Fetch the resume by ID
//        mockMvc.perform(get("/cv/{id}", createdResume.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(createdResume.getId()))
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
//    }
//
//    @Test
//    public void testUpdateResume() throws Exception {
//        // Create a resume first
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        String response = mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        CV createdResume = objectMapper.readValue(response, CV.class);
//
//        // Update the resume details
//        createdResume.setName("Jane Doe");
//        createdResume.setEmail("janedoe@example.com");
//
//        String updatedResumeJson = objectMapper.writeValueAsString(createdResume);
//
//        mockMvc.perform(put("/cv/{id}", createdResume.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedResumeJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Jane Doe"))
//                .andExpect(jsonPath("$.email").value("janedoe@example.com"));
//    }
//
//    @Test
//    public void testDeleteResume() throws Exception {
//        // Create a resume first
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        String response = mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        CV createdResume = objectMapper.readValue(response, CV.class);
//
//        // Delete the resume
//        mockMvc.perform(delete("/cv/{id}", createdResume.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Resume with ID " + createdResume.getId() + " has been deleted."));
//    }
//
//    @Test
//    public void testGetResumeByIdNotFound() throws Exception {
//        Long nonExistentId = 999L;
//
//        mockMvc.perform(get("/cv/{id}", nonExistentId))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Resume not found!"));
//    }
//}




//import com.cv.resume.creator.model.CV;
//import com.cv.resume.creator.model.Education;
//import com.cv.resume.creator.model.Skill;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//
//public class CvCreatorIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private CV sampleResume;
//    
//    
//    
//    
//
//    @BeforeEach
//    public void setUp() {
//        // Create a sample CV object for testing
//        sampleResume = new CV();
//        sampleResume.setName("John Doe");
//        sampleResume.setEmail("johndoe@example.com");
//        sampleResume.setPhone("1234567890");
//        sampleResume.setLinkedin("https://linkedin.com/in/johndoe");
//
//        // Add skills
//        List<Skill> skills = new ArrayList<>();
//        Skill skill = new Skill();
//        skill.setName("Java");
//        skills.add(skill);
//        sampleResume.setSkills(skills);
//
//        // Add education
//        List<Education> educationList = new ArrayList<>();
//        Education education = new Education();
//        education.setUniversity("XYZ University");
//        education.setDegree("B.Tech");
//        education.setGraduationYear(2022);
//        educationList.add(education);
//        sampleResume.setEducationList(educationList);
//
//        // Add courses
//        List<String> courses = List.of("Spring Boot", "REST API Development");
//        sampleResume.setCourses(courses);
//    }
//
//    @Test
//    public void testCreateResume() throws Exception {
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
//    }
//
//    @Test
//    public void testGetResumeById() throws Exception {
//        // Create a resume first
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        String response = mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        CV createdResume = objectMapper.readValue(response, CV.class);
//
//        // Fetch the resume by ID
//        mockMvc.perform(get("/cv/{id}", createdResume.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(createdResume.getId()))
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
//    }
//
//    @Test
//    public void testUpdateResume() throws Exception {
//        // Create a resume first
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        String response = mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        CV createdResume = objectMapper.readValue(response, CV.class);
//
//        // Update the resume details
//        createdResume.setName("Jane Doe");
//        createdResume.setEmail("janedoe@example.com");
//
//        String updatedResumeJson = objectMapper.writeValueAsString(createdResume);
//
//        mockMvc.perform(put("/cv/{id}", createdResume.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedResumeJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Jane Doe"))
//                .andExpect(jsonPath("$.email").value("janedoe@example.com"));
//    }
//
//    @Test
//    public void testDeleteResume() throws Exception {
//        // Create a resume first
//        String resumeJson = objectMapper.writeValueAsString(sampleResume);
//
//        String response = mockMvc.perform(post("/cv")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(resumeJson))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        CV createdResume = objectMapper.readValue(response, CV.class);
//
//        // Delete the resume
//        mockMvc.perform(delete("/cv/{id}", createdResume.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Resume with ID " + createdResume.getId() + " has been deleted."));
//    }
//
//    @Test
//    public void testGetResumeByIdNotFound() throws Exception {
//        Long nonExistentId = 999L;
//
//        mockMvc.perform(get("/cv/{id}", nonExistentId))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Resume not found!"));
//    }
//}
//
