package org.apache.nutch.protocol.httpclient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HttpFormAuthConfigurer {
	private String loginUrl;
	private String loginFormId;
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
	private Set<String> removedFormFields;

	public HttpFormAuthConfigurer() {
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public HttpFormAuthConfigurer setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
		return this;
	}

	public String getLoginFormId() {
		return loginFormId;
	}

	public HttpFormAuthConfigurer setLoginFormId(String loginForm) {
		this.loginFormId = loginForm;
		return this;
	}

	public Map<String, String> getLoginPostData() {
		return loginPostData == null ? new HashMap<String, String>()
			: loginPostData;
	}

	public HttpFormAuthConfigurer setLoginPostData(
			Map<String, String> loginPostData) {
		this.loginPostData = loginPostData;
		return this;
	}

	public Map<String, String> getAdditionalPostHeaders() {
		return additionalPostHeaders == null ? new HashMap<String, String>()
				: additionalPostHeaders;
	}

	public HttpFormAuthConfigurer setAdditionalPostHeaders(
			Map<String, String> additionalPostHeaders) {
		this.additionalPostHeaders = additionalPostHeaders;
		return this;
	}

	public boolean isLoginRedirect() {
		return loginRedirect;
	}
	public HttpFormAuthConfigurer setLoginRedirect(boolean redirect) {
		this.loginRedirect = redirect;
	return this;

	}

	public Set<String> getRemovedFormFields() {
		return removedFormFields == null ? new HashSet<String>()
				: removedFormFields;
	}

	public HttpFormAuthConfigurer setRemovedFormFields(
			Set<String> removedFormFields) {
		this.removedFormFields = removedFormFields;
		return this;
	}
}