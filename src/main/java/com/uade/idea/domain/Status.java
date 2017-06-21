package com.uade.idea.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	@JsonProperty("Initial")
	Initial,
	@JsonProperty("PreSelected")
	PreSelected,
	@JsonProperty("Rejected")
	Rejected,
	@JsonProperty("FinalStage")
	FinalStage
	
}
