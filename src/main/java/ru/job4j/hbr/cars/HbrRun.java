package ru.job4j.hbr.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbrRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Brand toyota = new Brand("Toyota");
            addModels(toyota);
            session.save(toyota);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void addModels(Brand brand) {
        Model corolla = new Model("Corolla");
        Model camry = new Model("Camry");
        Model vitz = new Model("Vitz");
        Model yaris = new Model("Yaris");
        Model iq = new Model("IQ");
        brand.addModel(corolla);
        brand.addModel(vitz);
        brand.addModel(camry);
        brand.addModel(yaris);
        brand.addModel(iq);
    }
}
