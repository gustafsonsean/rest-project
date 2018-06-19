
package com.eco.portfoliomanagement.model.web;

import java.util.HashMap;
import java.util.Map;

public class AssetAllocation {

    /**
     * The asset symbol
     * (Required)
     * 
     */
    private String symbol;
    /**
     * The percentage of the model to allocate for the symbol
     * (Required)
     * 
     */
    private Double percentage;


    /**
     * The asset symbol
     * (Required)
     * 
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * The asset symbol
     * (Required)
     * 
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * The percentage of the model to allocate for the symbol
     * (Required)
     * 
     */
    public Double getPercentage() {
        return percentage;
    }

    /**
     * The percentage of the model to allocate for the symbol
     * (Required)
     * 
     */
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }


}
