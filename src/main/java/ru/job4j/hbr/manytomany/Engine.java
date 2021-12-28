package ru.job4j.hbr.manytomany;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "engines")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numberEngine;

    public Engine() {
    }

    public Engine(String numberEngine) {
        this.numberEngine = numberEngine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberEngine() {
        return numberEngine;
    }

    public void setNumberEngine(String numberEngine) {
        this.numberEngine = numberEngine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return id == engine.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
