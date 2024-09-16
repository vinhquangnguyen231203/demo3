package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentImageDTO {
    @JsonProperty("student_id")
    @Min(value =1, message = "ID của student phải lớn hơn 0")
    private Long studentId;

    @Size(min = 5, max = 200, message = "Tên từ 5-200 kí tự")
    @JsonProperty("img_url")
    private String imgUrl;


}
