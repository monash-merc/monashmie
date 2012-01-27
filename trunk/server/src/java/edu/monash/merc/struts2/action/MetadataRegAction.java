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

import edu.monash.merc.common.service.ldap.LDAPService;
import edu.monash.merc.common.service.rifcs.PartyActivityWSService;
import edu.monash.merc.domain.Activity;
import edu.monash.merc.domain.Licence;
import edu.monash.merc.domain.Party;
import edu.monash.merc.dto.PartyBean;
import edu.monash.merc.dto.ProjectBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * MetadataRegAction Action class
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 * Modified by Sindhu Emilda - sindhu.emilda@monash.edu
 * @version 2.0
 */
@Scope("prototype")
@Controller("data.metadataRegAction")
public class MetadataRegAction extends DMBaseAction {

    @Autowired
    private PartyActivityWSService paWsService;

    @Autowired
    private LDAPService ldapService;

    private String authencatId;

    private String nlaId;

    private List<PartyBean> partyList;

    private List<ProjectBean> projectList;

    private Licence licence;

    private String accessRights;

    private Map<String, String> addPartyTypeMap = new LinkedHashMap<String, String>();

    private String addPartyType;

    private String anzSrcCode;

    private String physicalAddress;

    private PartyBean addedPartyBean;
    
    private String stringResult;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public String showMdReg() {


        return SUCCESS;
    }


    public String addRMParty() {

        return SUCCESS;
    }

    public String addUDParty() {

        return SUCCESS;
    }


    public String mdReg() {

    	stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Data</test>";
        return SUCCESS;
    }

    private void populateAllParties(List<Party> existedParties, PartyBean rmpb) {
        if (partyList == null) {
            partyList = new ArrayList<PartyBean>();
            // add the rm party
            if (rmpb != null) {
                partyList.add(rmpb);
            }
        }
        if (existedParties != null && existedParties.size() > 0) {
            for (Party party : existedParties) {
                String partykey = party.getPartyKey();
                // create a previous PartyBean
                PartyBean existedParty = copyPartyToPartyBean(party);
                existedParty.setSelected(true);
                // if rmpb is not null, then we compare it with exsited parties which previous populated
                // if key is not the same, then we add it into the list,
                // if rmpb is null, then we add all existed parties
                if (rmpb != null) {
                    String rmPbKey = rmpb.getPartyKey();
                    if (!rmPbKey.equals(partykey)) {
                        partyList.add(existedParty);
                    }
                } else {
                    partyList.add(existedParty);
                }
            }
        }
    }

    private void popilateAllActivities(List<Activity> existedActivities, List<ProjectBean> rmProjList) {
        // sign the rm project summary list to project list
        projectList = rmProjList;
        if (projectList != null && projectList.size() > 0) {
            for (ProjectBean projb : projectList) {
                if (existedActivities != null && existedActivities.size() > 0) {
                    for (Activity a : existedActivities) {
                        if (projb.getActivityKey().equals(a.getActivityKey())) {
                            projb.setSelected(true);
                        }
                    }
                }
            }
        }
    }

    private PartyBean copyPartyToPartyBean(Party p) {
        PartyBean pb = new PartyBean();
        pb.setPartyKey(p.getPartyKey());
        pb.setPersonTitle(p.getPersonTitle());
        pb.setPersonGivenName(p.getPersonGivenName());
        pb.setPersonFamilyName(p.getPersonFamilyName());
        pb.setUrl(p.getUrl());
        pb.setEmail(p.getEmail());
        pb.setAddress(p.getAddress());
        pb.setIdentifierType(p.getIdentifierType());
        pb.setIdentifierValue(p.getIdentifierValue());
        pb.setOriginateSourceType(p.getOriginateSourceType());
        pb.setOriginateSourceValue(p.getOriginateSourceValue());
        pb.setGroupName(p.getGroupName());
        pb.setFromRm(p.isFromRm());
        return pb;
    }


    public void setPaWsService(PartyActivityWSService paWsService) {
        this.paWsService = paWsService;
    }

    public void setLdapService(LDAPService ldapService) {
        this.ldapService = ldapService;
    }

    public String getNlaId() {
        return nlaId;
    }

    public void setNlaId(String nlaId) {
        this.nlaId = nlaId;
    }

    public List<PartyBean> getPartyList() {
        return partyList;
    }

    public void setPartyList(List<PartyBean> partyList) {
        this.partyList = partyList;
    }

    public List<ProjectBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectBean> projectList) {
        this.projectList = projectList;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public Map<String, String> getAddPartyTypeMap() {
        return addPartyTypeMap;
    }

    public void setAddPartyTypeMap(Map<String, String> addPartyTypeMap) {
        this.addPartyTypeMap = addPartyTypeMap;
    }

    public String getAddPartyType() {
        return addPartyType;
    }

    public void setAddPartyType(String addPartyType) {
        this.addPartyType = addPartyType;
    }

    public String getAnzSrcCode() {
        return anzSrcCode;
    }

    public void setAnzSrcCode(String anzSrcCode) {
        this.anzSrcCode = anzSrcCode;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public PartyBean getAddedPartyBean() {
        return addedPartyBean;
    }

    public void setAddedPartyBean(PartyBean addedPartyBean) {
        this.addedPartyBean = addedPartyBean;
    }


	public String getStringResult() {
		return stringResult;
	}


	public void setStringResult(String stringResult) {
		this.stringResult = stringResult;
	}
    
}
