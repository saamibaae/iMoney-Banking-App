package com.imoney.models;

import java.util.Date;
import java.util.UUID;

public abstract class BaseEntity {

    protected String id;
    protected Date createdDate;

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
        this.createdDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
