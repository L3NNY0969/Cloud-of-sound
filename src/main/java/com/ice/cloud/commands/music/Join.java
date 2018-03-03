package com.ice.cloud.commands.music;
import java.util.Arrays;
import java.util.List;
import com.ice.cloud.utils.cmd.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Join extends Command {
	@Override
	public String name() {
		return "c.join";
	}
	
	@Override
	public String description() {
		return "Joins your voice channel if you are in one!";
	}
	
	@Override
	public List<String> alias() {
		return Arrays.asList("c.summon", "c.plsjoin");
	}
	
	@Override
	public void whenExecuted(GuildMessageReceivedEvent event, String[] args, User author) {
		VoiceChannel vc = event.getGuild().getMember(author).getVoiceState().getChannel();
		if(vc == null) {
			event.getChannel().sendMessage("You must be in a voice channel first!").queue();
			return;
		} else {
			if(!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_SPEAK)) {
				event.getChannel().sendMessage(":x: Failed to join your voice channel. Reason: I cannot speak in your voice channel!").queue();
				return;
			}
			if(!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_SPEAK)) {
				event.getChannel().sendMessage(":x: Failed to join your voice channel. Reason: I cannot connect to your voice channel!").queue();
				return;
			}
			event.getGuild().getAudioManager().openAudioConnection(vc);
			event.getChannel().sendMessage(":white_check_mark: Successfully joined **"+vc.getName()+"**").queue();	
		}
	}
}
