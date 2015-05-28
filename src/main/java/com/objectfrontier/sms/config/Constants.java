package com.objectfrontier.sms.config;

public enum Constants {

	TEMP("TEMP");

	private final String constants;

	Constants(String constants) {
		this.constants = constants;
	}
	
	public String toString() {
		return this.constants;
	}
}
