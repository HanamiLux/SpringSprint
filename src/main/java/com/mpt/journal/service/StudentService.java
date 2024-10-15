package com.mpt.journal.service;


import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.model.StudentModel;

import java.util.List;

public interface StudentService {
    public List<StudentModel> findAllStudent();
    public StudentModel findStudentById(int id);
    public StudentModel addStudent(StudentModel student);
    public StudentModel updateStudent(StudentModel student);
    public void deleteStudent(int id);
}
