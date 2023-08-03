package com.natan.hello.controller;

import com.natan.hello.domain.dto.NewUserDto;
import com.natan.hello.domain.dto.UserProfileDto;
import com.natan.hello.domain.model.User;
import com.natan.hello.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    // requisicao/resposta = Verbo + Rota
    // - headers
    // - body
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileDto>> getAllUsers(
            @PageableDefault(size = 5, sort = {"name"}) Pageable pagination
    ) {
        var users = userService.getAll(pagination);

        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserById(@PathVariable Long id) {
        var user = userService.getById(id);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserProfileDto> saveNewUser(
            @RequestBody NewUserDto newUser,
            UriComponentsBuilder uriBuilder
    ) {
        var savedUser = userService.save(newUser);

        if (savedUser == null) {
            return ResponseEntity.badRequest().build();
        }

        var uri = uriBuilder.path("/users/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();

    }

}
