package com.shareit.shareit.purchase.domain.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PurchaseStatus {
	PROCESS("PROCESS"), SUCCEED("SUCCEED");

	private final String status;

}
