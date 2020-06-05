package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNameTakenException;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateEmbedCommandHandler implements ICommandHandler {
	private final CommandService commandService;
	private final EmbedGeneratorFactory embedGeneratorFactory;
	private final DiscordBotConfiguration discordBotConfiguration;

	@Override
	public void handle(MessageCreateEvent messageCreateEvent, String message) {
		// verifies there's enough arguments
		if (message.split(" ").length > 2) {
			// gets the command name
			String command = message.split(" ")[1];
			// gets the command description
			String description = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
			try {
				commandService.registerEmbedCommand(command, description);
			} catch (CommandNameTakenException commandNameTakenException) {
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNAMETAKENEXCEPTION).build(commandNameTakenException.getMessage()));
			}
		} else {
			// if there aren't enough arguments, lets the member know how to use the command
			messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getCreateEmbedCommand()).build("if you're seeing this, please contact an admin!"));
		}
	}
}
