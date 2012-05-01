package edu.monash.merc.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 *
 * Date: 4/01/12
 * Time: 5:21 PM
 * @version 1.0
 */

@Entity
@Table(name = "regmd")
public class RegMetadata extends Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "pk_generator")
    @TableGenerator(name = "pk_generator", pkColumnName = "pk_column_name", valueColumnName = "pk_column_value", pkColumnValue = "regmd_pk")
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "loginId")
    private String uid;

    @Basic
    @Column(name = "unique_id")
    private String uniqueId;

    @Basic
    @Column(name = "dataset_id")
    private long datasetId;


    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description", length=2000)
    private String description;

    @OneToOne(mappedBy = "regMetadata", targetEntity = Licence.class, fetch = FetchType.LAZY)
    @Cascade({CascadeType.DELETE})
    private Licence licence;

    @ManyToMany(targetEntity = Activity.class)
    @JoinTable(name = "regmd_activity", joinColumns = {@JoinColumn(name = "regmd_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "activity_id", referencedColumnName = "id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {
            "regmd_id", "activity_id"})})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Activity> activities = new ArrayList<Activity>();

    @ManyToMany(targetEntity = Party.class)
    @JoinTable(name = "regmd_party", joinColumns = {@JoinColumn(name = "regmd_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {
            "regmd_id", "party_id"})})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Party> parties = new ArrayList<Party>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(long datasetId) {
        this.datasetId = datasetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }
}
