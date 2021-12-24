package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
        tu(session -> session.save(item));
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        item.setId(id);
        return tu(session -> session.update(item));
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item").list());
    }

    @Override
    public List<Item> findActual() {
        return tx(session -> session.createQuery("from Item where done = false").list());
    }

    @Override
    public Item getItem(int id) {
        return tx(session -> session.get(Item.class, id));
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private boolean tu(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
            return true;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
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