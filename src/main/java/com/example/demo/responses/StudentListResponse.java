package com.example.demo.responses;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentListResponse {
    private List<StudentResponse> students;
    private int totalPages;
}