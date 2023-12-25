package pl.teacherpanel.teacherpanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("")
    public List<Teacher> getAll() {
        return teacherRepository.getTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getById(@PathVariable("id") int id) {
        return teacherRepository.getTeacherById(id).getBody();
    }

    @PostMapping("")
    public ResponseEntity<Integer> add(@RequestBody List<Teacher> teachers) {
        return teacherRepository.addTeacher(teachers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") int id) {
        return teacherRepository.deleteTeacher(id);
    }

    @GetMapping("/csv")
    public ResponseEntity<String> getAllToCSV() {
        return teacherRepository.getTeachersToCSV();
    }

    @PutMapping("/{id}")
    public int update(@PathVariable("id") int id, @RequestBody Teacher updatedTeacher) {
        Teacher teacher = teacherRepository.getTeacherById(id).getBody();
        if(teacher != null)
        {
            teacher.setName(updatedTeacher.getName());
            teacher.setSurname(updatedTeacher.getSurname());
            teacher.setTeacherCondition(updatedTeacher.getTeacherCondition());
            teacher.setBirthday(updatedTeacher.getBirthday());
            teacher.setSalary(updatedTeacher.getSalary());
            teacher.setGroupID(updatedTeacher.getGroupID());

            teacherRepository.updateTeacher(teacher);

            return 1;
        } else {
            return -1;
        }
    }
}