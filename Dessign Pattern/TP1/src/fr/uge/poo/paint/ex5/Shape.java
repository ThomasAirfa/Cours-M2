package fr.uge.poo.paint.ex5;

public sealed interface Shape permits Line, RectangularShape {
  
  public void draw(Graphics graphics);

  public Point center();
}
