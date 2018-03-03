package com.ice.cloud.utils;

import com.ice.cloud.Cloud;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CloudEventListener extends ListenerAdapter {
	
	private int found = 0;
	
	@Override
	public void onReady(ReadyEvent event) {
		User me = event.getJDA().getSelfUser();
		StringBuilder ready = new StringBuilder()
				.append("---- { LAUNCHED } ----"+"\n")
				.append("- User: "+me.getName()+"#"+me.getDiscriminator()+"\n")
				.append("- Servers: "+event.getJDA().getGuilds().size()+"\n")
				.append("- Channels: "+event.getJDA().getTextChannels().size()+"\n")
				.append(" + Voice: "+event.getJDA().getVoiceChannels().size()+"\n")
				.append(" + Text: "+event.getJDA().getTextChannels().size());
		System.out.println(ready.toString());
		Cloud.cloudBot.getPresence().setGame(Game.of(GameType.LISTENING, "to 🎵 | "+event.getJDA().getGuilds().size()+" guild(s)"));
	}
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		event.getGuild().getTextChannels().forEach(channel -> {
			if(found == 0) {
				found = 1;
				if(!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_EMBED_LINKS)) return; //Well we cant send nothing if there is no permissions for the bot
				else {
					channel.sendMessage("Hey there, I'm Cloud of sounds. And i play some good songs from soundcloud. To play music simply do `c.play [searchterm]`").queue();
				}	
			} else return; //We dont wanna send too many messages
		});
		event.getJDA().getTextChannelById("419631735441981440").sendMessage(new EmbedBuilder()
			.setColor(Cloud.emColor)
			.setAuthor("New server joined: "+event.getGuild().getName()+"("+event.getGuild().getName(), event.getGuild().getIconUrl())
			.addField("Members", "This guild has **"+event.getGuild().getMembers().stream().filter(u -> u.getUser().isBot() == true).count()+"** bot(s) and **"+event.getGuild().getMembers().stream().filter(u->u.getUser().isBot() == false).count()+"** human(s)", false)
			.addField("Channels", "This guild has **"+event.getGuild().getTextChannels().size()+"** text channel(s) and **"+event.getGuild().getVoiceChannels().size()+"** voice channel(s)", false)
			.addField("Guild Region", "**"+event.getGuild().getRegionRaw(), false)
			.addField("Total now", event.getJDA().getGuilds().size()+" servers", false)
			.build()
		).queue();
		Cloud.cloudBot.getPresence().setGame(Game.of(GameType.LISTENING, "to 🎵 | "+event.getJDA().getGuilds().size()+" guild(s)"));
	}
	
	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		event.getJDA().getTextChannelById("419631735441981440").sendMessage(new EmbedBuilder()
				.setColor(Cloud.emColor)
				.setAuthor("Left server: "+event.getGuild().getName()+"("+event.getGuild().getName(), event.getGuild().getIconUrl())
				.addField("Total now", event.getJDA().getGuilds().size()+" servers", false)
				.build()
		).queue();
		Cloud.cloudBot.getPresence().setGame(Game.of(GameType.LISTENING, "to 🎵 | "+event.getJDA().getGuilds().size()+" guild(s)"));
	}
}
