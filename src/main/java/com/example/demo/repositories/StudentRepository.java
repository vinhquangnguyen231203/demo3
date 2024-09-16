package com.example.demo.repositories;

import com.example.demo.models.Student;
import com.example.demo.models.XepLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);

    List<Student> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s from Student s where s.thanhPho like LOWER(concat('%',:name,'%'))")
    List<Student> findByThanhPho(String name);

    @Query("SELECT s from Student s where s.thanhPho like LOWER(concat('%',:name,'%')) or s.name like LOWER(concat('%',:name,'%'))")
    List<Student> findByThanhPhoAndTen(String name);


    @Query("select s from Student s where s.xepLoai = :xepLoai")
    List<Student> findByXepLoai(XepLoai xepLoai);

    @Query("select s from Student  s where year(s.ngaySinh) between :startYear and :endYear")
    List<Student> findByYearNgaySinhBetween(int startYear, int endYear);


    @Query("select s from Student s where " +
            "(:name is null or s.name like %:name%) and " +
            "(:xepLoai is null or s.xepLoai = :xepLoai) and " +
            "(:thanhPho is null or s.thanhPho like %:thanhPho%) and " +
            "(:startYear is null or year(s.ngaySinh) >= :startYear) and " +
            "(:endYear is null or year(s.ngaySinh) <= :endYear)")
    List<Student> search(
            @Param("name") String name,
            @Param("xepLoai") XepLoai xepLoai,
            @Param("thanhPho") String thanhPho,
            @Param("startYear") int startYear, // Changed to Integer for nullability
            @Param("endYear") int endYear     // Changed to Integer for nullability
    );
}
