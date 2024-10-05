package br.com.murilo.tripplans.user;

import br.com.murilo.tripplans.user.api.responses.CustomUserCreatedAPIResponse;
import br.com.murilo.tripplans.user.api.responses.CustomUserDeletedAPIResponse;
import br.com.murilo.tripplans.user.api.responses.CustomUserOKAPIResponse;
import br.com.murilo.tripplans.user.VO.UserVO;
import br.com.murilo.tripplans.user.api.responses.CustomUserUpdateAPIResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/v1")
@Tag(name = "User", description = "Endpoint for managing user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = {"application/json", "application/xml"})
    @CustomUserOKAPIResponse(summary = "Find all users", description = "Find all users in the database")
    public List<UserVO> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @CustomUserOKAPIResponse(summary = "Find one user", description = "Find one user in the database")
    public UserVO findOne(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    @CustomUserCreatedAPIResponse(summary = "Create one user", description = "Create one user in the database")
    public UserVO save(@RequestBody UserVO user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    @CustomUserDeletedAPIResponse(summary = "Delete one user", description = "Delete one user in the database")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    @CustomUserUpdateAPIResponse(summary = "Update one user", description = "Update one user in the database")
    public UserVO update(@RequestBody UserVO user) {
        return userService.update(user);
    }

    @PutMapping(value = "/{id}/generate-code", produces = {"application/json", "application/xml"})
    @CustomUserUpdateAPIResponse(summary = "Generate code for user", description = "Generate code for user in the database")
    public UserVO generateCode(@PathVariable UUID id) {
        return userService.generateCode(id);
    }
}