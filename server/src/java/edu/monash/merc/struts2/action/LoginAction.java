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

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import edu.monash.merc.common.service.ldap.LDAPService;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 * Modified by Sindhu Emilda - sindhu.emilda@monash.edu
 * 
 *         Date: 5/01/12 Time: 2:01 PM
 * @version 1.0
 */
@Scope("prototype")
@Controller("user.loginAction")
public class LoginAction extends DMBaseAction {

	@Autowired
	private LDAPService ldapService;

	private String userName;

	private String password;

	private String stringResult;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public void setLdapService(LDAPService ldapService) {
		this.ldapService = ldapService;
	}

	public String login() {

		try {
			boolean succeed = this.ldapService.ldapUserLogin(userName, password);
			
			if (succeed) {
				stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><status>SUCCESS</status>";
				storeInSession(ActionConts.SESS_AUTHENTICATION_FLAG, ActionConts.SESS_AUTHENCATED);
			}
		} catch (Exception ex) {
			stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><status>ERROR</status>";
			logger.error(ex.getMessage());
			return ERROR;
		}

		return SUCCESS;
	}

	public String getStringResult() {
		return stringResult;
	}

	public void setStringResult(String stringResult) {
		this.stringResult = stringResult;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
