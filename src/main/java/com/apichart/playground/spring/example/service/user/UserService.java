package com.apichart.playground.spring.example.service.user;

import com.apichart.playground.spring.example.repository.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getOne(Long id);

    List<User> findAll();

    User createUser(User user);

    User updateUser(User user);

    void deleteById(Long id);

    void deleteUser(User user);

    boolean existById(Long id);

}
