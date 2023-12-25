package pl.teacherpanel.teacherpanel;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.opencsv.CSVWriter;

@Repository
public class TeacherRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Teacher> getTeachers() {
        return jdbcTemplate.query("SELECT * FROM teachers", BeanPropertyRowMapper.newInstance(Teacher.class));
    }

    public ResponseEntity<Teacher> getTeacherById(int id) {
        try {
            Teacher teacher = jdbcTemplate.queryForObject("SELECT * FROM teachers WHERE id = ?",
                    BeanPropertyRowMapper.newInstance(Teacher.class), id);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Integer> addTeacher(List<Teacher> teachers) {
        try {
            teachers.forEach(teacher -> jdbcTemplate.update(
                    "INSERT INTO teachers(name, surname, teacherCondition, birthday, salary, groupID) VALUES (?, ?, ?, ?, ?, ?)",
                    teacher.getName(), teacher.getSurname(), teacher.getTeacherCondition(), teacher.getBirthday(),
                    teacher.getSalary(), teacher.getGroupID()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> deleteTeacher(int id) {
        int result = jdbcTemplate.update("DELETE FROM teachers WHERE id = ?", id);
        return (result > 0) ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> getTeachersToCSV() {
        List<Teacher> teachers = jdbcTemplate.query("SELECT * FROM teachers",
                BeanPropertyRowMapper.newInstance(Teacher.class));

        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String filePath = "teachers.csv";

        try (FileWriter fileWriter = new FileWriter(filePath); CSVWriter csvWriter = new CSVWriter(fileWriter)) {
            String[] headers = {"id", "name", "surname", "teacherCondition", "birthday", "salary", "groupID"};
            csvWriter.writeNext(headers);

            for (Teacher teacher : teachers) {
                String[] data = {
                        String.valueOf(teacher.getId()),
                        teacher.getName(),
                        teacher.getSurname(),
                        teacher.getTeacherCondition(),
                        String.valueOf(teacher.getBirthday()),
                        String.valueOf(teacher.getSalary()),
                        String.valueOf(teacher.getGroupID())
                };
                csvWriter.writeNext(data);
            }

            return new ResponseEntity<>(filePath, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> updateTeacher(Teacher teacher) {
        int result = jdbcTemplate.update(
                "UPDATE teachers SET name = ?, surname = ?, teacherCondition = ?, birthday = ?, salary = ?, groupID = ? WHERE id = ?",
                teacher.getName(), teacher.getSurname(), teacher.getTeacherCondition(), teacher.getBirthday(),
                teacher.getSalary(), teacher.getGroupID(), teacher.getId());

        return (result > 0) ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
