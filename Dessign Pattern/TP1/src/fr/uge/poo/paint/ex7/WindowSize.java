package fr.uge.poo.paint.ex7;

public record WindowSize(int width, int height) {
  public static final WindowSize MIN_WINDOW_SIZE = new WindowSize(500, 500);

  public WindowSize max(WindowSize other) {
	return new WindowSize(Math.max(width, other.width()), Math.max(height, other.height()));
  }
}