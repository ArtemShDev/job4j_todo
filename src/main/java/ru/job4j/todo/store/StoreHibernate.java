package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;

public class StoreHibernate implements Store, AutoCloseable {

    private static final StoreHibernate INSTANCE = new StoreHibernate();

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private StoreHibernate() {

    }

    public static StoreHibernate instOf() {
        return INSTANCE;
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setId(id);
        session.update(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findActual() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item where done = false").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item getItem(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        Item item = new Item("Description of Test Item", false);
        Item item1 = new Item("Description of Test Item2", false);
        Item item2 = new Item("Description of Test Item3", false);
        Item item3 = new Item("Description of Test Item4", false);
        Item item4 = new Item("Description of Test Item5", false);
        INSTANCE.add(item);
        INSTANCE.add(item1);
        INSTANCE.add(item2);
        INSTANCE.add(item3);
        INSTANCE.add(item4);
    }
}