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
package edu.monash.merc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AppPropSettings class
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 * @version 2.0
 */

@Scope("singleton")
@Component
public class AppPropSettings {

    public static String APPLICATION_NAME = "application.name";

    public static String SYSTEM_ADMIN_EMAIL = "admin.user.email";

    public static String SYSTEM_ADMIN_NAME = "admin.user.display.name";

    public static String SYSTEM_ADMIN_PWD = "admin.user.password";

    public static String SYSTEM_SERVICE_EMAIL = "system.service.email";

    // ANDS RIF-CS Configuration
    public static String ANDS_RIFCS_REG_ENABLED = "ands.rifcs.register.enabled";

    public static String ANDS_RIFCS_STORE_LOCATION = "ands.rifcs.files.store.location";

    public static String ANDS_RIFCS_PHYSICAL_LOCATION = "ands.rifcs.physical.location";

    public static String ANDS_RIFCS_DATASET_ACCESS_RIGHTS = "ands.rifcs.dataset.access.rights";

    public static String EXPERIMENT_UUID_PREFIX = "ands.rifcs.uuid.prefix";

    public static String ANDS_RIFCS_REG_GROUP_NAME = "ands.rifcs.register.group.name";

    public static String ANDS_RIFCS_REG_ANZSRC_CODE = "ands.rifcs.register.anzsrc.code";

    public static String ANDS_PARTY_ACTIVITY_WS_NAME = "ands.party.activtiy.rm.ws.name";

    public static String ANDS_PARTY_ACTIVITY_WS_ENDPOINT = "ands.party.activtiy.rm.ws.endpoint";

    public static String ANDS_PARTY_ACTIVITY_WS_TIMEOUT = "ands.party.activtiy.rm.ws.timeout";

    // Mail Server Configuration
    public static String SMTP_MAIL_SERVER = "smtp.mail.server";

    // LDAP Configuration
    public static String LDAP_AUTH_SUPPORTED = "ldap.authentication.supported";

    public static String LDAP_FACTORY = "ldap.factory";

    public static String LDAP_SERVER_URL = "ldap.server.url";

    public static String LDAP_BASE_DN = "ldap.base.dn";

    public static String LDAP_SECURITY_PROTOCOL = "ldap.security.protocol";

    public static String LDAP_AUTHENTICATION = "ldap.authentication";

    public static String LDAP_UID_ATTR_NAME = "ldap.uid.attr.name";

    public static String LDAP_MAIL_ATTR_NAME = "ldap.mail.attr.name";

    public static String LDAP_CN_ATTR_NAME = "ldap.cn.attr.name";

    public static String LDAP_GENDER_ATTR_NAME = "ldap.gender.attr.name";

    public static String LDAP_SN_ATTR_NAME = "ldap.sn.attr.name";

    public static String LDAP_GIVENNAME_ATTR_NAME = "ldap.givenname.attr.name";

    public static String LDAP_PERSONAL_TITLE_ATTR_NAME = "ldap.personaltitle.attr.name";


    @Autowired
    @Qualifier("appPropConfigurer")
    private ApplicationPropertyConfigurater appPropConfigurater;

    public ApplicationPropertyConfigurater getAppPropConfigurater() {
        return appPropConfigurater;
    }

    public void setAppPropConfigurater(ApplicationPropertyConfigurater appPropConfigurater) {
        this.appPropConfigurater = appPropConfigurater;
    }

    public String getPropValue(String propKey) {
        String propValue = this.appPropConfigurater.getPropValue(propKey);
        if (propValue != null) {
            propValue = propValue.trim();
        }
        return propValue;
    }

    public Map<String, String> getResolvedProps() {
        return this.appPropConfigurater.getResolvedProps();
    }
}
