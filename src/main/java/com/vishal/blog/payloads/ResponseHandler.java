package com.vishal.blog.payloads;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.is2xxSuccessful());
		map.put("data", responseObject == null ? Collections.EMPTY_LIST : responseObject);
		
		return new ResponseEntity<Object>(map, status);
	}
}
