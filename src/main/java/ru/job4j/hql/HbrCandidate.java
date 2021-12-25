package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

public class HbrCandidate {

    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Candidate candidate1 = new Candidate("Java Junior", "1 year", 100);
        Candidate candidate2 = new Candidate("Java Middle", "2 year", 200);
        addFirst(candidate1);
        add(candidate1);
        add(candidate1);
        addFirst(candidate2);
        replace(candidate2);
        delete(candidate1);
        System.out.println(findAll());
    }

    private static void addFirst(Candidate candidate) {
        tx(session -> session.save(candidate));
    }

    private static boolean add(Candidate candidate) {
      return tx(session -> session.createQuery("insert into Candidate(name, experience, salary) "
              + " select c.name, c.experience, c.salary*2 from Candidate c where c.id = :id")
              .setParameter("id", candidate.getId()).executeUpdate()) > 0;
    }

    private static boolean replace(Candidate candidate) {
        return tx(session -> session.createQuery("update Candidate set name = concat(name, 'LAST') "
                + "where id = :id").setParameter("id", candidate.getId()).executeUpdate()) > 0;
    }

    private static List<Candidate> findAll() {
        return tx(session -> session.createQuery("from Candidate").list());
    }

    private static boolean delete(Candidate candidate) {
        return tx(session -> session.createQuery("delete from Candidate where id = :id")
        .setParameter("id", candidate.getId()).executeUpdate()) > 0;
    }

    private static <T> T tx(final Function<Session, T> command) {
        final Session session = SF.openSession();
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
}
