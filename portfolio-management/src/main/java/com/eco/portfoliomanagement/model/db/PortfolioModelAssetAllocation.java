package com.eco.portfoliomanagement.model.db;


import javax.persistence.*;

@Entity
public class PortfolioModelAssetAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String symbol;
    private double percentage;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol( String symbol ) {
        this.symbol = symbol;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage( double percentage ) {
        this.percentage = percentage;
    }
}
