package com.ice.cloud.audio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildManager extends AudioEventAdapter {

	public final AudioPlayer player;
	public final BlockingQueue<AudioTrack> queue;
	public final Guild server;
	public TextChannel sending;
	
	public GuildManager(AudioPlayer player, Guild g) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
		this.server = g;
	}
	
	public void play(AudioTrack track) {if(!player.startTrack(track, true)) queue.offer(track);}
	
	public void nextSong() {
		if(queue.isEmpty()) {
			server.getAudioManager().closeAudioConnection();
			
		}
		player.startTrack(queue.poll(), false);
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) nextSong();
	}
	
}
