/*
 * Copyright (c) 2010-2011, Monash e-Research Centre
 * (Monash University, Australia)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright
 * 	  notice, this list of conditions and the following disclaimer in the
 * 	  documentation and/or other materials provided with the distribution.
 * 	* Neither the name of the Monash University nor the names of its
 * 	  contributors may be used to endorse or promote products derived from
 * 	  this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package edu.monash.merc.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import edu.monash.merc.config.AppPropSettings;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * BaseAction Action class is a base Action class, the other action classed must extend this action
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 * @version 2.0
 */
public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    @Autowired
    protected AppPropSettings appSetting;

    protected Map<String, Object> session;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    public static final String JSON = "json";

    public static final String REDIRECT = "redirect";

    protected static String NOTFOUND = "notfound";

    public static final String CHAIN = "chain";


    public void setAppSetting(AppPropSettings appSetting) {
        this.appSetting = appSetting;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getServletRequest() {
        return request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public void storeInSession(String key, Object obj) {
        this.session.put(key, obj);
    }

    public void removeFromSession(String sessionKey) {
        this.session.remove(sessionKey);
    }

    public Object findFromSession(String key) {
        return this.session.get(key);
    }

    public String getAppRealPath(String path) {
        return ServletActionContext.getServletContext().getRealPath(path);
    }

    public String getAppRoot() {
        return getAppRealPath("/");
    }

    public String getAppContextPath() {
        return ServletActionContext.getRequest().getContextPath();
    }

    public int getServerPort() {
        return ServletActionContext.getRequest().getServerPort();
    }

    public String getAppHostName() {
        return ServletActionContext.getRequest().getServerName();
    }

    public String getServerQName() {
        String scheme = request.getScheme();
        String hostName = request.getServerName();
        int port = request.getServerPort();

        StringBuffer buf = new StringBuffer();
        if (scheme.equals(ActionConts.HTTP_SCHEME)) {
            buf.append(ActionConts.HTTP_SCHEME).append(ActionConts.HTTP_SCHEME_DELIM);
        } else {
            buf.append(ActionConts.HTTPS_SCHEME).append(ActionConts.HTTP_SCHEME_DELIM);
        }
        buf.append(hostName);
        if (port == 80 || port == 443) {
            return new String(buf);
        }
        buf.append(ActionConts.COLON_DEIM).append(port);
        return new String(buf);
    }
}
