package com.evilcorp.weatherservice;

/* Assume that this class is not thread-safe */
public class WeatherServiceNTS implements WeatherService {
  private static final WeatherServiceNTS WEATHER_SERVICE_NTS_INSTANCE = new WeatherServiceNTS();
  private static final Object lock = new Object();

  private WeatherServiceNTS() {
	System.out.println("Creating a connection to WeatherServiceNTS");
  }

  public int query(String city) {
	synchronized (lock) {
	  return city.hashCode() % 30;
	}
  }

  public static WeatherServiceNTS getInstance() {
	synchronized (lock) {
	  return WEATHER_SERVICE_NTS_INSTANCE;
	}
  }
}
