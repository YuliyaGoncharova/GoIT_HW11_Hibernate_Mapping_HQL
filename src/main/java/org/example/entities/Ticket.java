package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name="ticket")
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinColumn(name = "client_id",  nullable = false)
    private Client clientId;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Planet.class)
    @JoinColumn(name = "from_planet_id", nullable = false, referencedColumnName = "id")
    private Planet from;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Planet.class)
    @JoinColumn(name = "to_planet_id", nullable = false, referencedColumnName = "id")
    private Planet to;

    //added constructors, getters and setters


    public Ticket() {
    }

    public Ticket(long id, Date createdAt, Client clientId, Planet from, Planet to) {
        this.id = id;
        this.createdAt = createdAt;
        this.clientId = clientId;
        this.from = from;
        this.to = to;
    }

//    public Ticket(long id, Date createdAt, Client clientId, org.example.entities.Planet fromPlanet, org.example.entities.Planet toPlanet) {
//        this.id = id;
//        this.createdAt = createdAt;
//        this.clientId = clientId;
//        this.from = fromPlanet;
//        this.to = toPlanet;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }


    public Planet getFrom() {
        return from;
    }

    public void setFrom(Planet from) {
        this.from = from;
    }

    public Planet getTo() {
        return to;
    }

    public void setTo(Planet to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", clientId=" + clientId.getId() +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
