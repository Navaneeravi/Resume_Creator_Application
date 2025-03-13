package com.cv.resume.creator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cv.resume.creator.model.CV;

public interface CvRepository extends JpaRepository<CV, Long> {

}
