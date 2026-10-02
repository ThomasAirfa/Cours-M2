package fr.uge.poo.paint.ex6;

public sealed interface Graphics permits SimpleGraphicsAdapter, CoolGraphicsAdapter {
  @FunctionalInterface
  interface MouseCallback {
	void mouseClicked(int x, int y);
  }
  
  public enum GraphicsColor {
	WHITE, BLACK, ORANGE
  }
  
  void waitForMouseEvents(MouseCallback callback);

  void clear(GraphicsColor color);

  void setColor(GraphicsColor color);
  
  void drawLine(int x1, int y1, int x2, int y2);


  void drawOval(int x, int y, int width, int height);
  
  default void render() {
	
  }
  
  default void drawRect(int x, int y, int width, int height) {
	drawLine(x, y, x + width, y);
	drawLine(x + width, y, x + width, y + height);
	drawLine(x + width, y + height, x, y + height);
	drawLine(x, y + height, x, y);
  }
}
