package com.ice.cloud.utils;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CloudEventListener extends ListenerAdapter {
	@Override
	public void onReady(ReadyEvent event) {
		User me = event.getJDA().getSelfUser();
		StringBuilder ready = new StringBuilder()
				.append("---- { LAUNCHED } ----"+"\n")
				.append("- User: "+me.getName()+"#"+me.getDiscriminator()+"\n")
				.append("- Servers: "+event.getJDA().getGuilds().size()+"\n")
				.append("- Channels: "+event.getJDA().getTextChannels().size()+"\n")
				.append(" + Voice: "+event.getJDA().getVoiceChannels().size());
		System.out.println(ready.toString());
	}
}
