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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import edu.monash.merc.common.service.ldap.LDAPService;
import edu.monash.merc.common.service.rifcs.PartyActivityWSService;
import edu.monash.merc.dto.LDAPUser;
import edu.monash.merc.dto.PartyBean;

/**
 * @author: Sindhu Emilda
 * @email: sindhu.emilda@monash.edu
 * 
 *         Date: 27/01/12 Time: 2:00 PM
 * @version: 1.0
 */
@Scope("prototype")
@Controller("data.searchPartyAction")
public class PartyAction extends DMBaseAction {

	@Autowired
	private LDAPService ldapService;

	@Autowired
	private PartyActivityWSService partyActivitySvc;

	private String userName;

	private String password;

	private String party;

	private String stringResult;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public void setLdapService(LDAPService ldapService) {
		this.ldapService = ldapService;
	}

	public String searchParty() {

		try {
			boolean succeed = true;

			LDAPUser user = this.ldapService.searchLdapUser(party);

			String nlaId = null;
			// get researcher nla id from rm ws
			try {
				String authcateId = user.getUid();
				System.out.println("authcateId: " + authcateId);
				
				nlaId = this.partyActivitySvc.getNlaId(authcateId);
				System.out.println("nlaId: " + nlaId);
				
			} catch (Exception e) {
				System.out.println(getText("party.nlaid.notfound"));
				String errorMsg = e.getMessage();
				logger.error(getText("party.nlaid.notfound") + ", " + e);
				boolean keepdoing = false;
				if (StringUtils.containsIgnoreCase(errorMsg, "NLA Id not found") || 
						StringUtils.containsIgnoreCase(errorMsg, "Invalid authcate username")) {
					// construct reply
					succeed = false;
				} else {
					// construct reply
					succeed = false;
				}
			}

			// get the researcher party from rm ws
			PartyBean partyBean = null;
			if (nlaId != null) {
				try {
					partyBean = this.partyActivitySvc.getParty(nlaId);
					System.out.println("partyBean: " + partyBean.toString());
				} catch (Exception e) {
					System.out.println(getText("party.info.notfound"));
					String errorMsg = e.getMessage();
					logger.error(getText("party.info.notfound") + ", " + e);

					if (StringUtils.containsIgnoreCase(errorMsg, "Invalid party id")) {
						// construct reply
						succeed = false;
					} else {
						// construct reply
						succeed = false;
					}
				}
			}

			if (succeed) {
				stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Ok</test>";
				//storeInSession(ActionConts.SESS_AUTHENTICATION_FLAG, ActionConts.SESS_AUTHENCATED);
			} else {
				stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Error</test>";
			}
		} catch (Exception ex) {
			System.out.println(getText("ldap.user.notfound"));
			//stringResult = "";// error xml
			stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Error</test>";
			logger.error(getText("ldap.user.notfound") + ", " + ex);
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

	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		System.out.println("Party: " + party);
		this.party = party;
	}
}
