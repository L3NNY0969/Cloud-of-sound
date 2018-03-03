package com.ice.cloud.utils.cmd;

import java.util.List;
import java.util.Collections;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Command {
	
	public String name() {return null;}
	public String description() {return null;}
	public List<String> alias() {return Collections.emptyList();}
	public void whenExecuted(GuildMessageReceivedEvent event, String args[], User author) {}
	
}
