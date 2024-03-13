package com.adaSchool.Proyecto.Integrador.controller.user;

import com.adaSchool.Proyecto.Integrador.exception.UserNotFoundException;
import com.adaSchool.Proyecto.Integrador.repository.user.User;
import com.adaSchool.Proyecto.Integrador.repository.user.UserDto;
import com.adaSchool.Proyecto.Integrador.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users/")
public class UsersController  {

    private final UsersService usersService;

    public UsersController(@Autowired @Qualifier("userServiceImplementsMongoDB")UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User newUser = new User(userDto);
        usersService.save(newUser);
        URI createdUserUri = URI.create("/v1/users/"+newUser.getId());
        return ResponseEntity.created(createdUserUri).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable("id") String id) {
        Optional<User> user = usersService.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        Optional<User> optionalUser = usersService.findById(id);
        if (optionalUser.isEmpty()) throw new UserNotFoundException(id);
        User user = optionalUser.get();
        user.update(userDto);
        usersService.save(user);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        if (usersService.findById(id).isEmpty()) throw new UserNotFoundException(id);
        usersService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
