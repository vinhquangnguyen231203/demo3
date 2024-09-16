package com.example.demo.services;

import com.example.demo.dtos.StudentDTO;
import com.example.demo.dtos.StudentImageDTO;
import com.example.demo.models.Student;
import com.example.demo.models.StudentImage;
import com.example.demo.models.XepLoai;
import com.example.demo.responses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    Student saveStudent(StudentDTO studentDTO);
    Student updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
    Page<StudentResponse> getStudents(PageRequest pageable);
    List<Student> findByTenContainingIgnoreCase(String name);
    List<Student> findByThanhPho(String name);
    List<Student> findByThanhPhoAndTen(String name);
    List<Student> findByXepLoai(XepLoai xepLoai);
    List<Student> findByYearNgaySinhBetween(int startYear, int endYear);
    List<Student> search(String name, XepLoai xepLoai, String thanhPho, int startYear, int endYear);

    StudentImage saveStudentImage(Long studentId, StudentImageDTO studentImageDTO);
    List<StudentImage> getAllStudentImages(Long studentId);
}
