package com.ys.control;

import com.ys.utils.UploadMediaApiUtil;
import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import com.ys.messages.*;
import com.ys.common.*;
import java.lang.StringBuilder;
import com.ys.utils.Media_Type;
import com.ys.messages.Video;
public class control_media_upload {
	
	
private int maxMediaSize;
	
	private static int  currentMediaIdx = 0;
	
	private String MediaDir;
	private Media[] medias ;
	
	private String MediaPath;
	private StringBuilder sb;
	
	private Media_Type media_type;
	
	public Media_Type getMedia_type() {
		return media_type;
	}

	public void setMedia_type(Media_Type media_type) {
		this.media_type = media_type;
	}

	public control_media_upload(int maxMediaSize,String MediaDir,Media_Type media_type)
	{
		this.MediaDir = MediaDir;
		this.maxMediaSize=maxMediaSize;
		sb =  new StringBuilder(MediaDir);
		this.media_type = media_type;
		switch(media_type)
		{
		case IMAGE:
			medias = new Image[this.maxMediaSize];
			break;
			
		case VOICE:
			medias = new Voice[this.maxMediaSize];
			System.out.print("Init voice medias");
			break;
		case VIDEO:
			medias = new Video[this.maxMediaSize];
			System.out.print("Init video medias");
			break;
		default:
			break;
		}
			
		
	}
	
	public void uploadCurrentMedia()
	{
		
		
		if(currentMediaIdx == maxMediaSize)
		{
			currentMediaIdx = 0;
		}
		
		System.out.println("uploadCurrentMedia() ");
		UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil(); 
		String type="IMAGE";
		
		
		String appId = IdAndSecret.appId; 
		String appSecret = IdAndSecret.appSecret; 
		
		//String accessToken = uploadMediaApiUtil.getAccessToken(appId,appSecret);
		//该Token是根据线程在实时的获取
		String accessToken = AccessTokenInfo.accessToken.getTokenName();

		
		System.out.println("current accessToken is "+accessToken);
		
		if(media_type == Media_Type.IMAGE)
		{
			//目前只支持jpg格式
			MediaPath = sb.append(currentMediaIdx).append(".jpg").toString();
			medias[currentMediaIdx] = new Image();
			medias[currentMediaIdx].setMediaChannel(0);
			medias[currentMediaIdx].setMediaWidth(0);
			medias[currentMediaIdx].setMediaHeight(0);
			type = "IMAGE";
			
		}
		else if(media_type == Media_Type.VOICE)
		{
			//目前支持mp3格式
			System.out.println("Media_Type.VOICE "+accessToken);
			MediaPath = sb.append(currentMediaIdx).append(".mp3").toString();
			medias[currentMediaIdx] = new Voice();
			medias[currentMediaIdx].setMediaLength(0);
			type = "VOICE";
		}
		else if(media_type == Media_Type.VIDEO)
		{
			MediaPath = sb.append(currentMediaIdx).append(".mp4").toString();
			System.out.println("Media_Type.VOICE "+accessToken);
			medias[currentMediaIdx] = new Video();
		
			medias[currentMediaIdx].setMediaLength(0);
			
			type = "VIDEO";
		}
		
		medias[currentMediaIdx].setMediaPath(MediaPath);
		
	
		File file = new File(MediaPath); 
		System.out.println("MediaPath " + MediaPath);
		if(!file.exists())
		{
			System.out.println("MediaPath is not exist");
		}		
		
		
		System.out.println("uploadMediaApiUtil.uploadMedia");
		JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type); 
		
		System.out.println("获取media_id");
		System.out.println("media_id" + jsonObject.getString("media_id"));
		
		medias[currentMediaIdx].setMediaId(jsonObject.getString("media_id"));
		
		System.out.println("");
		currentMediaIdx++;
		
		System.out.println(jsonObject.toString());	
				
	}
	
	public static int getCurrentMediaIdx() {
		return currentMediaIdx;
	}
	public static void setCurrentMediaIdx(int currentMedaiIdx) {
		control_media_upload.currentMediaIdx = currentMediaIdx;
	}
	public Media[] getMedias() {
		return medias;
	}
	public void setMeias(Media[] medias) {
		medias = medias;
	}


}
