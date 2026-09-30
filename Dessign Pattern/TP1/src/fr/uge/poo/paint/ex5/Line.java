package fr.uge.poo.paint.ex5;

public record Line(int x1, int y1, int x2, int y2) implements Shape {

  @Override
  public void draw(Graphics graphics) {
	graphics.drawLine(x1, y1, x2, y2);
  }

  @Override
  public Point center() {
	return new Point((x1 + x2) / 2, (y1 + y2) / 2);
  }
}
