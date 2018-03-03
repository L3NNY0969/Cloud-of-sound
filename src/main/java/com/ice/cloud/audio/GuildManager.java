package com.ice.cloud.audio;

import java.awt.Color;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildManager extends AudioEventAdapter {

	public final AudioPlayer player;
	private final BlockingQueue<AudioTrack> queue;
	private final Guild server;
	private TextChannel sending;
	private boolean sendingSet = false;
	
	public GuildManager(AudioPlayer player, Guild g) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
		this.server = g;
	}
	
	public void setSendingChannel(TextChannel c) {
		if(sendingSet == true) return;
		sending = c;
		sendingSet = true;
	}
	
	public void play(AudioTrack track) {
		player.setVolume(50);
		if(!player.startTrack(track, true)) queue.offer(track);
	}
	
	public void nextSong() {
		if(queue.isEmpty()) {
			sending.sendMessage("The queue is empty. Add more music to continue the queue!").queue();
			sendingSet = false;
			return;
		} else {
			player.setVolume(50);
			player.startTrack(queue.poll(), false);
			AudioTrack t = player.getPlayingTrack();
			sending.sendMessage(new EmbedBuilder()
					.setDescription(":musical_note: Added `"+t.getInfo().title+"` to the queue\n"+
									"Creator: `"+t.getInfo().author+"`\n"+
									"Length: `"+t.getInfo().length+"` (Currently in ms)\n"+
									"Url: [click here]("+t.getInfo().uri+")"
					)
					.setColor(new Color(255, 102, 1))
					.build()
				).queue();
		}
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) nextSong();
	}
	
}
