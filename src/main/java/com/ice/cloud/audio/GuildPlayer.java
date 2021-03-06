package com.ice.cloud.audio;
import java.util.HashMap;
import java.util.Map;

import com.ice.cloud.Cloud;
import com.ice.cloud.utils.Time;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration.ResamplingQuality;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildPlayer {
	
	private final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	
	public GuildPlayer() {
		AudioSourceManagers.registerLocalSource(playerManager);
		AudioSourceManagers.registerRemoteSources(playerManager);
		playerManager.getConfiguration().setResamplingQuality(ResamplingQuality.LOW);
	}

	private Map<String, GuildMM> managers = new HashMap<>();
	public synchronized GuildMM getGuildPlayer(Guild g) {
		if(!managers.containsKey(g.getId())) managers.put(g.getId(), new GuildMM(playerManager, g));
		return managers.get(g.getId());
	}
	
	public void remove(Guild g) {
		if(managers.containsKey(g.getId())) managers.remove(g.getId());
		else return;
	}
	
	public int getPlayerCount() {return managers.size();}
	
	public void play(final TextChannel c, final String audioSource) {
		final GuildMM player = getGuildPlayer(c.getGuild());
		
		c.getGuild().getAudioManager().setSendingHandler(player.getSendHandler());
		player.getListener().setSendingChannel(c);
		
		playerManager.loadItemOrdered(player, audioSource, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				//Do nothing since we're not using any urls
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				if(player.getPlayer().getPlayingTrack() == null) {
					System.out.println("[AUDIO] Now playing "+playlist.getTracks().get(0).getInfo().title+" for "+c.getGuild().getName()+"("+c.getGuild().getId()+")");
					c.sendMessage(new EmbedBuilder()
						.setDescription(":musical_note: Now playing **["+playlist.getTracks().get(0).getInfo().title+"]("+playlist.getTracks().get(0).getInfo().uri+")**\n"+
										"By: `"+playlist.getTracks().get(0).getInfo().author+"`\n"+
										"Duration: "+Time.fromMStoHMS(playlist.getTracks().get(0).getInfo().length)
						)
						.setColor(Cloud.emColor)
						.build()
					).queue();
					player.listener.play(playlist.getTracks().get(0));
				} else {
					System.out.println("[AUDIO] Added "+playlist.getTracks().get(0).getInfo().title+" to the queue for "+c.getGuild().getName()+"("+c.getGuild().getId()+")");
					c.sendMessage(new EmbedBuilder()
							.setDescription(":musical_note: Added **["+playlist.getTracks().get(0).getInfo().title+"]("+playlist.getTracks().get(0).getInfo().uri+")** to the queue!\n"+
											"By: `"+playlist.getTracks().get(0).getInfo().author+"`\n"+
											"Duration: "+Time.fromMStoHMS(playlist.getTracks().get(0).getInfo().length)
							)
							.setColor(Cloud.emColor)
							.build()
						).queue();
						player.listener.play(playlist.getTracks().get(0));
				}
			}
			
			@Override
			public void noMatches() {
				System.out.println("[AUDIO] A search for "+audioSource+" returned zero results for "+c.getGuild().getName()+"("+c.getGuild().getId()+")");
				c.sendMessage("Found 0 results for `"+audioSource+"`").queue();
			}
			
			@Override
			public void loadFailed(FriendlyException e) {
				c.sendMessage("Welp, It looks like everything exploded. Please try playing this song later!").queue();
			}
		});
	}
	
}
