package com.akcreations.restapiexample.user;

import java.time.LocalDateTime;

public class ErrorDetails {
	public ErrorDetails(LocalDateTime timestamp, String message, String description) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
	public LocalDateTime timestamp;
	public String message;
	public String description;
	
}
