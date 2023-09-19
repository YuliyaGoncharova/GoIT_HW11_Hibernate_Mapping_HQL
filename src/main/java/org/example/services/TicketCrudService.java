package org.example.services;


import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.entities.Client;
import org.example.entities.Planet;
import org.example.entities.Ticket;
import org.example.util.HibernateUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TicketCrudService {
    public Ticket createTicket(Client client, Planet fromPlanet, Planet toPlanet) {
        Ticket result;

        Ticket ticket = new Ticket();
        ticket.setClientId(client);
        ticket.setFrom(fromPlanet);
        ticket.setTo(toPlanet);
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.merge(ticket);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Creating a ticket is FAILED: " + e.getMessage());
        }

        return result;
    }

    //getting a ticket by id
    public Ticket getById(long id) {
        Ticket ticket;
        try (Session session = openSession()) {
            {
                ticket = session.get(Ticket.class, id);
            }
            return Optional.ofNullable(ticket)
                    .orElseThrow(() -> new NoSuchElementException("There is no ticket with such id in this DB"));
        }
    }

    //listing all tickets
    public List<Ticket> listAll() {
        try (Session session = openSession()) {
            return session.createQuery("from Ticket ", Ticket.class).list();
        }
    }
//updating a ticket
    public void updateTicket(long id, Client clientId, Planet from, Planet to) {
        try (Session session = openSession()) {
            Transaction  transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                ticket.setClientId(clientId);
                ticket.setFrom(from);
                ticket.setTo(to);
                session.persist(ticket);
                transaction.commit();
            } else {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("There is no ticket with such id in this DB");
        }
    }

    //deleting ticket
    public void deleteById(long id) {
        Ticket ticket;
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                session.remove(ticket);
                transaction.commit();
            }
        }
        catch (SessionException e) {
            throw new RuntimeException();
        }
    }

    public List<Ticket> getTicketsForClient(long clientId) {
        try (Session session = openSession()) {
            Query<Ticket> query = session.createQuery(
                    "SELECT t FROM Ticket t " +
                            "WHERE t.clientId.id = :clientId", Ticket.class
            );
            query.setParameter("clientId", clientId);
            return query.list();
        }
    }


    private Session openSession() {
        return HibernateUtil
                .getInstance()
                .getSessionFactory()
                .openSession();
    }
}
