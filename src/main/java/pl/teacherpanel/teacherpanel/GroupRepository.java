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
public class GroupRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Group> getGroups() {
        return jdbcTemplate.query("SELECT * FROM groups", BeanPropertyRowMapper.newInstance(Group.class));
    }

    public ResponseEntity<Integer> addGroup(List<Group> groups) {
        try {
            groups.forEach(group -> jdbcTemplate.update(
                    "INSERT INTO groups(name, groupLimit) VALUES (?, ?)",
                    group.getName(), group.getGroupLimit()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> deleteGroup(int id) {
        int result = jdbcTemplate.update("DELETE FROM groups WHERE id = ?", id);
        return (result > 0) ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Teacher> getTeachersFromGroup(int id) {
        String sql = "SELECT t.*, g.name AS groupName FROM teachers t JOIN groups g ON t.groupID = g.id WHERE g.id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Teacher.class));
    }

    public String getGroupFilling(int id) {
        try {
            String groupNameSql = "SELECT name FROM groups WHERE id = ?";
            String groupName = jdbcTemplate.queryForObject(groupNameSql, String.class, id);

            String teacherCountSql = "SELECT COUNT(*) FROM teachers WHERE groupID = ?";
            int teacherCount = jdbcTemplate.queryForObject(teacherCountSql, Integer.class, id);

            String groupLimitSql = "SELECT groupLimit FROM groups WHERE id = ?";
            int groupLimit = jdbcTemplate.queryForObject(groupLimitSql, Integer.class, id);

            if (groupLimit == 0) {
                return groupName + ": 0.0%";
            }

            double fillingPercentage = ((double) teacherCount / groupLimit) * 100.0;
            return String.format("%s: %.1f%%", groupName, fillingPercentage);
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "Error"; // Adjust error handling as needed
        }
    }
}