package org.apache.nutch.protocol.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
//import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.io.InputStreamReader;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFormUrl {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpFormUrl.class);
private static final CookieManager COOKIEMANAGER = new CookieManager();
	private static Map<String, String> defaultLoginHeaders = new HashMap<String, String>();

	static {
		defaultLoginHeaders.put("User-Agent", "Mozilla/5.0");
		defaultLoginHeaders
				.put("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	defaultLoginHeaders.put("Accept-Language", "en-US,en;q=0.5");
		defaultLoginHeaders.put("Connection", "keep-alive");
		defaultLoginHeaders.put("Content-Type",
				"application/x-www-form-urlencoded");
	}
	private HttpClient client;
	private HttpFormUrlConfigurer urlConfigurer = new HttpFormUrlConfigurer();
	private String cookies;

private String spicyurl=urlConfigurer.getLoginUrl();
 List<NameValuePair> postParams=null;

public HttpFormUrl(HttpFormUrlConfigurer urlConfigurer,
			HttpClient client, Http http) {
		this.urlConfigurer = urlConfigurer;
		this.client = client;
		defaultLoginHeaders.put("Accept", http.getAccept());
		defaultLoginHeaders.put("Accept-Language", http.getAcceptLanguage());
		defaultLoginHeaders.put("User-Agent", http.getUserAgent());
	}


	public HttpFormUrl(String loginUrl) {
		this.urlConfigurer.setLoginUrl(loginUrl);
		
		//this.client = new HttpClient();


		
	}





 public void login() throws Exception, UnsupportedEncodingException {
   // client.setRedirectStrategy(new LaxRedirectStrategy());
    // make sure cookies is turn on
//COOKIEMANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    //CookieHandler.setDefault(COOKIEMANAGER);
client.getParams().setParameter("http.protocol.single-cookie-header", true);  
client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);

client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

    String pageContent = httpGetPageContent(urlConfigurer.getLoginUrl());
//LOGGER.info("url"+pageContent);

  postParams = getLoginFormParams(pageContent);
LOGGER.info("postParams"+postParams);
//sendPost(urlConfigurer.getLoginUrl(), postParams);
System.exit(0);
   
//LOGGER.info("post url:"+urlConfigurer.getLoginUrl());
  }
	
private void sendPost(String url, List<NameValuePair> params)
			throws Exception {
LOGGER.info("post url:"+url);
PrintStream out=null;
	PostMethod post = null;
		try {
			if (urlConfigurer.isLoginRedirect()) {
				post = new PostMethod(url) {
					@Override
					public boolean getFollowRedirects() {
						return true;
					}
				};
			} else {
				post = new PostMethod(url);
			}
			// we can't use post.setFollowRedirects(true) as it will throw
			// IllegalArgumentException:
			// Entity enclosing requests cannot be redirected without user
			// intervention
			//LOGGER.info("Post Url"+post);
			//setLoginHeader(post);
 NameValuePair[] nvPairArray = new NameValuePair[params.size()];
			post.addParameters(params.toArray(nvPairArray));
			LOGGER.info("Post Url"+post.getParameters());
			int rspCode = client.executeMethod(post);
LOGGER.info("responsecode"+rspCode);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("rspCode: " + rspCode);
				LOGGER.debug("\nSending 'POST' request to URL : " + url);

				LOGGER.debug("Post parameters : " + params);
				LOGGER.debug("Response Code : " + rspCode);
				for (Header header : post.getRequestHeaders()) {
					LOGGER.debug("Response headers : " + header);
LOGGER.info("Response headers : " + header);
			}
			}
			String rst = IOUtils.toString(post.getResponseBodyAsStream());



			LOGGER.debug("login post result: " + rst);

out = new PrintStream(new FileOutputStream("/NutchCrawl/Nutch/runtime/local/TextData/spicylang.html",true));
System.setOut(out);
 System.lineSeparator(); 
System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& HAPPY CODING DATA &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
System.out.println(rst);




//LOGGER.info("login post result: " + rst);
		} finally {
		if (post != null) {
//out.close();
//
				post.releaseConnection();

			}
		}
 

	}
