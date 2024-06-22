package com.devdoc.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ResponseDTO<T>: 응답 데이터를 구조화하여 클라이언트에 전송하기 위한 공통 응답 DTO

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	private String error; // 오류 메시지
	private List<T> data; // 데이터 리스트
}

