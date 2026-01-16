package com.giuliopastore.BankApp.services;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

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
                existingUser.setBirthDate(user.getBirthDate());
                existingUser.setBirthPlace(user.getBirthPlace());
                existingUser.setCity(user.getCity());
                existingUser.setProvince(user.getProvince());
                existingUser.setBankAccounts(user.getBankAccounts());
                return userRepository.save(existingUser);
            }
        } catch (Exception ex) {
         throw new NullPointerException(ex.getMessage());
        }
        return null;
    }

    public User updateUser(String uid, Map<String, Object> updates) {
        User existingUser = userRepository.findUserByUid(uid);

        if (existingUser == null) {
            return null;
        }

        updates.forEach((fieldName, value) -> {
            try {
                Field field = User.class.getDeclaredField(fieldName);
                field.setAccessible(true);

                if (field.getName().equals("subscriptionType")) {
                    value = Enum.valueOf(com.giuliopastore.BankApp.enums.SubscriptionType.class, (String) value);
                }

                field.set(existingUser, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Errore durante l'aggiornamento del campo " + fieldName + ": " + e.getMessage());
            }
        });

        return userRepository.save(existingUser);
    }

    public void deleteUser(String uid) {
        User user = userRepository.findUserByUid(uid);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}
