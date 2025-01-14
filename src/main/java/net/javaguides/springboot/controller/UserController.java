package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.exception.ErrorDetails;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    //    @Autowired
    private UserService userService;

    //    create user API
    /*@PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }*/

    //    above method using Java 8
    /*@PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());

        return Optional.ofNullable(user)
                .map(userService::createUser)
                .map(savedUser -> new ResponseEntity<>(savedUser, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }*/

    //    above code using UserDto(c)
    @PostMapping("createByDto")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        UserDto savedUser = userService.createUserByDto(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //    Get user by id
/*    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User getById = userService.getUserById(id);

        return ResponseEntity.ok(getById);
    }*/

    //    above code using UserDto
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto getById = userService.getUserById(id);

        return ResponseEntity.ok(getById);
    }

    //    Get all users
/*    @GetMapping("getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> listAllUsers = userService.getAllUsers();

        return ResponseEntity.ok(listAllUsers);
    }*/

    //    above code using UserDto
    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> listAllUsers = userService.getAllUsers();
        return ResponseEntity.ok(listAllUsers);
    }

    //    update user
    @PutMapping("updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);

        return ResponseEntity.ok(updatedUser);

    }

    //    delete user
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        System.out.println("User: " + id + " successfully deleted.!");
        return ResponseEntity.ok("User ID: " + id + " successfully deleted.!");
    }

}
