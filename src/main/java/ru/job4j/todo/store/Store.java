package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {

    Item add(Item item);

    boolean replace(int id);

    List<Item> findAll();

    List<Item> findActual();

    Item getItem(int id);

}