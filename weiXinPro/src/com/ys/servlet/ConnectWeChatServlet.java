package com.ys.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ys.utils.*;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.ys.control.*;
import java.util.Map;
import com.ys.utils.Media_Type;
import com.ys.common.ParseXml;



/**
 * Servlet implementation class ConnectWeChatServlet
 */
@WebServlet("/servlet/ConnectWeChatServlet")
public class ConnectWeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String TOKEN = "yeshan";
	
	
	private int maxMediaSize = 6;
	private String MediaDir="";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	
    public ConnectWeChatServlet() {
    	super();
    	System.out.println("-----开始构造servlet-----");
    
    	System.out.println("-----构造servlet结束-----");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("-----开始校验签名-----");
		 String signature = request.getParameter("signature");
	     String timestamp = request.getParameter("timestamp");
	        String nonce = request.getParameter("nonce");
	        String echostr = request.getParameter("echostr");
	        System.out.println(""+signature +"@"+timestamp +"$"+nonce +"^"+echostr);
	        System.out.println("aaaaa");
	        PrintWriter out = response.getWriter();
	        System.out.println("hahah");
	        /*
	        if(CheckConnectUtils.checkConncetWithWeChat(signature,timestamp,nonce)){
	            out.print(echostr);
	        }
	        */
	        /**
	         * 将token、timestamp、nonce三个参数进行字典序排序
	         * 并拼接为一个字符串
	         */
	        String sortStr = sort(TOKEN,timestamp,nonce);
	        /**
	         * 字符串进行shal加密
	         */
	        String mySignature = shal(sortStr);
	        /**
	         * 校验微信服务器传递过来的签名 和  加密后的字符串是否一致, 若一致则签名通过
	         */ if(!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature))
	         { System.out.println("-----签名校验通过-----"); 
	         response.getWriter().write(echostr); 
	         }else { 
	        	 System.out.println("-----校验签名失败-----"); 
	        	 }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		System.out.println("dopost");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("请求进入");
		String result = "";
		String msgType = "";
		//MessageUtil(int maxMediaSize,String MediaDir,Media_Type media_type)
		
		ParseXml ysParseXml = new ParseXml();
		MessageUtil ysMessageUtil;
		try { 
			
			
			 ysParseXml.parseXml(request);
			 Map<String,String> map = ysParseXml.getMap();
			 
			 System.out.println("开始构造消息"); 
			 
			 msgType = map.get("MsgType").toString();
			 
			 if(msgType.toUpperCase().equals("TEXT")){
				 
				 System.out.println("Text");
				 ysMessageUtil = new MessageUtil(6,"F:\\myEclipseWorkSpace\\WebTest\\imageData\\",Media_Type.IMAGE);
				 result = ysMessageUtil.buildXml(map);
			 }
			 
			 else if(msgType.toUpperCase().equals("VOICE")){
				 System.out.print("VOICE TYPE");
				 ysMessageUtil = new MessageUtil(2,"F:\\myEclipseWorkSpace\\WebTest\\voiceData\\",Media_Type.VOICE);
				 result = ysMessageUtil.buildXml(map);
			 }
			 else if(msgType.toUpperCase().equals("VIDEO")){
				 System.out.println("VIDEO TYPE");
				 ysMessageUtil = new MessageUtil(2,"F:\\myEclipseWorkSpace\\WebTest\\videoData\\",Media_Type.VIDEO);
				 result = ysMessageUtil.buildXml(map);
			 }
			 System.out.println(result);
			 if(result.equals("")){ 
				 result = "未正确响应"; 
				 }
			 } catch (Exception e) {
				 e.printStackTrace(); 
				 System.out.println("发生异常："+ e.getMessage());
				 } 
		response.getWriter().println(result); 
		}

	
	
	public String sort(String token, String timestamp, String nonce) {
		String[] strArray = {token, timestamp, nonce};
		Arrays.sort(strArray); 
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) { sb.append(str); }
		return sb.toString(); 
		}
	 /**
     * 字符串进行shal加密
     * @param str
     * @return
     */ public String shal(String str){
    	 try { 
    		 MessageDigest digest = MessageDigest.getInstance("SHA-1"); 
    		 digest.update(str.getBytes());
    		 byte messageDigest[] = digest.digest();
    		 StringBuffer hexString = new StringBuffer(); // 字节数组转换为 十六进制 数 
    		 for (int i = 0; i < messageDigest.length; i++) { 
    			 String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
    			 if (shaHex.length() < 2) { hexString.append(0); } 
    			 hexString.append(shaHex); 
    			 } return hexString.toString(); 
    			 } catch (NoSuchAlgorithmException e) {
    				 e.printStackTrace(); 
    			 } 
    	 return ""; 
    	 }

}
