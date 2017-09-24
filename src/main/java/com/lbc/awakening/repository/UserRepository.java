package com.lbc.awakening.repository;

import com.lbc.awakening.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    @Query("SELECT t FROM UserModel t WHERE t.email = ?1")
    UserModel getUserByEmail(String email);
}
