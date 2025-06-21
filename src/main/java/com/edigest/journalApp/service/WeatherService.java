package com.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edigest.journalApp.dto.WeatherResponse;

@Service
public class WeatherService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String apikey = "ce04427c81b6d50e109e2afea4709ce0";
	private static final String api = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

	public ResponseEntity<Object> getweather(String city) {
		String finalAPI = api.replace("API_KEY",apikey).replace("CITY",city);
		ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI,HttpMethod.GET,null,WeatherResponse.class);
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}

}
