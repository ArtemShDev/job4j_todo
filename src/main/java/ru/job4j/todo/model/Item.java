package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date(System.currentTimeMillis());
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();

    public Item() {
    }

    public Item(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public Item(int id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public Item(String description, boolean done, User user) {
        this.description = description;
        this.done = done;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategories(String[] ids) {
        String[] idsCat = ids[0].split(",");
        for (String str : idsCat) {
            categories.add(new Category(Integer.parseInt(str)));
        }
    }
}
