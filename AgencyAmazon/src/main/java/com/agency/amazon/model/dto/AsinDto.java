package com.agency.amazon.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AsinDto {
	@NotNull
	private List<String> asins;

	public List<String> getAsins() {
		return asins;
	}

	public void setAsins(List<String> asins) {
		this.asins = asins;
	}
}
