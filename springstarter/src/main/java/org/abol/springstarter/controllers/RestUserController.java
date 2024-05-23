package org.abol.springstarter.controllers;

import org.abol.springstarter.models.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class RestUserController {

    private List<BaseUser> users = new ArrayList<>();

    @PostMapping("/create")
    public ResponseEntity<BaseUser> createUser(@RequestBody BaseUser baseUser) {
        users.add(baseUser);
        log.info("User created: {}", baseUser);
        return ResponseEntity.ok(baseUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseUser> getUserById(@PathVariable("id") int id) {
        if (id >= 0 && id < users.size()) {
            BaseUser user = users.get(id);
            log.info("Displaying details for user with ID {}", id);
            return ResponseEntity.ok(user);
        } else {
            log.warn("No user found with ID {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseUser> updateUser(@PathVariable("id") int id, @RequestBody BaseUser baseUser) {
        if (id >= 0 && id < users.size()) {
            users.set(id, baseUser);
            log.info("User updated: {}", baseUser);
            return ResponseEntity.ok(baseUser);
        } else {
            log.warn("No user found with ID {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        if (id >= 0 && id < users.size()) {
            log.info("Deleting user at index {}", id);
            users.remove(id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("No user found with ID {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
