package fr.uge.poo.logger;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class PathLogger implements Logger, Closeable {
  private final BufferedWriter writer;

  public PathLogger(Path path) {
	try {
	  this.writer = Files.newBufferedWriter(Objects.requireNonNull(path), StandardCharsets.UTF_8,
	      StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	} catch (IOException e) {
	  throw new UncheckedIOException(e);
	}
  }

  @Override
  public void log(Level level, String message) {
	Objects.requireNonNull(level);
	Objects.requireNonNull(message);
	try {
	  writer.append(level + " " + message + "\n");
	  writer.flush();
	} catch (IOException e) {
	  throw new UncheckedIOException(e);
	}
  }

  @Override
  public void close() throws IOException {
	writer.close();
  }

}
