package com.apichart.playground.spring.example.controller;

import com.apichart.playground.spring.example.repository.user.User;
import com.apichart.playground.spring.example.service.user.UserService;
import com.apichart.playground.spring.example.template.ApiResponse;
import com.apichart.playground.spring.example.template.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        try {
            User user = userService.getOne(Long.parseLong(id));
            if (user == null) {
                ApiResponse apiResponse = new ApiResponse();

                Status status = new Status();
                status.setCode("0000");
                status.setDescription("Requested entity record does not exist");
                apiResponse.setStatus(status);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);

            } else {
                ApiResponse apiResponse = new ApiResponse();

                Status status = new Status();
                status.setCode("0000");
                status.setDescription("Success");
                apiResponse.setStatus(status);

                apiResponse.setData(user);
                return ResponseEntity.ok(apiResponse);
            }
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
//        return new ResponseEntity<>(userService.getOne(Long.parseLong(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createUser = userService.createUser(user);
        if (createUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createUser.getId())
                    .toUri();

            ApiResponse apiResponse = new ApiResponse();
            Status status = new Status();
            status.setCode("0000");
            status.setDescription("Created");
            apiResponse.setStatus(status);
            apiResponse.setData(user);

            return ResponseEntity.created(uri)
                    .body(apiResponse);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(
            @RequestBody User user) {
        User createUser = userService.updateUser(user);
        if (createUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            ApiResponse apiResponse = new ApiResponse();

            Status status = new Status();
            status.setCode("0000");
            status.setDescription("Updated");
            apiResponse.setStatus(status);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
