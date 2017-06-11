package com.services;

import com.model.entities.User;

import java.util.List;

/**
 * @author leyla
 * @since 17.05.17
 */
public interface UserService {

    void create(User userWrapper);
    void update(User userWrapper);
    void delete(Long id);
    List<User> getAll();
}
