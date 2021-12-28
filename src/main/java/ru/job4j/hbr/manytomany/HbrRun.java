package ru.job4j.hbr.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Set;

public class HbrRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            List<Author> authors = getAuthors();
            session.save(authors.get(0));
            session.save(authors.get(1));
            session.save(authors.get(2));
            session.getTransaction().commit();
            session.beginTransaction();
            Author author = session.get(Author.class, 14);
            session.remove(author);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static List<Author> getAuthors() {
        Author author = new Author("Dostoevskiy");
        author.addBook(new Book("Idiot"));
        author.addBook(new Book("White Nights"));
        Author author2 = new Author("Professor Ivanov");
        Author author3 = new Author("Professor McLaren");
        Book book = new Book("solid state physics");
        author2.addBook(book);
        author2.addBook(new Book("Dynamics"));
        author3.addBook(book);
        author3.addBook(new Book("Kinematics"));
        return List.of(author, author2, author3);
    }
}
