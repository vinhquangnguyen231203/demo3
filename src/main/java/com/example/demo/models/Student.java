package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "student")
public class Student extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50, message = "Tên từ 2-50 kí tự")
    @NotBlank(message = "Tên không được trống nha")
    private String name;

    @NotBlank(message = "Thành phố không được trống nha")
    private String thanhPho;



    @Past(message = "Phải là ngày trong quá khứ")
//    @JsonFormat(pattern = "dd-MM-yyyy")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Ngày sinh không được trống")
    private LocalDate ngaySinh;

//    @NotNull(message = "Xếp loại không được trónog")
    @Enumerated(EnumType.STRING)
    private XepLoai xepLoai;
}
