package com.ice.cloud.utils.cmd;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {

	private List<Command> commands = new ArrayList<>();
	
	public void register(Command c) {
		commands.add(c);
	}
	
	public void remove(Command c) {
		commands.remove(c);
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String args[] = event.getMessage().getContentDisplay().split(" ", 2);
		User author = event.getAuthor();
		commands.forEach(cmd -> {
			List<String> alias = new ArrayList<>(cmd.alias());
			alias.add(cmd.name());
			alias.forEach(a -> {
				if(args[0].equalsIgnoreCase(a)) {
					cmd.whenExecuted(event, args, author);
				}
			});
		});
	}
}
