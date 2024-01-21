package com.ssafy.fiveguys.game.player.repository;

import com.ssafy.fiveguys.game.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
