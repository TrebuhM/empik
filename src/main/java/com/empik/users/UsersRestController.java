package com.empik.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersRestController {

    private final UsersComponent usersComponent;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                value = "/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok(usersComponent.getUsers(login));
    }

}
