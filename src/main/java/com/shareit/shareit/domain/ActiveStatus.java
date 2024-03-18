package com.shareit.shareit.domain;

import lombok.Getter;

@Getter
public enum ActiveStatus {

    ACTIVE("ACTIVE"), PENDING("PENDING"),DELETED("DELETED");

    private final String activeStatus;

    ActiveStatus(String activeStatus){
        this.activeStatus = activeStatus;
    }
}
