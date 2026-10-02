package fr.uge.poo.paint.ex7;

public record Ellipse(int x, int y, int width, int height) implements RectangularShape {

  @Override
  public void draw(Graphics graphics) {
	graphics.drawOval(x, y, width, height);
  }
}
