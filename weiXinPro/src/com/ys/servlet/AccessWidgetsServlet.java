package com.ys.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ys.common.AccessTokenInfo;
import com.ys.common.IdAndSecret;
import com.ys.utils.WidgetUtil;

public class AccessWidgetsServlet extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	
	static Logger logger = LoggerFactory.getLogger(AccessTokenServlet.class);  
	
	public AccessWidgetsServlet() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
		final String appId = IdAndSecret.appId;
		final String appSecret = IdAndSecret.appSecret;
		new Thread(new Runnable() {
			@Override
			public void run() { 
				while (true) {
					try { 
						//获取accessToken 
						AccessTokenInfo.accessToken = getAccessToken(appId, appSecret); 
						//获取成功 
						if (AccessTokenInfo.accessToken != null) { 
							//获取到access_token 休眠7000秒,大约2个小时左右 
							Thread.sleep(7000 * 1000);
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
				
	}
	
	public void creatMenuTest(){
        //获取到access_token
        String accessToken = AccessTokenInfo.accessToken.getTokenName();
        //获取到自定义菜单的格式（JSONObject将对象转为json，然后再需要转为字符串型）
        
        
        String menu = JSONObject.fromObject(WidgetUtil.initMenu()).toString();
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