/*
	private void setLoginHeader(PostMethod post) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.putAll(defaultLoginHeaders);
		// additionalPostHeaders can overwrite value in defaultLoginHeaders
		//headers.putAll(authConfigurer.getAdditionalPostHeaders());
		for (Entry<String, String> entry : headers.entrySet()) {
			post.addRequestHeader(entry.getKey(), entry.getValue());
		}
		post.addRequestHeader("Cookie", getCookies());
LOGGER.info(" Post Cookies Header: " + getCookies());
	}
*/
	private String httpGetPageContent(String url) throws IOException {

		GetMethod get = new GetMethod(url);
Header cookieHeader=null;
String rst =null;

		try {
			
			client.executeMethod(get);
			cookieHeader = get.getResponseHeader("Set-Cookie");
LOGGER.info(" Get Cookies Header: " + cookieHeader);
			
		} finally {
			


				//setCookies(cookieHeader.getValue());
			//LOGGER.info("Cookies Header: " + cookieHeader);	//break;
//System.exit(1);
rst = IOUtils.toString(get.getResponseBodyAsStream());

get.releaseConnection();


			
			
		}




			return rst;


	}

	private List<NameValuePair> getLoginFormParams(String pageContent)
			throws UnsupportedEncodingException,Exception {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
/*
				
				params.add(new NameValuePair("language", "hi"));
				params.add(new NameValuePair("type", "all"));
				params.add(new NameValuePair("countryCode", "IN"));

*/


//String langs[]={"te","bn","hi","}

 Map<String, List<String>> map = new HashMap<String, List<String>>();
List<String> valSetOne = new ArrayList<String>();
	


	valSetOne.add("all");        
	valSetOne.add("te");
        valSetOne.add("bn");
	valSetOne.add("hi");
	valSetOne.add("en");
	valSetOne.add("kn");
	valSetOne.add("ta");
	valSetOne.add("ml");
	valSetOne.add("mr");
	valSetOne.add("pa");


        // create list two and store values
       // List<String> valSetTwo = new ArrayList<String>();
       // valSetTwo.add("all");
        //valSetTwo.add("Banana");
        // create list three and store values
       // List<String> valSetThree = new ArrayList<String>();
       // valSetThree.add("IN");
       // valSetThree.add("Car");


 // put values into map
        map.put("language", valSetOne);
        //map.put("type", valSetTwo);
        //map.put("countryCode", valSetThree);

for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            //System.out.println("Key = " + key);
            //System.out.println("Values = " + values + "n");
LOGGER.info(" Get Param Values: " + values);

for(String value:values)
{

if(value.equals("all")||value.equals("te")||value.equals("bn")||value.equals("hi")||value.equals("en")||value.equals("kn")||value.equals("ta")||value.equals("ml")||value.equals("mr")||value.equals("pa"))
{
LOGGER.info(" Each Param Values: " + value);
                      
			          params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//

//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//System.out.println("Welcome to India and Happy to Visting again");
//break;
}

/*


if(value.equals("te"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//break;

//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//break;
}
if(value.equals("bn"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//break;

}

if(value.equals("hi"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//break;

}
if(value.equals("en"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);

//break;
}
if(value.equals("kn"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);

//break;
}

if(value.equals("ta"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//break;

}
if(value.equals("ml"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//break;

}
if(value.equals("mr"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);
//break;

}
if(value.equals("pa"))
{
                                params.add(new NameValuePair(key,value));
				params.add(new NameValuePair("type","all"));
				params.add(new NameValuePair("countryCode","IN"));
//login();
sendPost(urlConfigurer.getLoginUrl(), params);

//break;
}
*/
				


}


				




        }






		
		return params;
	}
/*

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
}*/

	public boolean isRedirect() {
		return urlConfigurer.isLoginRedirect();
	}

	public void setRedirect(boolean redirect) {
		this.urlConfigurer.setLoginRedirect(redirect);
	}

}
