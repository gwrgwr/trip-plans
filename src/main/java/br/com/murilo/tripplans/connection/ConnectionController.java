package br.com.murilo.tripplans.connection;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/connection/v1")
@Tag(name = "Connection", description = "Endpoint for managing connection")
public class ConnectionController {

    @Autowired
    private ConnectionServices connectionServices;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Connection save(@RequestBody ConnectionDTO connectionDTO) {
        return connectionServices.save(connectionDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Connection> findAll() {
        return connectionServices.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Connection findOne(@PathVariable UUID id) {
        return connectionServices.findOne(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable UUID id) {
        connectionServices.delete(id);
    }
}