package fr.uge.poo.logger;

import java.util.Objects;
import java.util.function.Predicate;

public class FilteredLogger implements Logger {
  private final Logger logger;
  private final Predicate<Level> filter;

  public FilteredLogger(Logger logger, Predicate<Level> filter) {
	this.logger = Objects.requireNonNull(logger);
	this.filter = Objects.requireNonNull(filter);
  }

  @Override
  public void log(Level level, String message) {
	Objects.requireNonNull(level);
	Objects.requireNonNull(message);
	if (filter.test(level)) {
	  logger.log(level, message);
	}
  }

  public static FilteredLogger atLeast(Logger delegate, Level threshold) {
	Objects.requireNonNull(threshold);
	return new FilteredLogger(delegate, level -> level.compareTo(threshold) >= 0);
  }

  public static FilteredLogger atMost(Logger delegate, Level threshold) {
	Objects.requireNonNull(threshold);
	return new FilteredLogger(delegate, level -> level.compareTo(threshold) <= 0);
  }
}
