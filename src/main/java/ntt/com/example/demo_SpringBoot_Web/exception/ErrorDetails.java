package ntt.com.example.demo_SpringBoot_Web.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDetails {
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
//	 @DateTimeFormat(pattern = "dd-MM-yyyy")
//	 private Date  timestamp; 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
	private String message;
	private Object details;

	public ErrorDetails(LocalDateTime  timestamp, String message, Object details) {
	    super();
	    this.timestamp = timestamp;
	    this.message = message;
	    this.details = details;
	}

	public LocalDateTime  getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime  timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}
}
