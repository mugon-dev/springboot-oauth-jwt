package com.conny.oauthjwt.security.jwt.matcher;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class FilterSkipMatcher implements RequestMatcher {
	// 필터가 스킵할 URL 패턴 처리
	private final OrRequestMatcher skipMatcher;
	// 필터가 처리할 URL 패턴 처리
	private final OrRequestMatcher processingMatcher;

	/**
	 * 무시할 경로와 처리할 경로를 인자로 받아 RequestMatcher 를 생성하는 생성자
	 * @param skipPaths 필터가 스킵할 경로
	 * @param processingPaths 필터가 처리할 경로
	 */
	public FilterSkipMatcher(List<String> skipPaths, List<String> processingPaths) {
		this.skipMatcher = new OrRequestMatcher(
			skipPaths.stream()
				.map(AntPathRequestMatcher::new)
				.collect(Collectors.toList())
		);
		this.processingMatcher = new OrRequestMatcher(
			processingPaths.stream()
				.map(AntPathRequestMatcher::new)
				.collect(Collectors.toList())
		);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return !skipMatcher.matches(request) && processingMatcher.matches(request);
	}
}
