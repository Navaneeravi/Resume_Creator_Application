package com.cv.resume.creator.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.cv.resume.creator.model.Education;
import com.cv.resume.creator.model.Project;
import com.cv.resume.creator.model.WorkExperience;
import com.cv.resume.creator.model.CV;
import com.cv.resume.creator.model.CareerObjective;
import com.cv.resume.creator.model.Skill;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFGeneratorService {

    public byte[] generateResumePDF(CV resume) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Create iText 5 Document instance
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            // Open the document for writing
            document.open();

            // Add Title
            Paragraph title = new Paragraph("Resume");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Add Personal Details
            document.add(new Paragraph("Name: " + resume.getName()));
            document.add(new Paragraph("Email: " + resume.getEmail()));
            document.add(new Paragraph("LinkedIn: " + resume.getLinkedin()));
            document.add(new Paragraph("Phone: " + resume.getPhone()));
            document.add(new Paragraph("\n"));

            // Add Education Section
            Paragraph educationTitle = new Paragraph("Education");
            educationTitle.setSpacingAfter(5);
            document.add(educationTitle);

            if (resume.getEducationList() != null && !resume.getEducationList().isEmpty()) {
                List educationList = new List();
                for (Education education : resume.getEducationList()) {
                    educationList.add(new ListItem("University: " + education.getUniversity() +
                            " | Degree: " + education.getDegree() +
                            " | Graduation Year: " + education.getGraduationYear()));
                }
                document.add(educationList);
            } else {
                document.add(new Paragraph("No education details provided."));
            }
            document.add(new Paragraph("\n"));

            // Add Courses Section
            Paragraph coursesTitle = new Paragraph("Courses Taken:");
            coursesTitle.setSpacingAfter(5);
            document.add(coursesTitle);

            if (resume.getCourses() != null && !resume.getCourses().isEmpty()) {
                List coursesList = new List();
                for (String course : resume.getCourses()) {
                    coursesList.add(new ListItem(course));
                }
                document.add(coursesList);
            } else {
                document.add(new Paragraph("No courses provided."));
            }
            document.add(new Paragraph("\n"));

            // Add Academic Achievements Section
            Paragraph achievementsTitle = new Paragraph("Academic Achievements:");
            achievementsTitle.setSpacingAfter(5);
            document.add(achievementsTitle);

            if (resume.getAcademicAchievements() != null && !resume.getAcademicAchievements().isEmpty()) {
                List achievementsList = new List();
                for (String achievement : resume.getAcademicAchievements()) {
                    achievementsList.add(new ListItem(achievement));
                }
                document.add(achievementsList);
            } else {
                document.add(new Paragraph("No academic achievements provided."));
            }
            document.add(new Paragraph("\n"));

            // Add Extracurricular Activities Section
            Paragraph extracurricularTitle = new Paragraph("Extracurricular Activities:");
            extracurricularTitle.setSpacingAfter(5);
            document.add(extracurricularTitle);

            if (resume.getExtracurricularActivities() != null && !resume.getExtracurricularActivities().isEmpty()) {
                List extracurricularList = new List();
                for (String activity : resume.getExtracurricularActivities()) {
                    extracurricularList.add(new ListItem(activity));
                }
                document.add(extracurricularList);
            } else {
                document.add(new Paragraph("No extracurricular activities provided."));
            }
            document.add(new Paragraph("\n"));

            // Add Skills Section
            Paragraph skillsTitle = new Paragraph("Skills:");
            skillsTitle.setSpacingAfter(5);
            document.add(skillsTitle);

            if (resume.getSkills() != null && !resume.getSkills().isEmpty()) {
                List skillsList = new List();
                for (Skill skill : resume.getSkills()) {
                    skillsList.add(new ListItem(skill.getName()));
                }
                document.add(skillsList);
            } else {
                document.add(new Paragraph("No skills provided."));
            }

            // Add WorkExperience Section
            Paragraph WorkExperienceTitle = new Paragraph("WorkExperiences:");
            WorkExperienceTitle.setSpacingAfter(5);
            document.add(WorkExperienceTitle);

            if (resume.getWorkExperiences() != null && !resume.getWorkExperiences().isEmpty()) {
                List WorkExperienceList = new List();
                for (WorkExperience internship : resume.getWorkExperiences()) {
                    WorkExperienceList.add(new ListItem(internship.getDescription()));
                }
                document.add(WorkExperienceList);
            } else {
                document.add(new Paragraph("No WorkExperience experience provided."));
            }
            
         // Add CareerObjective Section
            Paragraph CareerObjectiveTitle = new Paragraph("CareerObjective:");
            CareerObjectiveTitle.setSpacingAfter(5);
            document.add(CareerObjectiveTitle);
            
            if (resume.getCareerObjective() != null && !resume.getCareerObjective().isEmpty()) {
                List careerobjectiveList = new List();
                for (CareerObjective careerobjective : resume.getCareerObjective()) {
                	careerobjectiveList.add(new ListItem(careerobjective.getDescription()));
                }
                document.add(careerobjectiveList);
            } else {
                document.add(new Paragraph("No CareerObjective experience provided."));
            }
            
         // Add Project Section
            Paragraph ProjectTitle = new Paragraph("Project:");
            ProjectTitle.setSpacingAfter(5);
            document.add(ProjectTitle);
            
            if (resume.getProject() != null && !resume.getProject().isEmpty()) {
                List projectList = new List();
                for (Project project : resume.getProject()) {
                	projectList.add(new ListItem(project.getDescription()));
                }
                document.add(projectList);
            } else {
                document.add(new Paragraph("No CareerObjective experience provided."));
            }

            // Close the document
            document.close();

            // Return the PDF as a byte array
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
