package com.shareit.shareit.chat.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shareit.shareit.chat.domain.DiscriminateType;
import com.shareit.shareit.purchase.domain.entity.Purchase;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "message")
public class PurchaseMessage {

	private Long roomId;
	private String senderId;
	@Builder.Default
	private final DiscriminateType discriminateType = DiscriminateType.PURCHASE;

	private Long purchaseId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	private LocalDateTime sendTime;

	public PurchaseMessage fromEntity(Purchase purchase) {
		return PurchaseMessage.builder()
			.purchaseId(purchase.getId())
			.startDate(purchase.getStartDate())
			.endDate(purchase.getEndDate())
			.build();
	}

}
