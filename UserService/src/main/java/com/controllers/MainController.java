package com.controllers;

import com.exceptions.UserAlreadyExistsException;
import com.model.dto.ErrorDTO;
import com.model.entities.User;
import com.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.services.UserService;

import java.util.List;

/**
 * @author leyla
 * @since 17.05.17
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody User userWrapper) {
        try {
            userService.create(userWrapper);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(UserAlreadyExistsException ex) {
            return new ResponseEntity<>(new ErrorDTO(ErrorMessage.USER_ALREADY_EXISTS), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    @ResponseBody
    public void updateUser(@RequestBody User userWrapper) {
        userService.update(userWrapper);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@RequestParam Long id) {
        userService.delete(id);
    }

    @RequestMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
