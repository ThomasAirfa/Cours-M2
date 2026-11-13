package fr.uge.poo.logger;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) throws IOException {
	try (var fileLogger = new PathLogger(Path.of("/tmp/logs.txt"))) {
	  var logger = new MultiLogger(SystemLogger.getInstance(), FilteredLogger.atMost(fileLogger, Logger.Level.WARNING));

	  logger.log(Logger.Level.ERROR, "erreur : console + fichier");
	  logger.log(Logger.Level.WARNING, "avertissement : console + fichier");
	  logger.log(Logger.Level.INFO, "info : console uniquement");
	}
  }
}