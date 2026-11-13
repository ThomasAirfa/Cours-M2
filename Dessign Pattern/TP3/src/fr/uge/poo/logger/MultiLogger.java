package fr.uge.poo.logger;

import java.util.List;
import java.util.Objects;

public final class MultiLogger implements Logger {
  private final List<Logger> loggers;

  public MultiLogger(Logger... loggers) {
	Objects.requireNonNull(loggers);
	this.loggers = List.of(loggers);
  }

  @Override
  public void log(Level level, String message) {
	Objects.requireNonNull(level);
	Objects.requireNonNull(message);
	for (var logger : loggers) {
	  logger.log(level, message);
	}
  }
}