package fr.uge.poo.logger.q0;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class PathLogger implements Closeable {
  private final BufferedWriter writer;
  
  public PathLogger(Path path) {
	try {
	  this.writer = Files.newBufferedWriter(Objects.requireNonNull(path), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	} catch (IOException e) {
	  throw new UncheckedIOException(e);
	}
  }
  
  public void log(String message) {
	
  }

  @Override
  public void close() throws IOException {
	writer.close();
  }

}
