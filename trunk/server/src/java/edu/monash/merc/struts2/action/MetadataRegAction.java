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

import edu.monash.merc.common.service.pid.impl.IdentifierServiceImpl;
import edu.monash.merc.config.AppPropSettings;
import edu.monash.merc.domain.RegMetadata;
import edu.monash.merc.dto.ActivityBean;
import edu.monash.merc.dto.LicenceBean;
import edu.monash.merc.dto.MDRegistrationBean;
import edu.monash.merc.dto.PartyBean;
import edu.monash.merc.service.DMService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * MetadataRegAction Action class
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 *         Modified by Sindhu Emilda - sindhu.emilda@monash.edu
 * @version 2.0
 */
@Scope("prototype")
@Controller("data.metadataRegAction")
public class MetadataRegAction extends DMBaseAction {

    @Autowired
    private IdentifierServiceImpl identifierService;

    @Autowired
    private DMService dmService;

    private List<PartyBean> partyBeans;

    private List<ActivityBean> activityBeans;

    private LicenceBean licenceBean;

    private String accessRights;

    private String anzSrcCode;

    private String physicalAddress;

    private String groupName;

    private String appName;
    
    private String rifcsStoreLocation;

    private RegMetadata regMetadata;

    //action response
    private String stringResult;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String lineSeparator = (String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));

    public void setIdentifierService(IdentifierServiceImpl identifierService) {
        this.identifierService = identifierService;
    }

    public void setDmService(DMService dmService) {
        this.dmService = dmService;
    }

    public String showMdReg() {


        return SUCCESS;
    }

    public String mdReg() {
        try {

            //do a validation before publish the metadata
            if (!validateMdRegInfo()) {
                stringResult = genErrorMsg(getText("dataset.metadata.reg.invalid.metadata.info"));
                return ERROR;
            }
            //get the previous RegMetadata if any
            RegMetadata previousRegMetadata = this.dmService.getRegMetadatadByDatasetId(regMetadata.getDatasetId());
            //if previous RegMetadata existed
            if (previousRegMetadata != null) {
                regMetadata.setId(previousRegMetadata.getId());
                regMetadata.setUniqueId(previousRegMetadata.getUniqueId());
            }
            //generate the rifcs unique id if required
            if (StringUtils.isBlank(regMetadata.getUniqueId())) {
                String monUuid = this.identifierService.genUUIDWithPrefix();
                regMetadata.setUniqueId(monUuid);
            }

            //populate the static value
            this.physicalAddress = appSetting.getPropValue(AppPropSettings.ANDS_RIFCS_PHYSICAL_LOCATION);
            this.anzSrcCode = appSetting.getPropValue(AppPropSettings.ANDS_RIFCS_REG_ANZSRC_CODE);
            this.groupName = appSetting.getPropValue(AppPropSettings.ANDS_RIFCS_REG_GROUP_NAME);
            this.accessRights = appSetting.getPropValue(AppPropSettings.ANDS_RIFCS_DATASET_ACCESS_RIGHTS);
            this.appName = getServerQName();
            this.rifcsStoreLocation = appSetting.getPropValue(AppPropSettings.ANDS_RIFCS_STORE_LOCATION);

            //create a new MDRegistrationBean
            MDRegistrationBean mdRegBean = new MDRegistrationBean();
            mdRegBean.setPhysicalAddress(physicalAddress);
            mdRegBean.setRifcsStoreLocation(rifcsStoreLocation);
            //set the RegMetadata
            mdRegBean.setRegMetadata(regMetadata);
            //set rifcs group name
            mdRegBean.setRifcsGroupName(this.groupName);
            //set access rights
            mdRegBean.setAccessRights(this.accessRights);
            //set the anzsrc code
            mdRegBean.setAnzsrcCode(this.anzSrcCode);
            //set the application name for originating source name
            mdRegBean.setAppName(this.appName);
            //set the application name for the electronic url
            mdRegBean.setElectronicURL(this.appName);
            //set the party beans
            mdRegBean.setPartyBeans(this.partyBeans);
            //set the activity beans
            mdRegBean.setActivityBeans(this.activityBeans);
            //set the licence bean
            mdRegBean.setLicenceBean(this.licenceBean);

            //save the metadata registration
            this.dmService.saveRegMetadata(mdRegBean);

            //success message
            stringResult = genSuccessMsg(getText("dataset.metadata.reg.success.message"));
        } catch (Exception ex) {
            logger.error(ex);
            //error message
            stringResult = genErrorMsg(getText("dataset.metadata.reg.action.failed"));
            return ERROR;
        }
        return SUCCESS;
    }

    private boolean validateMdRegInfo() {
        //TODO: to add validation for the metata registration info

        return true;
    }

    private String genErrorMsg(String errorMsg) {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("<response>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("<status>");
        responseBuilder.append("ERROR");
        responseBuilder.append("</status>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("<message>");
        responseBuilder.append(errorMsg);
        responseBuilder.append("</message>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("</response>");
        return responseBuilder.toString();
    }

    private String genSuccessMsg(String successMsg) {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("<response>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("<status>");
        responseBuilder.append("SUCCESS");
        responseBuilder.append("</status>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("<message>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append(successMsg);
        responseBuilder.append(lineSeparator);
        responseBuilder.append("</message>");
        responseBuilder.append(lineSeparator);
        responseBuilder.append("</response>");
        return responseBuilder.toString();
    }

    public List<PartyBean> getPartyBeans() {
        return partyBeans;
    }

    public void setPartyBeans(List<PartyBean> partyBeans) {
        this.partyBeans = partyBeans;
    }

    public List<ActivityBean> getActivityBeans() {
        return activityBeans;
    }

    public void setActivityBeans(List<ActivityBean> activityBeans) {
        this.activityBeans = activityBeans;
    }

    public LicenceBean getLicenceBean() {
        return licenceBean;
    }

    public void setLicenceBean(LicenceBean licenceBean) {
        this.licenceBean = licenceBean;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public RegMetadata getRegMetadata() {
        return regMetadata;
    }

    public void setRegMetadata(RegMetadata regMetadata) {
        this.regMetadata = regMetadata;
    }

    public String getStringResult() {
        return stringResult;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }
}
