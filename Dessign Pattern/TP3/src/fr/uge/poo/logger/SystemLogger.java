package fr.uge.poo.logger;

import java.util.Objects;

public class SystemLogger implements Logger {
  private static final SystemLogger SYSTEM_LOGGER_INSTANCE = new SystemLogger();

  private SystemLogger() {

  }

  @Override
  public void log(Logger.Level level, String message) {
	Objects.requireNonNull(message);
	Objects.requireNonNull(level);
	System.err.println(level + " " + message);
  }

  public static SystemLogger getInstance() {
	return SYSTEM_LOGGER_INSTANCE;
  }
}