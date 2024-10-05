package br.com.murilo.tripplans.connection;

import br.com.murilo.tripplans.user.User;
import br.com.murilo.tripplans.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConnectionServices {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    public Connection save(ConnectionDTO connectionDTO) {
        Optional<User> user1 = userRepository.findByCode(connectionDTO.code);
        Optional<User> user2 = userRepository.findById(connectionDTO.user_b_id);
        if (user1.isPresent() && user2.isPresent()) {
            connectionRepository.findExistingConnection(user1.get().getUserId(), user2.get().getUserId()).ifPresent(connection -> {
                throw new RuntimeException("Connection already exists");
            });
            System.out.println("user1: " + user1.get().getUserId());
            Connection connection = new Connection();
            connection.setUser1_id(user1.get().getUserId());
            connection.setUser2_id(user2.get().getUserId());
            connection.setConnectedAt(LocalDateTime.now());
            connection.setUsers(List.of(user1.get(), user2.get()));
            user1.get().setConnection(connection);
            user2.get().setConnection(connection);
            return connectionRepository.save(connection);
        }
        throw new RuntimeException("User not found");
    }

    public List<Connection> findAll() {
        return connectionRepository.findAll();
    }

    public Connection findOne(UUID id) {
        return connectionRepository.findById(id).orElseThrow();
    }

    public void delete(UUID id) {
        Connection connection = connectionRepository.findById(id).orElseThrow();
        List<User> users = connection.getUsers();
        for (User user : users) {
            user.setConnection(null);
            userRepository.save(user);
        }
        connectionRepository.deleteById(id);
    }
}