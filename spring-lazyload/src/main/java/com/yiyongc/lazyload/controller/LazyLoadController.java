package com.yiyongc.lazyload.controller;

import com.yiyongc.lazyload.service.LazyLoadService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LazyLoadController {

  @Autowired
  private LazyLoadService lazyLoadService;

  @PostMapping("/loadDefault")
  public ResponseEntity<?> loadDefaultEntities() {
    lazyLoadService.loadDefaultEntities();
    return new ResponseEntity<>(null, HttpStatus.CREATED);
  }

  @GetMapping("/courses/{id}/students")
  public ResponseEntity<?> getStudentsInCourses(@PathVariable("id") UUID courseId) {
    List<String> studentNamesInCourse = lazyLoadService.getStudentNamesInCourse(courseId);
    return new ResponseEntity<>(studentNamesInCourse, HttpStatus.OK);
  }

  @GetMapping("/students/{id}/courses")
  public ResponseEntity<?> getCourseEnrolledByStudent(@PathVariable("id") UUID studentId) {
    String courseName = lazyLoadService.getCourseEnrolledByStudent(studentId);
    if (courseName != null && !courseName.isBlank()) {
      return ResponseEntity.ok(courseName);
    } else {
      return new ResponseEntity<>("Student is not enrolled in any course.", HttpStatus.NOT_FOUND);
    }
  }
}
