package com.ice.cloud.utils;

import java.time.format.DateTimeFormatter;

import com.ice.cloud.Cloud;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CloudEventListener extends ListenerAdapter {
	
	private int found = 0;
	
	@Override
	public void onReady(ReadyEvent event) {
		User me = event.getJDA().getSelfUser();
		StringBuilder ready = new StringBuilder()
				.append("---- { LAUNCHED } ----"+"\n")
				.append("- User: "+me.getName()+"#"+me.getDiscriminator()+"\n")
				.append("- Server(s) "+event.getJDA().getGuilds().size()+"\n")
				.append("- Channels: "+event.getJDA().getTextChannels().size()+"\n")
				.append(" + Voice: "+event.getJDA().getVoiceChannels().size()+"\n")
				.append(" + Text: "+event.getJDA().getTextChannels().size());
		System.out.println(ready.toString());
	}
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		event.getJDA().getTextChannelById("419631735441981440").sendMessage(new EmbedBuilder()
				.setColor(Cloud.emColor)
				.setAuthor("New server joined: "+event.getGuild().getName()+"("+event.getGuild().getId()+")", null, event.getGuild().getIconUrl())
				.addField("Members", "This guild has **"+event.getGuild().getMembers().stream().filter(u -> u.getUser().isBot() == true).count()+"** bot(s) and **"+event.getGuild().getMembers().stream().filter(u->u.getUser().isBot() == false).count()+"** human(s)", false)
				.setThumbnail(event.getGuild().getIconUrl())
				.addField("Channels", "This guild has **"+event.getGuild().getTextChannels().size()+"** text channel(s) and **"+event.getGuild().getVoiceChannels().size()+"** voice channel(s)", false)
				.addField("Guild Region", "**"+event.getGuild().getRegionRaw()+"**", false)
				.addField("Total now", event.getJDA().getGuilds().size()+" guild(s)", false)
				.build()
			).queue();
			Cloud.cloudBot.getPresence().setGame(Game.listening("🎵 | "+event.getJDA().getGuilds().size()+" guild(s)"));
		event.getGuild().getTextChannels().forEach(channel -> {
			if(found == 0) {
				try {
					found = 1;
					channel.sendMessage("Hey there, I'm the cloud of sounds, I play music from soundcloud in your voice channel. To play music simply run `c.play [searchterm]`").queue();
				} catch (PermissionException e) {
					//Do nothing
				}
			} else return; //We dont wanna send too many messages
		});
	}
	
	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		event.getJDA().getTextChannelById("419631735441981440").sendMessage(new EmbedBuilder()
				.setColor(Cloud.emColor)
				.setAuthor("Left server: "+event.getGuild().getName()+"("+event.getGuild().getId()+")", null, event.getGuild().getIconUrl())
				.setThumbnail(event.getGuild().getIconUrl())
				.addField("Been with this guild since", event.getGuild().getSelfMember().getJoinDate().format(DateTimeFormatter.ISO_LOCAL_DATE), false)
				.addField("Total now", event.getJDA().getGuilds().size()+" guild(s)", false)
				.build()
		).queue();
		Cloud.cloudBot.getPresence().setGame(Game.listening("🎵 | "+event.getJDA().getGuilds().size()+" guild(s)"));
	}
}
