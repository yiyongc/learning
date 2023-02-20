package com.yiyongc.lazyload.repository;

import com.yiyongc.lazyload.model.Course;
import com.yiyongc.lazyload.model.Student;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
  List<Student> findAllByCourseEnrolled(Course courseEnrolled);
}
