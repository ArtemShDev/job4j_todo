package ru.job4j.hbr.lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "models2")
public class Model2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand2 brand;

    public Model2() {
    }

    public Model2(String name) {
        this.name = name;
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

    public Brand2 getBrand() {
        return brand;
    }

    public void setBrand(Brand2 brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model2 model = (Model2) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Model2{" + "id=" + id + ", name='" + name + '\'' + ", brand=" + brand + '}';
    }
}
