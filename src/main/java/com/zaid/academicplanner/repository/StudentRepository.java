package com.zaid.academicplanner.repository;

import com.zaid.academicplanner.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUniversityId(String universityId);

    boolean existsByUniversityId(String universityId);
}