package freecoding.entity;


import javax.persistence.*;

/**
 * Created by zhujing on 2017/7/14.
 */

@Entity
@Table(name = "test")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;




    @Column(name="name")

    private String name;

    @Column(name="age")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
