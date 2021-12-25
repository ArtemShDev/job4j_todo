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

    private final StandardServiceRegistry registry;
    private final SessionFactory sf;

    private StoreHibernate() {
        registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final Store INST = new StoreHibernate();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Item add(Item item) {
        tu(session -> session.save(item));
        return item;
    }

    @Override
    public boolean replace(int id) {
        return tu(session -> session.createQuery("update Item set done = true where id = :id")
                .setParameter("id", id).executeUpdate());
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
}