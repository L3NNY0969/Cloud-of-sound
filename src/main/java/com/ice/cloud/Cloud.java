package com.ice.cloud;

import java.awt.Color;

import com.ice.cloud.audio.GuildPlayer;
import com.ice.cloud.commands.misc.Ping;
import com.ice.cloud.commands.music.*;
import com.ice.cloud.utils.CloudEventListener;
import com.ice.cloud.utils.cmd.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class Cloud {
	
	public static JDA cloudBot;
	public static CommandHandler commandHandler = new CommandHandler();
	public static GuildPlayer musicHandler = new GuildPlayer();
	public static Color emColor = new Color(255, 102, 1);
	
	public static void main(String[] args) throws Exception {
		registerCommands();
		cloudBot = new JDABuilder(AccountType.BOT)
				.setToken(System.getenv("TOKEN"))
				.addEventListener(commandHandler)
				.addEventListener(new CloudEventListener())
				.buildBlocking();
		cloudBot.getPresence().setGame(Game.listening("to 🎵 | "+cloudBot.getGuilds().size()+" guild(s)"));
	}
	
	public static void registerCommands() {
		//Music because well this bot is music
		commandHandler.register(new Play());
		commandHandler.register(new Join());
		commandHandler.register(new Skip());
		//Misc
		commandHandler.register(new Ping());
	}
}
