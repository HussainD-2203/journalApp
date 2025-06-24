package com.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edigest.journalApp.cache.AppCache;
import com.edigest.journalApp.dto.WeatherResponse;

@Service
public class WeatherService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppCache appCache;
	
	@Value("${weather.api.key}")
	private String apikey;
	//instead of hard coding we are extracting the api from database
	//private static final String api = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

	public ResponseEntity<Object> getweather(String city) {
		//Here APP_CACHE is a map and we are using get("weather_api") method to get corresponding value for key=weather_api
		String finalAPI = appCache.app_cache.get("weather_api").replace("API_KEY",apikey).replace("CITY",city);
		ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI,HttpMethod.GET,null,WeatherResponse.class);
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
     
}
