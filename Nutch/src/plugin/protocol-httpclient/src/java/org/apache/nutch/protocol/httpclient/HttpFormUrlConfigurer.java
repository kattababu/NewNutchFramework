package org.apache.nutch.protocol.httpclient;



import java.util.HashMap;

import java.util.HashSet;

import java.util.Map;

import java.util.Set;



public class HttpFormUrlConfigurer {

	private String loginUrl;

	//private String loginFormId;

	/**

	 * The data posted to login form, such as username(or email), password

	 */

	private Map<String, String> loginPostData;

	/**

	 * In case we need add additional headers.



	 */

	private Map<String, String> additionalPostHeaders;

	

	private boolean loginRedirect;

	/**

	 * Used when we need remove some form fields.

	 */

	



	public HttpFormUrlConfigurer() {

	}



	public String getLoginUrl() {

		return loginUrl;

	}



	public HttpFormUrlConfigurer setLoginUrl(String loginUrl) {

		this.loginUrl = loginUrl;

		return this;

	}







	public Map<String, String> getLoginPostData() {

		return loginPostData == null ? new HashMap<String, String>()

			: loginPostData;

	}



	public HttpFormUrlConfigurer setLoginPostData(

			Map<String, String> loginPostData) {

		this.loginPostData = loginPostData;

		return this;

	}





	public Map<String, String> getAdditionalPostHeaders() {

		return additionalPostHeaders == null ? new HashMap<String, String>()

				: additionalPostHeaders;

	}



	public HttpFormUrlConfigurer setAdditionalPostHeaders(

			Map<String, String> additionalPostHeaders) {

		this.additionalPostHeaders = additionalPostHeaders;

		return this;

	}



	

	

	



	public boolean isLoginRedirect() {

		return loginRedirect;

	}

	public HttpFormUrlConfigurer setLoginRedirect(boolean redirect) {

		this.loginRedirect = redirect;

	return this;



	}



	

}