package com.shareit.shareit.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ActiveStatus {

    ACTIVE("ACTIVE"), DELETED("DELETED");

    private final String activeStatus;

    ActiveStatus(String activeStatus){
        this.activeStatus = activeStatus;
    }
}
