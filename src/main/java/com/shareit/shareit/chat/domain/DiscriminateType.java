package com.shareit.shareit.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscriminateType {

	MESSAGE("MESSAGE"), PURCHASE("PURCHASE");

	private final String discriminateType;
}
