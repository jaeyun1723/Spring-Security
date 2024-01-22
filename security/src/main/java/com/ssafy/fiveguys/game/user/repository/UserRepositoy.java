package com.ssafy.fiveguys.game.user.repository;

import com.ssafy.fiveguys.game.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoy extends JpaRepository<User, Long> {
}
