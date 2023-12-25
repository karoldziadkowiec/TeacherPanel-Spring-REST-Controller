package pl.teacherpanel.teacherpanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int id;
    private String name;
    private String surname;
    private String teacherCondition;
    private int birthday;
    private float salary;
    private int groupID;
    private String groupName;

    public Teacher(String name, String surname, String teacherCondition, int birthday, float salary, int groupID) {
        this.name = name;
        this.surname = surname;
        this.teacherCondition = teacherCondition;
        this.birthday = birthday;
        this.salary = salary;
        this.groupID = groupID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTeacherCondition() {
        return teacherCondition;
    }

    public void setTeacherCondition(String teacherCondition) {
        this.teacherCondition = teacherCondition;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}