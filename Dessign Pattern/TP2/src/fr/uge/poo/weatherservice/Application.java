package fr.uge.poo.weatherservice;

import java.util.Optional;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceNTS;
import com.evilcorp.weatherservice.WeatherServiceTS;
import com.evilcorp.weatherservice.WeatherServiceTSFail;

public class Application {

  public void start() throws InterruptedException {

	System.out.println("Warming  up");
	Thread.sleep(5_000);
	System.out.println("Starting");

	Thread.ofPlatform().start(() -> {
	  for (;;) {
		// WeatherService service = WeatherServiceTS.getInstance(); // q1
		// WeatherService service = WeatherServiceNTS.getInstance(); // q2

		Optional<WeatherServiceTSFail> service = WeatherServiceTSFail.getInstance(); // q3
		try {
		  Thread.sleep(1_000);
		} catch (InterruptedException e) {
		  throw new AssertionError();
		}
		if (!service.isEmpty()) {
		  System.out.println("Paris : " + service.get().query("Paris"));
		}
	  }
	});

	Thread.ofPlatform().start(() -> {
	  for (;;) {
		// WeatherService service = WeatherServiceTS.getInstance(); // q1
		// WeatherService service = WeatherServiceNTS.getInstance(); // q2
		Optional<WeatherServiceTSFail> service = WeatherServiceTSFail.getInstance(); // q3
		try {
		  Thread.sleep(1_000);
		} catch (InterruptedException e) {
		  throw new AssertionError();
		}
		if (!service.isEmpty()) {
		  System.out.println("Madrid : " + service.get().query("Madrid"));
		}
	  }
	});
  }

  public static void main(String[] args) throws InterruptedException {
	var application = new Application();
	application.start();
  }
}