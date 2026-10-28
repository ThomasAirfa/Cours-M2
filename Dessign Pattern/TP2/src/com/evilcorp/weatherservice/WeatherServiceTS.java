package com.evilcorp.weatherservice;

/* This class is thread-safe */
public class WeatherServiceTS implements WeatherService {
  private static final WeatherServiceTS WEATHER_SERIVCE_TS_INSTANCE = new WeatherServiceTS();

  private WeatherServiceTS() {
	System.out.println("Creating a connection to WeatherServiceTS");
  }

  public int query(String city) {
	return city.hashCode() % 30;
  }
  
  public static WeatherServiceTS getInstance() {
	return WEATHER_SERIVCE_TS_INSTANCE;
  }
}
