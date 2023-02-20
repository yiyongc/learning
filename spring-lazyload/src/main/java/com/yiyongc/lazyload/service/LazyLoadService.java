package com.yiyongc.lazyload.service;

import com.yiyongc.lazyload.model.Course;
import com.yiyongc.lazyload.model.Student;
import com.yiyongc.lazyload.repository.CourseRepository;
import com.yiyongc.lazyload.repository.StudentRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LazyLoadService {

  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private CourseRepository courseRepository;

  @Transactional(rollbackOn = Exception.class)
  public void loadDefaultEntities() {
    // Initialize 2 courses
    Course course1 = new Course("Intro to Supply Chain", 3);
    course1 = courseRepository.save(course1);
    Course course2 = new Course("Intro to Computing", 1);
    course2 = courseRepository.save(course2);

    // Initialize 3 students
    Student student1 = new Student("Josephine Silver", "JY280217");
    student1.setCourseEnrolled(course1);
    studentRepository.save(student1);
    Student student2 = new Student("Michael Angelo", "MA102030");
    studentRepository.save(student2);
    Student student3 = new Student("Chris Choi", "CC050692");
    student3.setCourseEnrolled(course2);
    studentRepository.save(student3);
  }

  public List<String> getStudentNamesInCourse(UUID courseId) {
    Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);
    List<Student> studentsInCourse = studentRepository.findAllByCourseEnrolled(course);
    return studentsInCourse.stream().map(Student::getName).collect(Collectors.toUnmodifiableList());
  }

  public String getCourseEnrolledByStudent(UUID studentId) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(EntityNotFoundException::new);
    Course studentEnrolledCourse = student.getCourseEnrolled();
    System.out.println("----- BEFORE LAZY INIT -----");
    String courseName = studentEnrolledCourse != null ? studentEnrolledCourse.getName() : "";
    System.out.println("----- AFTER LAZY INIT -----");
    return courseName;
  }
}
