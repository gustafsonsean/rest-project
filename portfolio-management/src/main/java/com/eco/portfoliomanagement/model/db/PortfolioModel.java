package com.eco.portfoliomanagement.model.db;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class PortfolioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private String guid;
    private Date dateAdded;
    private Date dateLastModified;

    private String name;
    private String description;
    private String createdOn;
    private int cashHoldingPercentage;
    private int driftPercentage;
    private ModelType modelType;
    private RebalanceFrequency rebalanceFrequency;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Advisor advisor;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioModelAssetAllocation> portfolioModelAssetAllocations;

    public enum ModelType {
        QUALIFIED,
        TAXABLE
    }

    public enum RebalanceFrequency {
        MONTHLY,
        QUARTERLY,
        SEMI_ANNUAL,
        ANNUAL
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid( String guid ) {
        this.guid = guid;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded( Date dateAdded ) {
        this.dateAdded = dateAdded;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified( Date dateLastModified ) {
        this.dateLastModified = dateLastModified;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( String createdOn ) {
        this.createdOn = createdOn;
    }

    public int getCashHoldingPercentage() {
        return cashHoldingPercentage;
    }

    public void setCashHoldingPercentage( int cashHoldingPercentage ) {
        this.cashHoldingPercentage = cashHoldingPercentage;
    }

    public int getDriftPercentage() {
        return driftPercentage;
    }

    public void setDriftPercentage( int driftPercentage ) {
        this.driftPercentage = driftPercentage;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setModelType( ModelType modelType ) {
        this.modelType = modelType;
    }

    public RebalanceFrequency getRebalanceFrequency() {
        return rebalanceFrequency;
    }

    public void setRebalanceFrequency( RebalanceFrequency rebalanceFrequency ) {
        this.rebalanceFrequency = rebalanceFrequency;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor( Advisor advisor ) {
        this.advisor = advisor;
    }

    public List<PortfolioModelAssetAllocation> getPortfolioModelAssetAllocations() {
        return portfolioModelAssetAllocations;
    }

    public void setPortfolioModelAssetAllocations( List<PortfolioModelAssetAllocation> portfolioModelAssetAllocations ) {
        this.portfolioModelAssetAllocations = portfolioModelAssetAllocations;
    }
}
