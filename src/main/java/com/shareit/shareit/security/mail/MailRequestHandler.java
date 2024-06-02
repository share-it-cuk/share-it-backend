package com.shareit.shareit.security.mail;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shareit.shareit.security.mail.dto.ReqMapValue;

@Component
public class MailRequestHandler {
	private Map<String, ReqMapValue> mailRequest = new ConcurrentHashMap<>();

	public void addRequest(String uuid, String code) {
		ReqMapValue value = new ReqMapValue(code, LocalDateTime.now());
		if (mailRequest.containsKey(uuid)) {
			mailRequest.replace(uuid, value);
		}
		mailRequest.put(uuid, value);

		System.out.println("[mailreq_add] - uuid =" + uuid + " code=" + mailRequest.get(uuid).getCode());
	}

	public Boolean checkRequest(String uuid, String code) {
		System.out.println("[mailreq_check] - uuid=" + uuid + " user_code=" + mailRequest.get(uuid).getCode());
		if (mailRequest.get(uuid).getCode().equals(code)) {
			System.out.println("[mailreq_check] - success");
			return true;
		}

		System.out.println("[mailreq_check] - success");
		return false;
	}

	public void removeRequest(String uuid) {
		mailRequest.remove(uuid);
	}

	/**
	 * 10분이 지난 request를 10분마다 검사해서 삭제
	 */
	@Scheduled(fixedRate = 600000) // 10분마다 실행
	public void removeExpiredRequests() {
		LocalDateTime now = LocalDateTime.now();
		mailRequest.entrySet().removeIf(entry -> {
			LocalDateTime putTime = entry.getValue().getPutTime();
			long minutesSinceRequest = ChronoUnit.MINUTES.between(putTime, now);
			return minutesSinceRequest >= 10; // 10분 이상 경과한 요청 삭제
		});
	}
}
