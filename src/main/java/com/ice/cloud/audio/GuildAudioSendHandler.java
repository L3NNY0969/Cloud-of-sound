package com.ice.cloud.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.core.audio.AudioSendHandler;

public class GuildAudioSendHandler  implements AudioSendHandler {

	public final AudioPlayer player;
	public AudioFrame frame;
	
	public GuildAudioSendHandler(AudioPlayer aplayer) {
		this.player = aplayer;
	}
	
	@Override
	public boolean canProvide() {
		frame = player.provide();
		return frame != null;
	}

	@Override
	public byte[] provide20MsAudio() {
		return frame.data;
	}
	
	@Override
	public boolean isOpus() {
		return true;
	}

}
