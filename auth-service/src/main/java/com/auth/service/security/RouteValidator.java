package com.auth.service.security;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.auth.service.dto.RequestDTO;

@Component
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {

	private List<RequestDTO> paths;

	public List<RequestDTO> getPaths() {
		return paths;
	}

	public void setPaths(List<RequestDTO> paths) {
		this.paths = paths;
	}

	public boolean isAdminPath(RequestDTO requestDTO) {
		return paths.stream().anyMatch(p -> 
		       Pattern.matches(p.getUri(), requestDTO.getUri()) && p.getMethod().equals(requestDTO.getMethod()));
	}

}
