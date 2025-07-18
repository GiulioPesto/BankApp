package com.giuliopastore.BankApp.repos;

import com.giuliopastore.BankApp.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUid(String uid);
}
