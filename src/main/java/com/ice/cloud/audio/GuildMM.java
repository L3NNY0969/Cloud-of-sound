package com.ice.cloud.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import net.dv8tion.jda.core.entities.Guild;

public class GuildMM {
	public final AudioPlayer player;
	public final GuildManager listener;
	
	public GuildMM(AudioPlayerManager manager, Guild server) {
		player = manager.createPlayer();
		listener = new GuildManager(player, server);
		player.addListener(listener);
	}
	
	public GuildManager getListener() {
		return listener;
	}
	
	public AudioPlayer getPlayer() {
		return player;
	}
	
	public GuildAudioSendHandler getSendHandler() {
		return new GuildAudioSendHandler(player);
	}
}
