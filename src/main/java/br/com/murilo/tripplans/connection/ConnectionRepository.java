package br.com.murilo.tripplans.connection;

import br.com.murilo.tripplans.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, UUID> {
    @Query("SELECT c FROM Connection c WHERE c.user1_id = ?1 OR c.user2_id = ?2")
    Optional<User> findExistingConnection(UUID user1_id, UUID user2_id);
}
