package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {

    Item add(Item item);

    boolean addUser(User user);

    boolean replace(int id);

    List<Item> findItems(String all);

    List<Item> findUserItems(String email, String all);

    Item getItem(int id);

    User findUser(String email);

    List<Category> findCategories();
}