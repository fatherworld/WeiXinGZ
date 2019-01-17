package com.ys.messages;
import com.ys.messages.Media;

public class Voice extends Media {
	private int VoiceLength;
	
	
	@Override
	public int getMediaLength() {
		return VoiceLength;
	}
	@Override
	public void setMediaLength(int voiceLength) {
		VoiceLength = voiceLength;
	}
	@Override
	public String getMediaId() {
		return mediaId;
	}
	@Override
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public String getMediaPath() {
		return mediaPath;
	}
	@Override
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	
}
