package com.mpt.journal.controller;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


//Основная бизнес-логика нашего проекта
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.findAllStudent()); // просто выгрузка студентов на экран
        return "studentList";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName) {
        StudentModel newStudent = new StudentModel(0, name, lastName, firstName, middleName); // тут мы получаем данные с главных полей, id задается автоматически из нашего репозитория
        studentService.addStudent(newStudent); // добавление студента в оперативную память(после перезапуска проекта, все данные стираются)
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }

    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName) {
        StudentModel updatedStudent = new StudentModel(id, name, lastName, firstName, middleName); // Получаем новые данные из полей для обновления
        studentService.updateStudent(updatedStudent); // Ссылаемся на наш сервис для обновления по id
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id); // Ссылаемся на наш сервис для удаления по id
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }
}
