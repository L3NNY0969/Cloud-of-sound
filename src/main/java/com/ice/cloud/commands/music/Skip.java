package com.ice.cloud.commands.music;

import java.util.Arrays;
import java.util.List;

import com.ice.cloud.Cloud;
import com.ice.cloud.audio.GuildMM;
import com.ice.cloud.utils.cmd.Command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Skip extends Command {
	@Override
	public String name() {
		return "c.skip";
	}
	
	@Override
	public String description() {
		return "Skips the current song!";
	}
	
	@Override
	public List<String> alias() {
		return Arrays.asList("c.skap", "c.skhap");
	}
	
	@Override
	public void whenExecuted(GuildMessageReceivedEvent event, String[] args, User author) {
		GuildMM player = Cloud.musicHandler.getGuildPlayer(event.getGuild());
		if(player.getListener().queueEmpty() == true) {
			event.getChannel().sendMessage("There are no other songs to skip to!").queue();
			return;
		} else {
			VoiceChannel userVC = event.getGuild().getMember(author).getVoiceState().getChannel();
			if(userVC == null) {
				event.getChannel().sendMessage("You must be in a voice channel first!").queue();
				return;
			}
			event.getChannel().sendMessage(":thumbsup: Skipped `"+player.getPlayer().getPlayingTrack().getInfo().title+"`").queue();
			player.getListener().nextSong();
		}
	}
}
