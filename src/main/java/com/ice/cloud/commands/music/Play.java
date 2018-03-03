package com.ice.cloud.commands.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ice.cloud.Cloud;
import com.ice.cloud.utils.cmd.Command;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Play extends Command {

	@Override
	public String name() {
		return "c.play";
	}
	
	@Override
	public String description() {
		return "Plays a song from soundcloud!";
	}
	
	@Override
	public List<String> alias() {
		return Collections.emptyList();
	}
	
	@Override
	public void whenExecuted(GuildMessageReceivedEvent event, String[] args, User author) {
		VoiceChannel channel = event.getGuild().getMember(author).getVoiceState().getChannel();
		TextChannel c = event.getChannel();
		String input = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
		if(channel == null) {
			c.sendMessage("You must be in a voice channel first!").queue();
			return;
		}
		if(args.length < 2) {
			c.sendMessage("Correct Usage: `c.play [searchterm]`").queue();
			return;
		} else {
			input = "scsearch:"+input;
			c.getGuild().getAudioManager().openAudioConnection(channel);
			Cloud.musicHandler.play(c, input);	
		}
	}

}
