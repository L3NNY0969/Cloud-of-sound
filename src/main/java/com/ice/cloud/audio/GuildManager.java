package com.ice.cloud.audio;

import java.awt.Color;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ice.cloud.Cloud;
import com.ice.cloud.utils.Time;
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
	private Guild server;
	private TextChannel sending;
	private boolean sendingSet = false;
	
	//Repeating stuff
	private boolean repeating = false;
	AudioTrack lastTrack;
	
	public GuildManager(AudioPlayer player, Guild g) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
		this.server = g;
	}
	
	public boolean queueEmpty() {
		return queue.isEmpty();
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
			System.out.println("[AUDIO] Removing "+server.getName()+"'s player with reason: Queue Concluded");
			Cloud.musicHandler.remove(server);
			sending.sendMessage("Music queue concluded!").queue();
			sendingSet = false;
			return;
		} else {
			player.setVolume(50);
			player.startTrack(queue.poll(), false);
			AudioTrack t = player.getPlayingTrack();
			System.out.println("[AUDIO] Now playing "+t.getInfo().title+" for "+server.getName()+"("+server.getId()+")");
			sending.sendMessage(new EmbedBuilder()
					.setDescription(":musical_note: Now playing **["+t.getInfo().title+"]("+t.getInfo().uri+")**\n"+
									"Creator: `"+t.getInfo().author+"`\n"+
									"Length: "+Time.fromMStoHMS(t.getInfo().length)
					)
					.setColor(new Color(255, 102, 1))
					.build()
				).queue();
		}
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) {
			if(repeating) 
				player.startTrack(track.makeClone(), false);
			else
				nextSong();
		}
	}
}