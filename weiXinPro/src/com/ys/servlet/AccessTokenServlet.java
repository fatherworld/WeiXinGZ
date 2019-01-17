package com.ys.servlet;
import com.alibaba.fastjson.JSON;




import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ys.common.AccessToken;
import com.ys.common.AccessTokenInfo;
import com.ys.utils.NetWorkUtil;
import com.ys.common.IdAndSecret;
/**
 * Servlet implementation class AccessTokenServlet
 */

//该servlet是为了启动时候一张不停的获取token
@WebServlet("/servlet/AccessTokenServlet")
public class AccessTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = LoggerFactory.getLogger(AccessTokenServlet.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessTokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("-----启动AccessTokenServlet-----");
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
	private AccessToken getAccessToken(String appId, String appSecret) {
	   NetWorkUtil netHelper = new NetWorkUtil(); 
		
       String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
       //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
       String result = netHelper.getHttpsResponse(Url, ""); 
       System.out.println("获取到的access_token="+result); 
       //使用FastJson将Json字符串解析成Json对象
       JSONObject json = JSON.parseObject(result);
       AccessToken token = new AccessToken();
       token.setTokenName(json.getString("access_token"));
       token.setExpireSecond(json.getInteger("expires_in"));
       return token;
       }
}
