package ru.job4j.hbr.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbrRun {

    public static void main(String[] args) {
        version1();
        version2();
    }

    private static void version2() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Brand2 toyota = new Brand2("Toyota");
            addModels(toyota);
            session.save(toyota);
            session.getTransaction().commit();
            session.beginTransaction();
            List<Brand2> brands = session.createQuery("select distinct br from Brand2 br join fetch br.models").list();
            for (Brand2 brand : brands) {
                System.out.println(brand.getModels());
            }
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void version1() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Brand2 toyota = new Brand2("Toyota");
            addModels(toyota);
            session.save(toyota);
            session.getTransaction().commit();
            session.beginTransaction();
            List<Brand2> brands = session.createQuery("from Brand2").list();
            for (Brand2 brand : brands) {
                System.out.println(brand.getModels());
            }
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void addModels(Brand2 brand) {
        Model2 corolla = new Model2("Corolla");
        Model2 camry = new Model2("Camry");
        Model2 yaris = new Model2("Yaris");
        brand.addModel(corolla);
        brand.addModel(camry);
        brand.addModel(yaris);
        corolla.setBrand(brand);
        camry.setBrand(brand);
        yaris.setBrand(brand);
    }
}
