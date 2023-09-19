package org.example;

import org.example.entities.Client;
import org.example.entities.Planet;
import org.example.entities.Ticket;
import org.example.services.ClientCrudService;
import org.example.services.PlanetCrudService;
import org.example.services.TicketCrudService;
import org.example.util.DatabaseMigration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseMigration migration = new DatabaseMigration();
        migration.initDb();

        ClientCrudService clientCrudService = new ClientCrudService();
        PlanetCrudService planetCrudService = new PlanetCrudService();
        TicketCrudService ticketCrudService = new TicketCrudService();

        //creating new planets, clients

        Client client1 = new Client();
        client1.setName("Melissa Cooper");
        clientCrudService.create(client1);

        Client client2 = new Client();
        client2.setName("Pastor Jeff");
        clientCrudService.create(client2);


//        Planet planet1 = new Planet();
//        planet1.setId("NEP7");
//        planet1.setName("Neptune");
//
//        Planet planet2 = new Planet();
//        planet2.setId("PLU8");
//        planet2.setName("Pluto");
//
//        planetCrudService.create(planet1);
//        planetCrudService.create(planet2);


        // creating tickets
        Ticket ticket = ticketCrudService.createTicket(
                clientCrudService.getById(2),
                planetCrudService.getById("SAT6"),
                planetCrudService.getById("MARS4"));
        System.out.println("Ticket created : " + ticket);
//        VEN2

        // updating tickets
        ticketCrudService.updateTicket(ticket.getId(), clientCrudService.getById(2), planetCrudService.getById("SAT6"), planetCrudService.getById("MARS4"));
        System.out.println("Ticked updated : " + ticketCrudService.getById(2));


//        long clientId = 2;
//        List<Ticket> tickets = ticketCrudService.getTicketsForClient(clientId);
//        for (Ticket ticket : tickets) {
//            System.out.println("ID of ticket : " + ticket);
//        }
//
//
//        // deleting ticket by id
//        long idTicket = 3;
//        ticketCrudService.deleteById(idTicket);
//        System.out.println("Ticket with ID " + idTicket + "is deleted");
//
        //listing all tickets
        System.out.println("List of all tickets : ");
        ticketCrudService.listAll().forEach(System.out::println);

        System.out.println("List of all clients : ");
        List<Client> clients = clientCrudService.listAll();
        for (Client client:clients) {
            System.out.println(client);
        }


//        не можна зберегти квиток для неіснуючого або null клієнта :
        try {
            ticketCrudService.createTicket(null, planetCrudService.getById("SAT6"), planetCrudService.getById("MARS4"));
            System.out.println("Test witn null client FAILED");
        } catch (Exception e) {
            System.out.println("Test witn null client ok");
        }

//      не можна зберегти квиток для неіснуючої або null планети

        try {
            ticketCrudService.createTicket(client1, null, planetCrudService.getById("MARS4"));
            System.out.println("Test with null planet is FAILED");
        } catch (Exception e) {
            System.out.println("Test with null planet is OK");
        }
    }


}