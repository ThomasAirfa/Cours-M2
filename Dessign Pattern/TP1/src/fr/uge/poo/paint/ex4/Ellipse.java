package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

public record Ellipse(int x, int y, int width, int height) implements RectangularShape {

  @Override
  public void draw(Graphics2D graphics) {
	graphics.drawOval(x, y, width, height);
  }
}
