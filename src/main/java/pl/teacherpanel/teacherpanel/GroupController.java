package pl.teacherpanel.teacherpanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    GroupRepository groupRepository;

    @GetMapping("")
    public List<Group> getAll() {
        return groupRepository.getGroups();
    }

    @PostMapping("")
    public ResponseEntity<Integer> add(@RequestBody List<Group> groups) {
        return groupRepository.addGroup(groups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") int id) {
        return groupRepository.deleteGroup(id);
    }

    @GetMapping("/{id}/teacher")
    public List<Teacher> getTeachersFromGroup(@PathVariable("id") int id) {
        return groupRepository.getTeachersFromGroup(id);
    }

    @GetMapping("/{id}/fill")
    public ResponseEntity<String> getGroupFillingPercentage(@PathVariable("id") int id) {
        String fillingPercentage = groupRepository.getGroupFilling(id);

        if ("Error".equals(fillingPercentage)) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(fillingPercentage);
        }
    }
}