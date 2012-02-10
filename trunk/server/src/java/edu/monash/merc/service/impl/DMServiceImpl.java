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
package edu.monash.merc.service.impl;

import edu.monash.merc.common.service.pid.impl.IdentifierServiceImpl;
import edu.monash.merc.common.service.rifcs.RIFCSService;
import edu.monash.merc.domain.Activity;
import edu.monash.merc.domain.Licence;
import edu.monash.merc.domain.Party;
import edu.monash.merc.domain.RegMetadata;
import edu.monash.merc.dto.LicenceBean;
import edu.monash.merc.dto.MDRegistrationBean;
import edu.monash.merc.dto.PartyBean;
import edu.monash.merc.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * DMService Service Implementation class
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 * Modified by Sindhu Emilda - sindhu.emilda@monash.edu
 * @version 2.0
 */
@Scope("prototype")
@Service
@Transactional
public class DMServiceImpl implements DMService {


    @Autowired
    private RegMetadataService regMetadataService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private IdentifierServiceImpl identifierService;

    @Autowired
    private RIFCSService rifcsService;

    public void setRegMetadataService(RegMetadataService regMetadataService) {
        this.regMetadataService = regMetadataService;
    }

    public void setIdentifierService(IdentifierServiceImpl identifierService) {
        this.identifierService = identifierService;
    }

    public void setPartyService(PartyService partyService) {
        this.partyService = partyService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public void setLicenceService(LicenceService licenceService) {
        this.licenceService = licenceService;
    }

    public void setRifcsService(RIFCSService rifcsService) {
        this.rifcsService = rifcsService;
    }

    @Override
    public void saveRegMetadata(RegMetadata regMetadata) {
        this.regMetadataService.saveRegMetadata(regMetadata);
    }

    @Override
    public RegMetadata getRegMetadataById(long id) {
        return this.regMetadataService.getRegMetadataById(id);
    }

    @Override
    public void deleteRegMetadata(RegMetadata regMetadata) {
        this.regMetadataService.deleteRegMetadata(regMetadata);
    }

    @Override
    public void updateRegMetadata(RegMetadata regMetadata) {
        this.regMetadataService.updateRegMetadata(regMetadata);
    }

    @Override
    public RegMetadata getRegMetadatadByDatasetId(long dsId) {
        return this.regMetadataService.getRegMetadatadByDatasetId(dsId);
    }

    @Override
    public List<Party> getPartiesByRegMetadataId(long regmdId) {
        return this.partyService.getPartiesByRegMetadataId(regmdId);
    }

    @Override
    public Party getPartyByPartyKey(String partyKey) {
        return this.partyService.getPartyByPartyKey(partyKey);
    }

    @Override
    public Party getPartyByUsrNameAndEmail(String firstName, String lastName, String email) {
        return this.partyService.getPartyByUsrNameAndEmail(firstName, lastName, email);
    }

    @Override
    public void saveParty(Party party) {
        this.partyService.saveParty(party);
    }

    @Override
    public void updateParty(Party party) {
        this.partyService.updateParty(party);
    }

    @Override
    public List<Activity> getActivitiesByRegMetadataId(long regmdId) {
        return this.activityService.getActivitiesByRegMetadataId(regmdId);
    }

    @Override
    public Activity getActivityByActKey(String activityKey) {
        return this.activityService.getActivityByActKey(activityKey);
    }

    @Override
    public void saveActivity(Activity activity) {
        this.activityService.saveActivity(activity);
    }

    @Override
    public void saveLicence(Licence licence) {
        this.licenceService.saveLicence(licence);
    }
    
    @Override
    public void mergeLicence(Licence licence){
    	this.licenceService.mergeLicence(licence);	
    }

    @Override
    public void updateLicence(Licence licence) {
        this.licenceService.updateLicence(licence);
    }

    @Override
    public Licence getLicenceByRegMetadataId(long regMdId) {
        return this.licenceService.getLicenceByRegMetadataId(regMdId);
    }

    @Override
    public Licence getLicenceById(long lId) {
        return this.licenceService.getLicenceById(lId);
    }

    @Override
    public void saveRegMetadata(MDRegistrationBean mdRegistrationBean) {
        //save the party
        List<PartyBean> partyList = mdRegistrationBean.getPartyBeans();
        // parties
        List<Party> parties = new ArrayList<Party>();
        for (PartyBean partybean : partyList) {
            Party p = null;
            if (!partybean.isFromRm()) {
                // search the party detail by the person's first name and last name from the database;
                p = getPartyByUsrNameAndEmail(partybean.getPersonGivenName(), partybean.getPersonFamilyName(), partybean.getEmail());
            } else {
                String partyKey = partybean.getPartyKey();
                if (StringUtils.isNotBlank(partyKey)) {
                    // search the party detail by the party's key
                    p = getPartyByPartyKey(partybean.getPartyKey());
                } else {
                    partybean.setPartyKey(this.identifierService.genUUIDWithPrefix());
                }
            }
            // if party not found from the database, we just save it into database;
            if (p == null) {
                p = copyPartyBeanToParty(partybean);
                saveParty(p);
            }
            parties.add(p);
        }
        RegMetadata regMetadata = mdRegistrationBean.getRegMetadata();
        //add the parties
        regMetadata.setParties(parties);
        //persist the RegMetadata (save or update)
        if (regMetadata.getId() == 0) {
            this.saveRegMetadata(regMetadata);
        } else {
            this.updateRegMetadata(regMetadata);
        }
        //save the licence:
        LicenceBean licenceBean = mdRegistrationBean.getLicenceBean();
        Licence licence = copyLicenceBeanToLicence(licenceBean);
        licence.setRegMetadata(regMetadata);
        if (licence.getId() == 0) {
            Licence existedLicence = this.getLicenceByRegMetadataId(regMetadata.getId());
            if (existedLicence != null) {
                licence.setId(existedLicence.getId());
                this.mergeLicence(licence);
            } else {
                this.saveLicence(licence);
            }
        } else {
            this.updateLicence(licence);
        }
        //publish the rifcs
        this.rifcsService.publishExpRifcs(mdRegistrationBean);
    }

    private Licence copyLicenceBeanToLicence(LicenceBean licenceBean) {
        Licence licence = new Licence();
        licence.setLicenceType(licenceBean.getLicenceType());
        licence.setCommercial(licenceBean.getCommercial());
        licence.setDerivatives(licenceBean.getDerivatives());
        licence.setJurisdiction(licenceBean.getJurisdiction());
        licence.setLicenceContents(licenceBean.getLicenceContents());
        return licence;
    }

    private Party copyPartyBeanToParty(PartyBean pb) {
        Party pa = new Party();
        pa.setPartyKey(pb.getPartyKey());
        pa.setPersonTitle(pb.getPersonTitle());
        pa.setPersonGivenName(pb.getPersonGivenName());
        pa.setPersonFamilyName(pb.getPersonFamilyName());
        pa.setUrl(pb.getUrl());
        pa.setEmail(pb.getEmail());
        pa.setAddress(pb.getAddress());
        pa.setIdentifierType(pb.getIdentifierType());
        pa.setIdentifierValue(pb.getIdentifierValue());
        pa.setOriginateSourceType(pb.getOriginateSourceType());
        pa.setOriginateSourceValue(pb.getOriginateSourceValue());
        pa.setGroupName(pb.getGroupName());
        pa.setFromRm(pb.isFromRm());
        return pa;
    }
}
