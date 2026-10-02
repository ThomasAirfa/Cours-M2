package fr.uge.poo.paint.ex7;

public sealed interface Shape permits Line, RectangularShape {
  
  public void draw(Graphics graphics);

  public Point center();
}
