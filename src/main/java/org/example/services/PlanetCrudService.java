package org.example.services;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.entities.Planet;
import org.example.util.HibernateUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PlanetCrudService {
    public void create(Planet planet) {
        Transaction transaction;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            session.persist(planet);
            transaction.commit();
        } catch (SessionException e) {
           throw new RuntimeException("Creating a planet is FAILED");
        }
    }

// to list all planets
    public List<Planet> listAll() {
        try (Session session = openSession()) {
            return session.createQuery("from Planet", Planet.class).list();
        }
    }
    public Planet getById(String id) {
        Planet planet;
        try (Session session = openSession()) {
           planet = session.get(Planet.class, id);
        }
        return Optional.ofNullable(planet)
                .orElseThrow(() -> new NoSuchElementException("There is no planet with such id in this DB"));

    }

    // updating planet
    public void update(String id, String name) {
        Transaction transaction;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            Planet planet = session.get(Planet.class, id);
            if (planet != null) {
                planet.setName(name);
                session.persist(planet);
                transaction.commit();
            }
        } catch (SessionException e) {
           throw new RuntimeException();
        }
    }



//deleting planet
    public void deleteById(String id) {
        Transaction transaction;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            Planet planet = session.get(Planet.class, id);
            if (planet != null) {
                session.remove(planet);
            }
            transaction.commit();
        } catch (Exception e) {
           throw new RuntimeException();
        }
    }
    private Session openSession() {
        return HibernateUtil
                .getInstance()
                .getSessionFactory()
                .openSession();
    }
}
