package restful.api.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restful.api.main.domain.model.User;
import restful.api.main.service.UserService;
import restful.api.main.service.implementation.UserServiceImplementation;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User userToCreate) {

        var userCreated = userService.create(userToCreate);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .buildAndExpand(userToCreate.getId())
                .toUri();
        return ResponseEntity.created(location).body(userCreated);

         //        return ResponseEntity.ok(userService.create(userToCreate));
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") final Long id, @RequestBody User userToUpdate) {
        return ResponseEntity.ok(userService.update(id, userToUpdate));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable final Long id, @RequestBody Map<String, Object> updates) {
        userService.partialUserUpdate(id, updates);
        User updatedUser = userService.findById(id);
        return ResponseEntity.ok(updatedUser);
    }


}
