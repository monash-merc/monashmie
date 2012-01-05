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
package edu.monash.merc.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Activity domain class
 *
 * @author Simon Yu - Xiaoming.Yu@monash.edu
 * @version 2.0
 */
@Entity
@Table(name = "activity")
public class Activity extends Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "pk_generator")
    @TableGenerator(name = "pk_generator", pkColumnName = "pk_column_name", valueColumnName = "pk_column_value", pkColumnValue = "activity_pk")
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "activity_key")
    private String activityKey;

    @ManyToMany(mappedBy = "activities")
    private List<RegMetadata> regMetadataList;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the activityKey
     */
    public String getActivityKey() {
        return activityKey;
    }

    /**
     * @param activityKey the activityKey to set
     */
    public void setActivityKey(String activityKey) {
        this.activityKey = activityKey;
    }

    public List<RegMetadata> getRegMetadataList() {
        return regMetadataList;
    }

    public void setRegMetadataList(List<RegMetadata> regMetadataList) {
        this.regMetadataList = regMetadataList;
    }
}
