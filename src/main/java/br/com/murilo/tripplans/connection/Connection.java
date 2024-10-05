package br.com.murilo.tripplans.connection;

import br.com.murilo.tripplans.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Connection {
    @Id
    @GeneratedValue
    @Column(name = "connection_id", updatable = false, nullable = false)
    private UUID connection_id;


    @Column(name = "user1_id", nullable = false)
    private UUID user1_id;
    @Column(name = "user2_id", nullable = false)
    private UUID user2_id;

    @OneToMany(mappedBy = "connection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;

    private LocalDateTime connectedAt;

    public UUID getId() {
        return connection_id;
    }

    public void setId(UUID id) {
        this.connection_id = id;
    }

    public LocalDateTime getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(LocalDateTime connectedAt) {
        this.connectedAt = connectedAt;
    }

    public UUID getConnection_id() {
        return connection_id;
    }

    public void setConnection_id(UUID connection_id) {
        this.connection_id = connection_id;
    }

    public UUID getUser1_id() {
        return user1_id;
    }

    public void setUser1_id(UUID user1_id) {
        this.user1_id = user1_id;
    }

    public UUID getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(UUID user2_id) {
        this.user2_id = user2_id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}