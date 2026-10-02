package fr.uge.poo.paint.ex6;

sealed interface RectangularShape extends Shape permits Rectangle, Ellipse {
  int x();

  int y();

  int width();

  int height();

  public void draw(Graphics graphics);

  default Point center() {
	return new Point(x() + width() / 2, y() + height() / 2);
  }
}