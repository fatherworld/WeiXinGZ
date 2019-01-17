//package com.ys.control;
//import com.ys.utils.UploadMediaApiUtil;
//import java.io.IOException;
//import com.alibaba.fastjson.JSONObject;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import com.ys.messages.*;
//import com.ys.common.*;
//import java.lang.StringBuilder;
//enum Media_Type{
//	IMAGE,
//	VOICE,
//	VIDEO 
//}
//
//
//public class control_image_upload {
//	private int maxMediaSize;
//	
//	private static int  currentImageIdx = 0;
//	
//	private String ImageDir;
//	private Image[] Images ;
//	
//	private String ImagePath;
//	private StringBuilder sb;
//
////	sb =  new StringBuilder("../../../../imageData/");
//	
//	public control_image_upload(int maxMediaSize,String ImageDir)
//	{
//		this.ImageDir = ImageDir;
//		this.maxMediaSize = maxMediaSize;
//		
//		sb =  new StringBuilder(ImageDir);
//		Images = new Image[this.maxMediaSize];
//	}
//	
//	public control_voice_upload(int maxImageSize,String ImageDir)
//	{
//		this.ImageDir = ImageDir;
//		this.maxImageSize = maxImageSize;
//		
//		sb =  new StringBuilder(ImageDir);
//		Images = new Image[this.maxImageSize];
//	}
//	
//	
//	public void uploadCurrentImage()
//	{
//		
//		System.out.println("uploadCurrentImage() ");
//		UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil(); 
//		
//		
//		String appId = IdAndSecret.appId; 
//		String appSecret = IdAndSecret.appSecret; 
//		String accessToken = uploadMediaApiUtil.getAccessToken(appId,appSecret);
//		
//		System.out.println("current accessToken is "+accessToken);
//		ImagePath = sb.append(currentImageIdx).append(".jpg").toString();
//		
//		
//		System.out.print("current imageidx is"+currentImageIdx);
//		
//		Images[currentImageIdx] = new Image();
//		
//		Images[currentImageIdx].setImagechannel(0);
//		Images[currentImageIdx].setImagewidth(0);
//		Images[currentImageIdx].setImageheight(0);
//		Images[currentImageIdx].setImgpath(ImagePath);
//		
//		//currentImage = new Image(0,0,0,ImagePath);
//		File file = new File(ImagePath); 
//		System.out.println("ImagePath " + ImagePath);
//		if(!file.exists())
//		{
//			System.out.print("imagepath is not exist");
//		}
//		
//		String type = "IMAGE"; 
//		JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type); 
//		
//		System.out.print("media_id" + jsonObject.getString("media_id"));
//		
//		Images[currentImageIdx].setImageId(jsonObject.getString("media_id"));
//		currentImageIdx++;
//		System.out.println("获取media_id");
//		System.out.println(jsonObject.toString());
//		
//		if(currentImageIdx == maxImageSize)
//		{
//			currentImageIdx = 0;
//		}
//		
//	}
//	public static int getCurrentImageIdx() {
//		return currentImageIdx;
//	}
//	public static void setCurrentImageIdx(int currentImageIdx) {
//		control_image_upload.currentImageIdx = currentImageIdx;
//	}
//	public Image[] getImages() {
//		return Images;
//	}
//	public void setImages(Image[] images) {
//		Images = images;
//	}
//	
//
//	
//}
