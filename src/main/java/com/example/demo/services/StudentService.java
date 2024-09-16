package com.example.demo.services;

import com.example.demo.dtos.StudentDTO;
import com.example.demo.dtos.StudentImageDTO;
import com.example.demo.models.Student;
import com.example.demo.models.StudentImage;
import com.example.demo.models.XepLoai;
import com.example.demo.repositories.StudentImageRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.responses.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService{
    private final StudentRepository studentRepository;
    private final StudentImageRepository studentImageRepository;
    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(StudentDTO studentDTO) {
        Student student = Student.builder()
                .name(studentDTO.getName())
                .xepLoai(XepLoai.fromXl(studentDTO.getXepLoai()))
                .thanhPho(studentDTO.getThanhPho())
                .ngaySinh(studentDTO.getNgaySinh())
                .build();
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Student student = getStudentById(id);
        student.setName(studentDTO.getName());
        student.setNgaySinh(studentDTO.getNgaySinh());
        student.setXepLoai(XepLoai.fromXl(studentDTO.getXepLoai()));
        student.setThanhPho(studentDTO.getThanhPho());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Page<StudentResponse> getStudents(PageRequest pageable) {
        return studentRepository.findAll(pageable).map(student -> {
            return StudentResponse.fromStudent(student);
        });
    }

    @Override
    public List<Student> findByTenContainingIgnoreCase(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> findByThanhPho(String name) {
        return studentRepository.findByThanhPho(name);
    }

    @Override
    public List<Student> findByThanhPhoAndTen(String name) {
        return studentRepository.findByThanhPhoAndTen(name);
    }

    @Override
    public List<Student> findByXepLoai(XepLoai xepLoai) {
        return studentRepository.findByXepLoai(xepLoai);
    }

    @Override
    public List<Student> findByYearNgaySinhBetween(int startYear, int endYear) {
        return studentRepository.findByYearNgaySinhBetween(startYear, endYear);
    }

    @Override
    public List<Student> search(String name, XepLoai xepLoai, String thanhPho, int startYear, int endYear) {
        return studentRepository.search( name,xepLoai , thanhPho,startYear, endYear);
    }

    @Override
    public StudentImage saveStudentImage(Long studentId, StudentImageDTO studentImageDTO) {
        Student student =getStudentById(studentId);
        StudentImage studentImage = StudentImage.builder()
                .student(student)
                .imgUrl(studentImageDTO.getImgUrl())
                .build();
        int size=studentImageRepository.findAllByStudentIdTemp(studentId).size();
        if (size>=4){
            throw new InvalidParameterException("Mỗi sinh viên có tối đa 4 hình");
        }

        return studentImageRepository.save(studentImage);
    }

    @Override
    public List<StudentImage> getAllStudentImages(Long studentId) {
        return studentImageRepository.findAllByStudentIdTemp(studentId);
    }
}
