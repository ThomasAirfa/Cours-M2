package fr.uge.duck;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggedDuck implements Duck {
  private static final Logger LOGGER = Logger.getLogger	(LoggedDuck.class.getClass().getName());
  
  private final Duck duck;
  
  public LoggedDuck(Duck duck) {
	this.duck = Objects.requireNonNull(duck);	
  }

  @Override
  public void quack() {
	LOGGER.log(Level.INFO, "call to quack");
	duck.quack();
  }
}
