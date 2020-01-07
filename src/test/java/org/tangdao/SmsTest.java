package org.tangdao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.tangdao.common.web.http.HttpClientUtils;

public class SmsTest {
	public static void main(String[] args) {
		String timestamps = System.currentTimeMillis() + "";
		
		String url = "http://127.0.0.1:8000/sms/send";
		String appkey="abcd";
		String appsecret="1234";
		String mobile="15821554550,13262669725,17709898766,15182939999";
		String content="【天机科技】您的验证码为8428。会员理财，试试，就可以成为会员。";
		String extNumber=null;
		String attach="";
		Map<String, Object> params = new HashMap<>();
		params.put("appkey", appkey);
		params.put("appsecret", DigestUtils.md5Hex(appsecret + mobile + timestamps));
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("timestamp", timestamps);
		params.put("extNumber", extNumber == null ? "" : extNumber);
		params.put("attach", attach);
		
		String ret = HttpClientUtils.post(url, params);
		System.out.println("ret:"+ret);
	}
}
