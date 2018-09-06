package cn.glbb.quicklook;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item {
    private int id;
    private String name;
    private Set<Catagory> catagories = new HashSet<>();

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_category",
            joinColumns = {@JoinColumn(name = "item_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "id")})
    public Set<Catagory> getCatagories() {
        return catagories;
    }

    public void setCatagories(Set<Catagory> catagories) {
        this.catagories = catagories;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catagories=" + catagories +
                '}';
    }
}
