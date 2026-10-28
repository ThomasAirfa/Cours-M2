package com.evilcorp.weatherservice;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/* This class is thread-safe */

/* Comment aurez dû être proprement codée la librairie WeatherServiceFail ?
 * 
 * Pas d'exception dans le constrcteur qui ne dépend pas des paramètres de celui-ci.
 * */
public class WeatherServiceTSFail implements WeatherService {
  private static WeatherServiceTSFail WEATHER_SERVICE_TS_FAIL;
  private static final Object lock = new Object();

  private WeatherServiceTSFail() {
	if (ThreadLocalRandom.current().nextBoolean()) {
	  throw new IllegalStateException("Connection failed please try again later");
	}
	System.out.println("Creating a connection to WeatherServiceTSFail");
  }

  public int query(String city) {
	return city.hashCode() % 30;
  }

  public static Optional<WeatherServiceTSFail> getInstance() {
	synchronized (lock) {
	  if (WEATHER_SERVICE_TS_FAIL != null) {
		return Optional.of(WEATHER_SERVICE_TS_FAIL);
	  }
	  try {
		WEATHER_SERVICE_TS_FAIL = new WeatherServiceTSFail();
		return Optional.of(WEATHER_SERVICE_TS_FAIL);
	  } catch (IllegalStateException e) {
		System.err.println(e.getMessage());
		return Optional.empty();
	  }
	}
  }
}
