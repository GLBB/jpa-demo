package cn.glbb.quicklook;

import javax.persistence.*;
import java.util.Date;

@NamedQuery(name = "namedQuery",query = "from Customer c WHERE c.id > ?")
@Entity
@Table(name="JPA_Customers")
public class Customer {
    private Integer id;
    private String lastName;
    private String email;
    private int age;

    private Date birthday;
    private Date currentTime;

    public Customer() {
    }

    public Customer(String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Temporal(TemporalType.TIME)
    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_Generator")
    @TableGenerator(name = "ID_Generator",
                    table = "table_id",
                    pkColumnName = "table_name",
                    pkColumnValue = "customer",
                    valueColumnName = "table_value",
                    initialValue = 5,
                    allocationSize = 2)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name ="Last_Name",length = 50,nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Transient
    public String getInfo(){
        return "id: "+id+" lastName: "+lastName+" email: "+email+" age: "+age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", currentTime=" + currentTime +
                '}';
    }
}
