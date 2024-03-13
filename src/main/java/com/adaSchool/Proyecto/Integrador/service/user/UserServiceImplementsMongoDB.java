package com.adaSchool.Proyecto.Integrador.service.user;

import com.adaSchool.Proyecto.Integrador.exception.UserNotFoundException;
import com.adaSchool.Proyecto.Integrador.repository.user.User;
import com.adaSchool.Proyecto.Integrador.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserServiceImplementsMongoDB implements UsersService{

    private UserRepository userRepository;

    public UserServiceImplementsMongoDB(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
            return user;
        }else{
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public List<User> all() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
           userRepository.deleteById(id);
        }else{
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User update(User user, String userId) {
        Optional<User> searchUser=userRepository.findById(userId);
        if(searchUser.isPresent()){
            return userRepository.save(user);
        }else{
            throw new UserNotFoundException(userId);
        }
    }
}
