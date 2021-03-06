package com.services.impl;

import com.dao.UserDAO;
import com.exceptions.UserAlreadyExistsException;
import com.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.services.UserService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author leyla
 * @since 17.05.17
 */
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void create(User userWrapper) {
        if(userDAO.getCountByName(userWrapper.getName()) == 0) {
            userDAO.save(userWrapper);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public void update(User userWrapper) {
        if(userWrapper != null) {
            User user = userDAO.getById(userWrapper.getId());

            String email = userWrapper.getEmail();
            String phoneNumber = userWrapper.getPhoneNumber();

            if(!StringUtils.isEmpty(email)) user.setEmail(email);
            if(!StringUtils.isEmpty(phoneNumber)) user.setPhoneNumber(phoneNumber);

            userDAO.save(user);
        }
    }

    @Override
    public void delete(Long id) {
        if(id != null) {
            User user = userDAO.getById(id);
            userDAO.delete(user);
        }
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }
}
