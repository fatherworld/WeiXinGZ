package com.ys.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ys.control.control_media_upload;
import com.ys.messages.Image;
import com.ys.messages.Media;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ys.common.WeatherInfo;
import com.ys.api.YouDaoAPI;

public class MessageUtil {
	
	
	control_media_upload ys_control_media;
	
	
    /**
     * 解析微信发来的请求（XML）
     * @param request
     * @return map
     * @throws Exception
     */
	
	
	public MessageUtil(int maxMediaSize,String MediaDir,Media_Type media_type)
	{
		ys_control_media = new control_media_upload(maxMediaSize,MediaDir,media_type);
	}
	
	public MessageUtil()
	{
		
	}
	

    /**
     * 根据消息类型 构造返回消息
     */
    public String buildXml(Map<String,String> map) {
        String result;
        String msgType = map.get("MsgType").toString();
        String msgContent = map.get("Content");
        System.out.println("MsgType:" + msgType);
        if(msgType.toUpperCase().equals("TEXT")){
        	if(msgContent.equals("想要一张图片"))
        	{   
        		System.out.print("msgContent is :"+ msgContent);
        		ys_control_media.uploadCurrentMedia();
        		System.out.print("End");
        		int currentMediaIdx = ys_control_media.getCurrentMediaIdx();
        		System.out.print("currentImageIdx is"+ currentMediaIdx);
        		
        		Media media = ys_control_media.getMedias()[currentMediaIdx-1];
        		
        		
        		result = buildImageMessage(map,media.getMediaId());
        		
        	}
        	//查询天气
        	
        	else if(msgContent.contains("天气") && !"".equals(msgContent))
        	{
        		 if(msgContent.contains(":")){
        			 String cityName = msgContent.substring(msgContent.lastIndexOf(":")+1,msgContent.length());
        			 WeatherInfo weather = new WeatherInfo();
        			 String weaInfo = weather.getWeatherInfo(cityName);
        			 result = buildTextMessage(map,weaInfo);
        		 }
        		 else{
        			 String notice = "查询天气的正确姿势: 天气:城市名\n请客官输入正确的格式~";
        			 result = buildTextMessage(map,notice);
        		 }
        	}
        	
        	else if(msgContent.contains("翻译") && !"".equals(msgContent)){
                if(msgContent.contains(":")){
                    String word = msgContent.substring(msgContent.lastIndexOf(":")+1,msgContent.length()).trim();
                    YouDaoAPI translateInfo = new YouDaoAPI();
                    String weaInfo = null;
					try {
						System.out.println("翻译");
						weaInfo = translateInfo.translate(word);
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
                    result = buildTextMessage(map,weaInfo);
                }else{
                    String notice = "翻译的正确姿势: 翻译:翻译文本\n请客官输入正确的格式哟~";
                    result = buildTextMessage(map,notice);
                }
        	}
        	
        	else{
        		result = buildTextMessage(map, "这是叶山的公众号,欢迎订阅?");
        	}
            
        }
        //接收到语音消息
        else if(msgType.toUpperCase().equals("VOICE"))
        {
        	System.out.print("msgContent is :"+ msgContent);
    		ys_control_media.uploadCurrentMedia();
    		System.out.print("End");
    		int currentMediaIdx = ys_control_media.getCurrentMediaIdx();
    		System.out.print("currentImageIdx is"+ currentMediaIdx);
    		
    		Media media = ys_control_media.getMedias()[currentMediaIdx-1];
    		
    		
    		result = buildImageMessage(map,media.getMediaId());
        }
        
        else if(msgType.toUpperCase().equals("VIDEO"))
        {
        	System.out.println("msgContent is :"+ msgContent);
    		ys_control_media.uploadCurrentMedia();
    		int currentMediaIdx = ys_control_media.getCurrentMediaIdx();
    		System.out.print("currentImageIdx is"+ currentMediaIdx);
    		
    		Media media = ys_control_media.getMedias()[currentMediaIdx-1];
    		
    		//buildVideoMessage(Map<String, String> map,String media_id)
    		result = buildVideoMessage(map,media.getMediaId());
        }
        
        else{
            String fromUserName = map.get("FromUserName");
            // 开发者微信号
            String toUserName = map.get("ToUserName");
            result = String
                    .format(
                            "<xml>" +
                                    "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                    "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                    "<CreateTime>%s</CreateTime>" +
                                    "<MsgType><![CDATA[text]]></MsgType>" +
                                    "<Content><![CDATA[%s]]></Content>" +
                                    "</xml>",
                            fromUserName, toUserName, getUtcTime(),
                            "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");
        }

        return result;
    }

    /**
     * 构造文本消息
     *
     * @param map
     * @param content
     * @return
     */
    private static String buildTextMessage(Map<String,String> map, String content) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 文本消息XML数据格式
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" + "</xml>",
                fromUserName, toUserName, getUtcTime(), content);
    }

	/**
     *  构建图片消息
     * @param map
     * @param picUrl
     * @return
     */
    private static String buildImageMessage(Map<String, String> map,String media_id) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        /*返回指定的图片(该图片是上传为素材的,获得其media_id)*/
        //String media_id = "UCWXNCogK5ub6YFFQf7QcEpvDIYLf3Zh0L5W9i4aEp2ehfnTrASeV59x3LMD88SS";

        /*返回用户发过来的图片*/
        //String media_id = map.get("MediaId");
        return String.format(
                "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[image]]></MsgType>" +
                "<Image>" +
                "   <MediaId><![CDATA[%s]]></MediaId>" +
                "</Image>" +
                "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id
        );
    }


 	/**
     * 构造语音消息
     * @param map
     * @return
     */
    private static String buildVoiceMessage(Map<String, String> map,String media_id) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        /*返回用户发过来的语音*/
       // String media_id = map.get("MediaId");
        return String.format(
                "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[voice]]></MsgType>" +
                "<Voice>" +
                "   <MediaId><![CDATA[%s]]></MediaId>" +
                "</Voice>" +
                "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id
        );
    }


	/**
     * 回复视频消息
     * @param map
     * @return
     */
    private static String buildVideoMessage(Map<String, String> map,String media_id) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String title = "客官发过来的视频哟~~";
        String description = "客官您呐,现在肯定很开心,对不啦 嘻嘻?";
        /*返回用户发过来的视频*/
        //String media_id = map.get("MediaId");
       // String media_id = "hTl1of-w78xO-0cPnF_Wax1QrTwhnFpG1WBkAWEYRr9Hfwxw8DYKPYFX-22hAwSs";
        return String.format(
                "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[video]]></MsgType>" +
                "<Video>" +
                "   <MediaId><![CDATA[%s]]></MediaId>" +
                "   <Title><![CDATA[%s]]></Title>" +
                "   <Description><![CDATA[%s]]></Description>" +
                "</Video>" +
                "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id,title,description
        );
    }

    
    
    
    private static String getUtcTime() {
        Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
        String nowTime = df.format(dt);
        long dd = (long) 0;
        try {
            dd = df.parse(nowTime).getTime();
        } catch (Exception e) {

        }
        return String.valueOf(dd);
    }

}


