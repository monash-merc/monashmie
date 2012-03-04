package edu.monash.merc.dto;

import java.io.Serializable;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 *
 * Date: 30/01/12
 * Time: 12:05 PM
 * @version 1.0
 */
public class LicenceBean implements Serializable {

    private long id;

    private String licenceType;

    private String commercial;

    private String derivatives;

    private String jurisdiction;

    private String licenceContents;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getCommercial() {
        return commercial;
    }

    public void setCommercial(String commercial) {
        this.commercial = commercial;
    }

    public String getDerivatives() {
        return derivatives;
    }

    public void setDerivatives(String derivatives) {
        this.derivatives = derivatives;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getLicenceContents() {
        return licenceContents;
    }

    public void setLicenceContents(String licenceContents) {
        this.licenceContents = licenceContents;
    }
}
