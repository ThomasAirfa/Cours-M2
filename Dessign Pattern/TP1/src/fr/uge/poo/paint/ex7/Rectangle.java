package fr.uge.poo.paint.ex7;

public record Rectangle(int x, int y, int width, int height) implements RectangularShape {

  @Override
  public void draw(Graphics graphics) {
	graphics.drawRect(x, y, width, height);
  }
}
