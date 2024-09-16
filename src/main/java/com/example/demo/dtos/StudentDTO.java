package com.example.demo.dtos;

import com.example.demo.models.XepLoai;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {
    @NotBlank(message = "Tên không được trống nha")
    @Size(min = 2, max = 50, message = "Tên từ 2-50 kí tự")
    private String name;

    @NotBlank(message = "Thành phố không được trống nha")
    private String thanhPho;



    @Past(message = "Phải là ngày trong quá khứ")
    @JsonFormat(pattern = "dd-MM-yyyy")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Ngày sinh không được trống")
    private LocalDate ngaySinh;

    //    @NotNull(message = "Xếp loại không được trónog")
    @Enumerated(EnumType.STRING)
    private String xepLoai;
}
