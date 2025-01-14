package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.exception.EmailAlreadyExistsException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    //    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

//   ========== Get user by ID  ================

    /*    @Override
        public User getUserById(Long userId) {
            Optional<User> optionalUser = userRepository.findById(userId);

            return optionalUser.get();
        }*/
//    above code using UserDto
/*    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.get();

        return UserMapper.maptoUserDto(user);
    }*/

//    above code using exception handling

    @Override
    public UserDto getUserById(Long userId) {
        User optionalUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

//        User user = optionalUser.get();

        return UserMapper.maptoUserDto(optionalUser);
    }


    /*@Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }*/

    //    above code using UserDto
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::maptoUserDto).collect(Collectors.toList());
    }

    /*    @Override
        public User updateUser(User user) {
            User existingUser = userRepository.findById(user.getId()).get();

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());

            return userRepository.save(existingUser);

        }*/
//above code using exception handling
    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);

    }

/*    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }*/

//    above method using exception handling

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.deleteById(userId);
    }

    //    create user by UserDto
    @Override
    public UserDto createUserByDto(UserDto userDto) {
/*//        convert UserDto into User JPA entity
        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );
        User savedUser = userRepository.save(user);
//      above code using UserMapper(c) as follows:

        //convert User JPA entity into UserDto
        UserDto savedUserDto = new UserDto(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail()
        );*/
//      above code using UserMapper(c) as follows:
//        convert UserDto into User JPA entity

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for User.!");
        }
        User user = UserMapper.maptoUser(userDto); //maptoUser
        User savedUser = userRepository.save(user);
        //convert User JPA entity into UserDto
        UserDto savedUserDto = UserMapper.maptoUserDto(savedUser); //maptoUserDto
        return savedUserDto;
    }

}
