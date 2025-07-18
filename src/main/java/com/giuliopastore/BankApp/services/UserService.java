package com.giuliopastore.BankApp.services;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUid(String uid) {
        return userRepository.findUserByUid(uid);
    }

    public User updateUser(String uid, User user) {
        User existingUser = userRepository.findUserByUid(uid);
        try {
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setAddress(user.getAddress());
                existingUser.setPhoneNumber(user.getPhoneNumber());
                existingUser.setZipCode(user.getZipCode());
                existingUser.setTaxIdCode(user.getTaxIdCode());
                existingUser.setAge(user.getAge());
                existingUser.setBankAccounts(user.getBankAccounts());
                return userRepository.save(existingUser);
            }
        } catch (Exception ex) {
         throw new NullPointerException(ex.getMessage());
        }
        return null;
    }

    public void deleteUser(String uid) {
        User user = userRepository.findUserByUid(uid);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}
