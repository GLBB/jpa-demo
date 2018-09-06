package cn.glbb.quicklook;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {
    private int id;
    private String name;
    private Set<Student> students = new HashSet<>();

    @Id
    @GeneratedValue
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

    @OneToMany(cascade = {CascadeType.REMOVE},mappedBy = "teacher")
//    @JoinColumn(name = "teacher_id")
    // 双向一对多
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
