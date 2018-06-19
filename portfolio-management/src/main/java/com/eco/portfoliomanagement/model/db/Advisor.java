package com.eco.portfoliomanagement.model.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class Advisor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private String advisorId;
    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId( String advisorId ) {
        this.advisorId = advisorId;
    }
}
