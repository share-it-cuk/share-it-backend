package com.shareit.shareit.post.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostInfoWithPaging implements PostResponse{

	private boolean hasNext;
	private LocalDateTime cursorTime;
	private Long cursorId;
	private List<PostInfoForList> postInfos;

}
