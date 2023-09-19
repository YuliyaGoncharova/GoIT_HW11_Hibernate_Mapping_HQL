package org.example.services;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.entities.Client;
import org.example.util.HibernateUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClientCrudService {

    public void create(Client client) {
        Transaction transaction;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Creating a user is FAILED");

        }
    }

    public Client getById(long id) {
        Client client;
        try (Session session = openSession()) {
            client = session.get(Client.class, id);
        }
        return Optional.ofNullable(client)
                .orElseThrow(() -> new NoSuchElementException("There is no the client with such id in this DB"));
    }

    //    to list all entities
    public List<Client> listAll() {
        try (Session session = openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }

    // to update a client with this id
    public void update(long id, String name) {
        Transaction transaction;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                client.setName(name);
                session.persist(client);
                transaction.commit();
            }


        } catch (SessionException e) {
           throw new RuntimeException();
        }
    }
    public void deleteById(long id) {
        Transaction transaction;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.remove(client);
            }
            transaction.commit();
        } catch (SessionException e) {
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

