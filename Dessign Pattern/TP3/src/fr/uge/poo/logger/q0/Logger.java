package fr.uge.poo.logger.q0;

public interface Logger {
  public enum Level {
	ERROR, WARNING, INFO
  }
  
  public void log(Level level, String message);
}
