package com.ice.cloud.commands.music;

import java.util.Arrays;
import java.util.List;

import com.ice.cloud.Cloud;
import com.ice.cloud.audio.GuildMM;
import com.ice.cloud.utils.cmd.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Repeat extends Command {
	@Override
	public String name() {
		return "c.repeat";
	}
	
	 @Override
	public String description() {
		 return "Toggles loop or un toggles loop for the current song!";
	}
	 
	@Override
	public List<String> alias() {
		return Arrays.asList("c.loop", "c.infinite");
	}
	
	@Override
	public void whenExecuted(GuildMessageReceivedEvent event, String[] args, User author) {
		GuildMM player = Cloud.musicHandler.getGuildPlayer(event.getGuild());
		VoiceChannel vc = event.getGuild().getMember(author).getVoiceState().getChannel();
		if(vc == null) {
			event.getChannel().sendMessage("You must be in a voice channel first!").queue();
			return;
		}
		player.getListener().setRepeating(!player.getListener().getRepeating());
		event.getChannel().sendMessage(player.getListener().getRepeating() ? "The player is now repeating" : "The player is no longer repeating").queue();
	}
}
