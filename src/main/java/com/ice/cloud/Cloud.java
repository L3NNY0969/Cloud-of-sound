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
				.setToken(System.getevn("TOKEN"))
				.addEventListener(commandHandler)
				.addEventListener(new CloudEventListener())
				.buildBlocking();
	}
	
	public static void registerCommands() {
		commandHandler.register(new Play());
	}
}
