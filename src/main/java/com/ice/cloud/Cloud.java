package com.ice.cloud;

import com.ice.cloud.audio.GuildPlayer;
import com.ice.cloud.commands.music.*;
import com.ice.cloud.utils.CloudEventListener;
import com.ice.cloud.utils.cmd.CommandHandler;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Cloud {
	public static JDA cloudBot;
	public static CommandHandler commandHandler = new CommandHandler();
	public static GuildPlayer musicHandler = new GuildPlayer();
	
	public static void main(String[] args) throws Exception {
		registerCommands();
		cloudBot = new JDABuilder(AccountType.BOT)
<<<<<<< HEAD
				.setToken(System.getenv("TOKEN"))
=======
				.setToken(System.getevn("TOKEN"))
>>>>>>> 08264db6c744c99ac8a2bf6d4bd67ba3208a8199
				.addEventListener(commandHandler)
				.addEventListener(new CloudEventListener())
				.buildBlocking();
	}
	
	public static void registerCommands() {
		commandHandler.register(new Play());
	}
}
