package fr.uge.poo.paint.ex7;

sealed interface RectangularShape extends Shape permits Rectangle, Ellipse {
  int x();

  int y();

  int width();

  int height();

  public void draw(Graphics graphics);

  default Point center() {
	return new Point(x() + width() / 2, y() + height() / 2);
  }

  default WindowSize requiredWindowSize() {
	return new WindowSize(x() + width(), y() + height());
  }
}