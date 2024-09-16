package com.example.demo.responses;

import com.example.demo.models.Student;
import com.example.demo.models.XepLoai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse  extends  BaseResponse{
    private Long id;
    private String name;
    private String thanhPho;
    private LocalDate ngaySinh;
    private String xepLoai;

    public static StudentResponse fromStudent(Student student){
        StudentResponse studentResponse = StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .thanhPho(student.getThanhPho())
                .ngaySinh(student.getNgaySinh())
                .xepLoai(student.getXepLoai().getXl())
                .build();
        studentResponse.setCreateAt(student.getCreateAt());
        studentResponse.setUpdateAt(student.getUpdateAt());
        return studentResponse;
    }
}
