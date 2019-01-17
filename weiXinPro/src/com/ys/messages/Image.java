package com.ys.messages;

public class Image extends Media{
	/*
	private String ImageId;
	private String imagepath;
	*/
	
	private int imagewidth;
	private int imageheight;
	private int imagechannel;
	
	public String getImageId() {
		return this.mediaId;
	}
	public void setImageId(String imageId) {
		this.mediaId = imageId;
	}
	public String getImgpath() {
		return this.mediaPath;
	}
	
	public void setImgpath(String imgpath) {
		this.mediaPath = imgpath;
	}
	
	@Override
	public int getMediaWidth() {
		return imagewidth;
	}
	@Override
	public void setMediaWidth(int imagewidth) {
		this.imagewidth = imagewidth;
	}
	@Override
	public int getMediaHeight() {
		return imageheight;
	}
	@Override
	public void setMediaHeight(int imageheight) {
		this.imageheight = imageheight;
	}
	@Override
	public int getMediaChannel() {
		return imagechannel;
	}
	@Override
	public void setMediaChannel(int imagechannel) {
		this.imagechannel = imagechannel;
	}
		
	public Image(int imagewidth,int imageheight,int imagechannel,String imagepath)
	{
		this.imagechannel = imagechannel;
		this.imagewidth = imagewidth;
		this.imageheight = imageheight;
		this.mediaPath = imagepath;
	}
	public Image()
	{
		
	}
}
