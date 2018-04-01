package com.shadov.test.database.access;

import com.shadov.test.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByNick(String nick);
}
