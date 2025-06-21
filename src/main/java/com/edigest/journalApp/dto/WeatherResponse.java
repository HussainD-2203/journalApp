package com.edigest.journalApp.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse{
    
    public Current current;
  
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
	public class Current{
	  public String observation_time;
	  public int temperature;
	  public ArrayList<String> weather_descriptions;
	  public int feelslike;
	  public String is_day;
  	}

}