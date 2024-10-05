package br.com.murilo.tripplans.connection.VO;

import br.com.murilo.tripplans.user.User;
import com.github.dozermapper.core.Mapping;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ConnectionVO extends RepresentationModel<ConnectionVO> {
    @Mapping("connection_id")
    private UUID connection_id;
    private UUID user1_id;
    private UUID user2_id;

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
