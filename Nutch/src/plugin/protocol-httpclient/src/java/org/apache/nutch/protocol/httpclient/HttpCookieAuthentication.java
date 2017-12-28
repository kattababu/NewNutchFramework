
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nutch.protocol.httpclient;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * An implementation of authentication through cookies. Currently
 * performs a POST on the given URL, adding any resulting cookies
 * to the client.
 * 
 * Cookies must be checked against their expiration dates with each
 * request to ensure they haven't expired.
 *
 * @author Jasper van Veghel <jasper@seajas.com>
 */
public class HttpCookieAuthentication  {
  /**
   * POSTs to the given <code>url</code> and stores the resulting cookies.
   *
   * @param http                An instance of the implementation class
   *                            of this plugin
   * @param url                 URL to be fetched
   * @param followRedirects     Whether to follow redirects; follows
   *                            redirect if and only if this is true
   * @return                    HTTP cookie authentication
   * @throws IOException        When an error occurs
   */
  HttpCookieAuthentication(Http http, URL url, boolean followRedirects) throws IOException {
    // Add cookies if we haven't retrieved any yet or if they've expired

    if (Http.getClient().getState() != null && Http.getClient().getState().getCookies().length > 0) {
      boolean expired = false;

      for (Cookie cookie : Http.getClient().getState().getCookies()) {
        if (cookie.isExpired()) {
          expired = true;
      
          break;
        }
      }

      // We have cookies and none of them have expired yet; don't get new ones

     if (!expired)
        return;
    }

    // Prepare POST method for HTTP request

    PostMethod method = new PostMethod(url.toString());
    method.setFollowRedirects(followRedirects);
    method.setDoAuthentication(true);

    // Set HTTP parameters

    HttpMethodParams parameters = method.getParams();

    if (http.getUseHttp11()) {
      parameters.setVersion(HttpVersion.HTTP_1_1);
    } else {
      parameters.setVersion(HttpVersion.HTTP_1_0);
    }

    parameters.makeLenient();
    parameters.setContentCharset("UTF-8");
    parameters.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
    parameters.setBooleanParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);

    try {
      int code = Http.getClient().executeMethod(method);

      if (code == 200 && Http.LOG.isTraceEnabled()) {
        Http.LOG.trace("url: " + url +
            "; status code: " + code +
            "; cookies received: " + Http.getClient().getState().getCookies().length);
      } else {
          Http.LOG.error("Unable to retrieve login page; code = " + code);
      }
    } finally {
      method.releaseConnection();
    }
  }
}
//No newline at end of file
