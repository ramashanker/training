package com.rama.app.controller;

import com.rama.app.student.Student;
import com.rama.app.student.StudentNotFoundException;
import com.rama.app.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> retrieveAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student retrieveStudent(@PathVariable long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent()) {
            throw new StudentNotFoundException("id-" + id);
        }

        return student.get();
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentRepository.deleteById(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.noContent()
                             .build();
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id) {

        Optional<Student> studentOptional = studentRepository.findById(id);

        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound()
                                 .build();
        }

        student.setId(id);

        studentRepository.save(student);

        return ResponseEntity.noContent()
                             .build();
    }
}
