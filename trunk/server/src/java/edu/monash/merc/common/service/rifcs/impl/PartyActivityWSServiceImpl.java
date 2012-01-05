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

package edu.monash.merc.common.service.rifcs.impl;

import edu.monash.merc.common.service.rifcs.PartyActivityWSService;
import edu.monash.merc.config.AppPropSettings;
import edu.monash.merc.dto.ActivityBean;
import edu.monash.merc.dto.PartyBean;
import edu.monash.merc.dto.ProjectBean;
import edu.monash.merc.wsclient.rm.RmWsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PartyActivityWSServiceImpl class
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 * @version 2.0
 */
@Scope("prototype")
@Service
public class PartyActivityWSServiceImpl implements PartyActivityWSService {

    private RmWsClient wsClient;

    private boolean configured;

    @Autowired
    private AppPropSettings appSettings;

    public void setAppSettings(AppPropSettings appSettings) {
        this.appSettings = appSettings;
    }


    private void init() {
        if (wsClient == null) {
            wsClient = new RmWsClient();
            wsClient.setServiceName(appSettings.getPropValue(AppPropSettings.ANDS_PARTY_ACTIVITY_WS_NAME));
            wsClient.setTargetEndpoint(appSettings.getPropValue(AppPropSettings.ANDS_PARTY_ACTIVITY_WS_ENDPOINT));
            wsClient.setTimeout(Long.valueOf(appSettings.getPropValue(AppPropSettings.ANDS_PARTY_ACTIVITY_WS_TIMEOUT)).longValue());
            wsClient.serviceInit();
            configured = true;
        }
    }

    @Override
    public String getNlaId(String authcateId) {
        if (!configured) {
            init();
        }
        return wsClient.getNlaId(authcateId);
    }

    @Override
    public PartyBean getParty(String nlaId) {
        if (!configured) {
            init();
        }
        return wsClient.getPartyRegistryObject(nlaId);
    }

    @Override
    public List<ProjectBean> getProjects(String nlaId) {
        if (!configured) {
            init();
        }
        return wsClient.getProjects(nlaId);
    }

    @Override
    public ActivityBean getActivity(String projectId) {
        if (!configured) {
            init();
        }
        return wsClient.getActivityRegistryObject(projectId);
    }
}
