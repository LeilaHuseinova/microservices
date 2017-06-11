package com.controllers;

import com.model.wrappers.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author leyla
 * @since 17.05.17
 */
@Controller
@RequestMapping("/users")
public class MainController {

    private static final String USERS_SERVICE = "http://localhost:2222/";

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/createUser")
    @ResponseBody
    public void createUser(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phoneNumber) {
        User userWrapper = new User();
        userWrapper.setName(name);
        userWrapper.setEmail(email);
        userWrapper.setPhoneNumber(phoneNumber);

        restTemplate.postForLocation(USERS_SERVICE + "createUser", userWrapper);
    }

    @RequestMapping("/getAllUsers")
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(USERS_SERVICE + "users", List.class);

        for(LinkedHashMap<String, Object> map : usersMap) {
            User user = new User();
            user.setId(Long.parseLong(map.get("id").toString()));
            user.setName(map.get("name").toString());
            user.setPhoneNumber(map.get("phoneNumber").toString());
            user.setEmail(map.get("email").toString());

            users.add(user);
        }

        return users;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public void updateUser(@RequestParam Long id,
                           @RequestParam String email,
                           @RequestParam String phoneNumber) {
        User userWrapper = new User();
        userWrapper.setId(id);
        userWrapper.setEmail(email);
        userWrapper.setPhoneNumber(phoneNumber);
        
        restTemplate.put(USERS_SERVICE + "updateUser", userWrapper);
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public void deleteUser(@RequestParam Long id) {
        restTemplate.delete(USERS_SERVICE + "deleteUser?id=" + id);
    }
}
