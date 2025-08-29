package sawan.dev.com.EmployeePerformanceTracker.controller;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.com.EmployeePerformanceTracker.dto.UserDto;
import sawan.dev.com.EmployeePerformanceTracker.entity.User;
import sawan.dev.com.EmployeePerformanceTracker.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    // ✅ Create a new User
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(modelMapper.map(savedUser, UserDto.class));
    }


    //Get User By id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }



    //Ger All Users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }

    //Update  user
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User updatedUser = userService.updateUser(id, user);
        UserDto updateUserDto = modelMapper.map(updatedUser, UserDto.class);
        return ResponseEntity.ok(updateUserDto);
    }

    //Delete User
    @DeleteMapping("/{id}")
    public  ResponseEntity<String>deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
       return ResponseEntity.ok("User delete successfully with id :"+ id);
    }

}
