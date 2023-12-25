package pl.teacherpanel.teacherpanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.teacherpanel.teacherpanel.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TeacherRepository teacherRepository;


    // Test dla 1)POST: /api/teacher - dodaje nauczyciela
    @Test
    public void testAddTeacher() throws Exception {
        Teacher teacher = new Teacher("James", "Bond", "PRESENT", 2000, 5000.0F, 3);

        mockMvc.perform(post("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(teacher))))
                .andExpect(status().isOk());
    }

    // Test dla 2)DELETE: /api/teacher/:id - usuwa nauczyciela
    @Test
    public void testDeleteTeacher() throws Exception {
        int teacherId = 1;

        mockMvc.perform(delete("/api/teacher/{id}", teacherId))
                .andExpect(status().isOk());
    }

    // Test dla 3)GET: /api/teacher/csv - zwraca wszystkich nauczycieli w formie pliku CSV
    @Test
    public void testGetTeachersToCSV() throws Exception {
        List<Teacher> teachers = Arrays.asList(
                new Teacher("James", "Bond", "PRESENT", 2000, 5000.0F, 1),
                new Teacher("Jane", "Smith", "ABSENT", 1995, 6000.0F, 2)
        );

        ResponseEntity<String> responseEntity = new ResponseEntity<>("CSV Test", HttpStatus.OK);

        Mockito.when(teacherRepository.getTeachersToCSV()).thenReturn(responseEntity);

        mockMvc.perform(get("/api/teacher/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string("CSV Test"));
    }
}