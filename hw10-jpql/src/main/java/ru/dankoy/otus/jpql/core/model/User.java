package ru.dankoy.otus.jpql.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ezelenin
 * <p>
 * Памятка.
 * Запись JoinColumn указывает новый столбец
 */
@Entity
@Table(name = "tUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AddressDataSet address = new AddressDataSet();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PhoneDataSet> phoneDataSets = new ArrayList<>();

    public User() {

    }

    public User(String name, int age, AddressDataSet address, List<PhoneDataSet> phoneDataSets) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneDataSets = phoneDataSets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phoneDataSets=" + phoneDataSets +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhoneDataSets(List<PhoneDataSet> phoneDataSets) {
        this.phoneDataSets = phoneDataSets;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public List<PhoneDataSet> getPhoneDataSets() {
        return phoneDataSets;
    }

}
