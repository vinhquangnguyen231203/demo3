package com.example.demo.controllers;


import com.example.demo.dtos.StudentDTO;
import com.example.demo.dtos.StudentImageDTO;
import com.example.demo.exceptions.ResourceNtFoundException;
import com.example.demo.models.Student;
import com.example.demo.models.StudentImage;
import com.example.demo.models.XepLoai;
import com.example.demo.responses.ApiResponse;
import com.example.demo.responses.StudentListResponse;
import com.example.demo.responses.StudentResponse;
import com.example.demo.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;
    @GetMapping("")
    public ResponseEntity<ApiResponse> index(){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("OKE")
                .data(studentService.getAllStudents())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
//    public ResponseEntity<List<Student>> getAllStudent(){
//        return ResponseEntity.ok().body(studentService.getAllStudents());
//    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> indexID(@PathVariable("id") Long id){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("OKE")
                .data(studentService.getStudentById(id))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
//    public ResponseEntity<Student> getStudentByID(@PathVariable("id") Long id){
//        return ResponseEntity.ok().body(studentService.getStudentById(id));
//    }



    @PostMapping("")
    public ResponseEntity<ApiResponse> postStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult result){
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Insert successfully" )
                .data(StudentResponse.fromStudent(studentService.saveStudent(studentDTO)))
                .build();
        return ResponseEntity.ok().body(apiResponse);

    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO, BindingResult result){
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation failed")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        Student student = studentService.updateStudent(id, studentDTO);
        if(student == null){
            throw new ResourceNtFoundException("Student không tìm thấy với id: "+id);
        }
        ApiResponse apiResponse =ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Update successfully" )
                .data(StudentResponse.fromStudent(student))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable ("id") Long id){

        Student student = studentService.getStudentById(id);
        if (student == null){
            throw new ResourceNtFoundException("Student không tìm thấy id: "+ id);
        }
        studentService.deleteStudent(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(null)
                .message("Delete thành công")
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }


    @GetMapping("/search1")
    public ResponseEntity<ApiResponse> search1(@RequestParam String name){
        ApiResponse apiResponse =ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.findByTenContainingIgnoreCase(name))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/search2")
    public ResponseEntity<ApiResponse> search2(@RequestParam String name){
        ApiResponse apiResponse =ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.findByThanhPho(name))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/search3")
    public ResponseEntity<ApiResponse> search3(@RequestParam String name){
        ApiResponse apiResponse =ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.findByThanhPhoAndTen(name))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllStudents(
            @RequestParam(value = "page", defaultValue = "0")  int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit){
        PageRequest pageable = PageRequest.of(
                page, limit,
                Sort.by("createAt").descending()
        );
        Page<StudentResponse> studentResponsePage = studentService.getStudents(pageable);
        // studentResponsePage chứ thoong tin của page thứ maasy và tổng số lượng page
        int totalPages = studentResponsePage.getTotalPages();
        List<StudentResponse> studentResponses = studentResponsePage.getContent();
        StudentListResponse studentListResponse = StudentListResponse.builder()
                .students(studentResponses)
                .totalPages(totalPages)
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Show Student successfully")
                .data(studentListResponse)
                .build();
        return ResponseEntity.ok().body(apiResponse);

    }
    @GetMapping("/searchXepLoai")
    public ResponseEntity<ApiResponse> searchXepLoai(@RequestParam("xepLoai") String xepLoaistr){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.findByXepLoai(XepLoai.fromXl(xepLoaistr)))
                .build();
        return ResponseEntity.ok().body(apiResponse);
//        @RequestParam(value = "page", defaultValue = "0")  int page,
//        @RequestParam(value = "limit", defaultValue = "5") int limit){
//            PageRequest pageable = PageRequest.of(
//                    page, limit,
//                    Sort.by("createAt").descending()
//            );
//            StudentService sr = (StudentService) studentService.findByXepLoai((XepLoai.fromXl(xepLoaistr)));
//
//            Page<StudentResponse> studentResponsePage = sr.getStudents(pageable);
//            // studentResponsePage chứ thoong tin của page thứ maasy và tổng số lượng page
//            int totalPages = studentResponsePage.getTotalPages();
//            List<StudentResponse> studentResponses = studentResponsePage.getContent();
//            StudentListResponse studentListResponse = StudentListResponse.builder()
//                    .students(studentResponses)
//                    .totalPages(totalPages)
//                    .build();
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .message("Show Student successfully")
//                    .data(studentListResponse)
//                    .build();
//            return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/searchByYearBetween")
    public ResponseEntity<ApiResponse> searchByYearBetween(@RequestParam int startYear,@RequestParam int endYear){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.findByYearNgaySinhBetween(startYear, endYear))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "xepLoai", required = false) String xepLoai,
            @RequestParam(value = "thanhPho", required = false) String thanhPho,
            @RequestParam(value = "startYear", required = false) int startYear,
            @RequestParam(value = "endYear", required = false) int endYear){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.search(  name,XepLoai.fromXl(xepLoai),thanhPho, startYear, endYear))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/getImg/{id}")
    public ResponseEntity<ApiResponse> getImg(@PathVariable Long id){
        ApiResponse apiResponse =ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Search successfully")
                .data(studentService.getAllStudentImages(id))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping(value = "/uploadImg/{id}")
    public ResponseEntity<ApiResponse> uploadImg(@PathVariable Long id, @ModelAttribute("files") List<MultipartFile> files)  {

//        String fileName= storeFile(files);
//        StudentImageDTO studentImageDTO =StudentImageDTO.builder()
//                .imgUrl(fileName)
//                .build();
//
//        ApiResponse apiResponse =ApiResponse.builder()
//                .status(HttpStatus.OK.value())
//                .message("Update successfully" )
//                .data(studentService.saveStudentImage(id, studentImageDTO))
//                .build();
//        return ResponseEntity.ok().body(apiResponse);

        List<StudentImage> studentImages = new ArrayList<>();
        int count =0;
        for(MultipartFile file: files){
            if (file!=null){
                if (file.getSize()==0){
                    count++;
                    continue;
                }
                String fileName= null;
                try {
                    fileName = storeFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                StudentImageDTO studentImageDTO =StudentImageDTO.builder()
                        .imgUrl(fileName)
                        .build();
                StudentImage studentImage= studentService.saveStudentImage(id, studentImageDTO);
                studentImages.add(studentImage);
            }

        }
        if (count==1){
            throw new IllegalArgumentException("Vui lòng chọn file....");
        }


        ApiResponse apiResponse =ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Update successfully" )
                .data(studentImages)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString()+"_"+filename;
        java.nio.file.Path uploadDdir = Paths.get("upload");
        if( !Files.exists(uploadDdir)){
            Files.createDirectory(uploadDdir);
        }
        java.nio.file.Path destination = Paths.get(uploadDdir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    
}
