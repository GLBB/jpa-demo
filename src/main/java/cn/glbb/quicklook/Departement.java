package cn.glbb.quicklook;

import javax.persistence.*;

@Entity
@Table(name = "departement")
public class Departement {
    private int id;
    private String deptName;
    private Manager manager;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id",unique = true)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
