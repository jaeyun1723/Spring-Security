package com.ssafy.fiveguys.game.user.repository;

import com.ssafy.fiveguys.game.user.dto.SocialType;
import com.ssafy.fiveguys.game.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoy extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
