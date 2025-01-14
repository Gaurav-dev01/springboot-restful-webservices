package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    //List<User> getAllUsers();
    //    User getUserById(Long userId);

    //    above commited method using UserDto as follows:
    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);

    //    create user by UserDto
    UserDto createUserByDto(UserDto user);
}
