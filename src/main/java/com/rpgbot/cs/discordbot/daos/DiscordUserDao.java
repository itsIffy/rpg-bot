package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordUserDao extends JpaRepository<User, Long> {
}
