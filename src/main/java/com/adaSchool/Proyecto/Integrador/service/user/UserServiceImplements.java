package com.adaSchool.Proyecto.Integrador.service.user;

import com.adaSchool.Proyecto.Integrador.exception.UserNotFoundException;
import com.adaSchool.Proyecto.Integrador.repository.user.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplements implements UsersService{

    Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
       users.put(user.getId(),user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        if(users.containsKey(id)){
            return Optional.of(users.get(id)) ;
        }else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public List<User> all() {
        List<User> usersList = new ArrayList<>();
        usersList.addAll(users.values());
        return usersList;
    }

    @Override
    public void deleteById(String id) {
        if(users.containsKey(id)){
            users.remove(id);
        }else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User update(User user, String userId) {
        if(users.containsKey(userId)){
            users.put(userId, user);
           return user;
        }else {
            throw new UserNotFoundException(userId);
        }
    }
}
