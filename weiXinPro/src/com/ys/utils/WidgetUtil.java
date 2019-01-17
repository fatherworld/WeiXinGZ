package com.ys.utils;
import com.ys.api.TranslateResponseDTO;
import com.ys.widgets.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.WebApplicationContext;
//import net.sf.json.JSONObject;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class WidgetUtil {

	 
    //进行创建菜单的接口URL
    private static final String CREATE_MENU_URL ="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
 
    //菜单查询的接口URL
    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
 
    //菜单删除的接口URL
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";


	 /**
     * 设置菜单的形式
     * @return
     */
    public static CustomerMenu initMenu(){
        CustomerMenu customeMenu = new CustomerMenu();
        ClickButton clickButton = new ClickButton();
        clickButton.setName("click菜单");
        clickButton.setType("click");
        clickButton.setKey("01");
 
        ViewButton viewButton = new ViewButton();
        viewButton.setName("view菜单");
        viewButton.setType("view");
        viewButton.setUrl("需要访问的地址");
 
        ClickButton clickButton2 = new ClickButton();
        clickButton2.setName("扫码事件的click菜单");
        clickButton2.setType("scancode_push");
        clickButton2.setKey("02");
 
        ClickButton clickButton3 = new ClickButton();
        clickButton3.setName("地理位置的click菜单");
        clickButton3.setType("location_select");
        clickButton3.setKey("03");
 
        BaseButton baseButton = new BaseButton();
        baseButton.setName("菜单");
        //将clickButton2,clickButton3作为一个子菜单中的按钮
        baseButton.setSub_button(new BaseButton[]{clickButton2,clickButton3});
        //把按钮分别放入到菜单中
        customeMenu.setButton(new BaseButton[]{clickButton,viewButton,baseButton});
 
        return customeMenu;
    }
    
    /**
     * Get请求，方便到一个url接口来获取结果
     * @param url
     * @return
     */
    public static widgetReturn doGetStr(String url){
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        widgetReturn dto = null;
        try{
            HttpResponse response = defaultHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity, "UTF-8");
                dto = new widgetReturn();
                
                
                dto=JSONObject.parseObject(result, widgetReturn.class);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }
    
    
    
    /**
     * 带参数的post请求，方便到一个url接口来获取结果
     * @param url
     * @param outStr
     * @return
     */
    public static widgetReturn doPostStr(String url , String outStr)  {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        widgetReturn dto = null;
        //JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr , "UTF-8"));
            HttpResponse response = defaultHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity, "UTF-8");
              //  jsonObject = JSONObject.fromObject(result);
                
                dto = new widgetReturn();
                
                
                dto=JSONObject.parseObject(result, widgetReturn.class);
               // jsonObject = JSONObject.
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }
   
 
    /**
     * 创建自定义菜单
     * @param token
     * @param menu
     * @return
     */
    public static int createMenu(String token , String menu){
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN" ,token);
        widgetReturn jsonObject = doPostStr(url, menu);
        if(jsonObject != null){
            //接受返回回来的参数，如果是0，就是创建成功
            result = jsonObject.getErrorCode();
            if(result==0)
            {
            	System.out.println("创建菜单栏成功");
            }
            else
            {
            	System.out.println("创建菜单栏失败，errorcode :"+result);
            }
        }
        return result;
    }
 
    /**
     * 对菜单进行查询
     * @param token
     * @return
     */
    public static  widgetReturn queryMenu(String token){
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN" ,token);
        widgetReturn jsonObject = doGetStr(url);
        return jsonObject;
    }
 
    /**
     * 对菜单进行删除
     * @param token
     * @return
     */
    public static  widgetReturn deleteMenu(String token){
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN" ,token);
        widgetReturn jsonObject = doGetStr(url);
        return jsonObject;
    }
	
	
}
