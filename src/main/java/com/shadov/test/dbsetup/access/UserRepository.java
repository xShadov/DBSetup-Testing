package com.shadov.test.dbsetup.access;

import com.shadov.test.dbsetup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByNick(String nick);
}
