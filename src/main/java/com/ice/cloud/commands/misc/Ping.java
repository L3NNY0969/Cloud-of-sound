package com.ice.cloud.commands.misc;

import java.util.Arrays;
import java.util.List;

import com.ice.cloud.Cloud;
import com.ice.cloud.utils.cmd.Command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Ping extends Command {
	@Override
	public String name() {
		return "c.ping";
	}
	
	@Override
	public String description() {
		return "Pings the bot and returns its time";
	}
	
	@Override
	public List<String> alias() {
		return Arrays.asList("c.peng", "c.replytime");
	}
	
	@Override
	public void whenExecuted(GuildMessageReceivedEvent event, String[] args, User author) {
		event.getChannel().sendMessage(new EmbedBuilder()
				.setTitle("Pong")
				.setColor(Cloud.emColor)
				.setDescription("**"+event.getJDA().getPing()+"** ms")
				.build()
		).queue();
	}
}
