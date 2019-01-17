package com.ys.messages;

public class Video extends Media{
	
	private int VideoLength;
	
	
	public int getMediaLength() {
		return VideoLength;
	}
	public void setMediaoLength(int videoLength) {
		VideoLength = videoLength;
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
