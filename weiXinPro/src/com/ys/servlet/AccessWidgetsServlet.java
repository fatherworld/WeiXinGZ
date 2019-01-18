package com.ys.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ys.common.AccessTokenInfo;
import com.ys.common.IdAndSecret;
import com.ys.utils.WidgetUtil;
import com.ys.common.AccessTokenInfo;

public class AccessWidgetsServlet extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	
	static Logger logger = LoggerFactory.getLogger(AccessTokenServlet.class);  
	
	public AccessWidgetsServlet() {
		super();
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
		super.init();
		/*
		final String appId = getInitParameter("appId");
		final String appSecret = getInitParameter("appSecret");
		*/
		/*
		final String appId = "wxe862d02e1a98fbc2";
		final String appSecret = "d16bb091078f0da6739bf62278e1166d";
		*/
		
		System.out.println("init ");
		
		System.out.println("AccessTokenInfo.accessToken"+AccessTokenInfo.accessToken);
		
				
		AccessTokenInfo.accessToken = AccessTokenInfo.accessToken; 
		
		creatMenu(AccessTokenInfo.accessToken.getTokenName());
		
		
		/*
		new Thread(new Runnable() {
			@Override
			public void run() { 
				while (true) {
					try { 
						//获取accessToken 
						//AccessTokenInfo.accessToken = AccessTokenInfo.accessToken; 
						//获取成功 
						if (AccessTokenInfo.accessToken != null) { 
							//获取到access_token 休眠7000秒,大约2个小时左右 
							//Thread.sleep(7000 * 1000);
							creatMenu(AccessTokenInfo.accessToken.getTokenName());
							
							} else { //获取失败
								Thread.sleep(1000 * 3);
								//获取的access_token为空 休眠3秒 
								} 
						} catch (Exception e) { 
							System.out.println("发生异常：" + e.getMessage());
							e.printStackTrace(); 
							try { 
								Thread.sleep(1000 * 10);
								//发生异常休眠1秒 
								} 
								catch (Exception e1) {
									e.printStackTrace();
									} 
							} 
					} 
				} 
			}).start(); 
			*/
				
	}
	
	public void creatMenu(String accessToken){
        //获取到access_token
       // String accessToken = AccessTokenInfo.accessToken.getTokenName();
        //获取到自定义菜单的格式（JSONObject将对象转为json，然后再需要转为字符串型）
        //javaObject 与 javaBean_Obj间的转换，基于fast Json
		
		System.out.println("creatMenu " );
        String menu = JSON.toJSONString(WidgetUtil.initMenu());
        
        System.out.println("MenuString " + menu);
        
     //   String menu = JSONObject.fromObject(WidgetUtil.initMenu()).toString();
        //调用创建菜单
        int result = WidgetUtil.createMenu(accessToken, menu);
        if(result == 0){
            //如果调用方法之后，返回的是0，那么就表示创建成功。
            System.out.println("创建成功");
        }else{
            System.out.println("创建失败");
        }
    }

	

}
