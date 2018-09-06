package cn.glbb.quicklook;

import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {
    private int id;
    private String managerName;
    private Departement dept;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @OneToOne(mappedBy = "manager")
    public Departement getDept() {
        return dept;
    }

    public void setDept(Departement deptName) {
        this.dept = deptName;
    }
}
