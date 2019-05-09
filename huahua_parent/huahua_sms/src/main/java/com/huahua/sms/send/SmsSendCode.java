package com.huahua.sms.send;

import com.huahua.sms.utils.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class SmsSendCode {
    public static void sendCode(String appcode,String mobile,String tplId,String code) {
        /**
         * 功能描述：发送短信验证码
         * @Author LiHuaMing
         * @Date 22:52 2019/4/24
         * @Param [appcode 短信签名, mobile 手机号, tplId 模板id, code 验证码]
         * @return void
         */
        String host = "http://dingxintz.market.alicloudapi.com";
        String path = "/dx/notifySms";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        querys.put("param", "name:"+code);
        querys.put("tpl_id", tplId);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
