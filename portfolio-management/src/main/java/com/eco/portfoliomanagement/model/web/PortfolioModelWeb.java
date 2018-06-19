
package com.eco.portfoliomanagement.model.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioModelWeb {

    /**
     * guid for model
     * (Required)
     * 
     */
    private String guid;
    /**
     * Unique Name for the model
     * (Required)
     * 
     */
    private String name;
    /**
     * Description of the model
     * (Required)
     * 
     */
    private String description;
    /**
     * Percentage of cash to hold in the model
     * (Required)
     * 
     */
    private Integer cashHoldingPercentage;
    /**
     * Percentage of drift from target allocation of assets
     * (Required)
     * 
     */
    private Integer driftPercentage;
    /**
     * Date model created
     * 
     */
    private String createdOn;
    /**
     * Type of model
     * (Required)
     * 
     */
    private PortfolioModelWeb.ModelType modelType;
    /**
     * Frequency to rebalance model
     * (Required)
     * 
     */
    private PortfolioModelWeb.RebalanceFrequency rebalanceFrequency;
    /**
     * Advisor who manages the model, guid
     * (Required)
     * 
     */
    private Object advisorId;
    /**
     * Asset Allocations
     * (Required)
     * 
     */
    private List<AssetAllocation> assetAllocations = null;

    /**
     * guid for model
     * (Required)
     * 
     */
    public String getGuid() {
        return guid;
    }

    /**
     * guid for model
     * (Required)
     * 
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * Unique Name for the model
     * (Required)
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Unique Name for the model
     * (Required)
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description of the model
     * (Required)
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description of the model
     * (Required)
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Percentage of cash to hold in the model
     * (Required)
     * 
     */
    public Integer getCashHoldingPercentage() {
        return cashHoldingPercentage;
    }

    /**
     * Percentage of cash to hold in the model
     * (Required)
     * 
     */
    public void setCashHoldingPercentage(Integer cashHoldingPercentage) {
        this.cashHoldingPercentage = cashHoldingPercentage;
    }

    /**
     * Percentage of drift from target allocation of assets
     * (Required)
     * 
     */
    public Integer getDriftPercentage() {
        return driftPercentage;
    }

    /**
     * Percentage of drift from target allocation of assets
     * (Required)
     * 
     */
    public void setDriftPercentage(Integer driftPercentage) {
        this.driftPercentage = driftPercentage;
    }

    /**
     * Date model created
     * 
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * Date model created
     * 
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Type of model
     * (Required)
     * 
     */
    public PortfolioModelWeb.ModelType getModelType() {
        return modelType;
    }

    /**
     * Type of model
     * (Required)
     * 
     */
    public void setModelType(PortfolioModelWeb.ModelType modelType) {
        this.modelType = modelType;
    }

    /**
     * Frequency to rebalance model
     * (Required)
     * 
     */
    public PortfolioModelWeb.RebalanceFrequency getRebalanceFrequency() {
        return rebalanceFrequency;
    }

    /**
     * Frequency to rebalance model
     * (Required)
     * 
     */
    public void setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency rebalanceFrequency) {
        this.rebalanceFrequency = rebalanceFrequency;
    }

    /**
     * Advisor who manages the model, guid
     * (Required)
     * 
     */
    public Object getAdvisorId() {
        return advisorId;
    }

    /**
     * Advisor who manages the model, guid
     * (Required)
     * 
     */
    public void setAdvisorId(Object advisorId) {
        this.advisorId = advisorId;
    }

    /**
     * Asset Allocations
     * (Required)
     * 
     */
    public List<AssetAllocation> getAssetAllocations() {
        return assetAllocations;
    }

    /**
     * Asset Allocations
     * (Required)
     * 
     */
    public void setAssetAllocations(List<AssetAllocation> assetAllocations) {
        this.assetAllocations = assetAllocations;
    }

    public enum ModelType {

        QUALIFIED("QUALIFIED"),
        TAXABLE("TAXABLE");
        private final String value;
        private final static Map<String, PortfolioModelWeb.ModelType> CONSTANTS = new HashMap<String, PortfolioModelWeb.ModelType>();

        static {
            for (PortfolioModelWeb.ModelType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ModelType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static PortfolioModelWeb.ModelType fromValue( String value) {
            PortfolioModelWeb.ModelType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum RebalanceFrequency {

        MONTHLY("MONTHLY"),
        QUARTERLY("QUARTERLY"),
        SEMI_ANNUAL("SEMI_ANNUAL"),
        ANNUAL("ANNUAL");
        private final String value;
        private final static Map<String, PortfolioModelWeb.RebalanceFrequency> CONSTANTS = new HashMap<String, PortfolioModelWeb.RebalanceFrequency>();

        static {
            for (PortfolioModelWeb.RebalanceFrequency c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private RebalanceFrequency(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static PortfolioModelWeb.RebalanceFrequency fromValue( String value) {
            PortfolioModelWeb.RebalanceFrequency constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
