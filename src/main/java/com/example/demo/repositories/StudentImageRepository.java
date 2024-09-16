package com.example.demo.repositories;

import com.example.demo.models.StudentImage;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentImageRepository extends JpaRepository<StudentImage, Long>{
    @Query("SELECT s from StudentImage s where s.student.id = :id")
    List<StudentImage> findAllByStudentIdTemp(Long id);
}
