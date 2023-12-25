package pl.teacherpanel.teacherpanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.teacherpanel.teacherpanel.Group;
import pl.teacherpanel.teacherpanel.GroupController;
import pl.teacherpanel.teacherpanel.GroupRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GroupRepository groupRepository;


    // Test dla 4)GET: /api/group - zwraca wszystkie grupy nauczycielskie
    @Test
    public void testGetAllGroups() throws Exception {
        List<Group> groups = Arrays.asList(
                new Group("Polish", 3),
                new Group("French", 5)
        );

        Mockito.when(groupRepository.getGroups()).thenReturn(groups);

        mockMvc.perform(get("/api/group"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(groups)));
    }

    // Test dla 5)POST: /api/group - dodaje nową grupę nauczycielską
    @Test
    public void testAddNewGroup() throws Exception {
        Group newGroup = new Group("Spanish", 5);

        mockMvc.perform(post("/api/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(newGroup))))
                .andExpect(status().isOk());
    }

    // Test dla 6)DELETE: /api/group/:id - usuwa grupę nauczycielską
    @Test
    public void testDeleteGroup() throws Exception {
        int groupId = 1;

        mockMvc.perform(delete("/api/group/{id}", groupId))
                .andExpect(status().isOk());
    }

    // Test dla 7)GET: /api/group/:id/teacher - zwraca wszystkich nauczycieli z danej grupy
    @Test
    public void testGetTeachersInGroup() throws Exception {
        int groupId = 1;
        List<Teacher> teachers = Arrays.asList(
                new Teacher("James", "Bond", "PRESENT", 2000, 5000.0F, groupId),
                new Teacher("Jane", "Smith", "ABSENT", 1995, 6000.0F, groupId)
        );

        Mockito.when(groupRepository.getTeachersFromGroup(groupId)).thenReturn(teachers);

        mockMvc.perform(get("/api/group/{id}/teacher", groupId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(teachers)));
    }

    // Test dla 8)GET: /api/group/:id/fill - zwraca zapełnienie procentowe danej grupy
    @Test
    public void testGetGroupFillPercentage() throws Exception {
        int groupId = 1;
        double fillPercentage = 75.0;

        Mockito.when(groupRepository.getGroupFilling(groupId)).thenReturn(String.valueOf(fillPercentage));

        mockMvc.perform(get("/api/group/{id}/fill", groupId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(fillPercentage)));
    }
}