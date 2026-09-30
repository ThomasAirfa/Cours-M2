package fr.uge.poo.paint.ex5;

import java.awt.Color;

public sealed interface Graphics permits SimpleGraphicsAdapter, CoolGraphicsAdapter {
  @FunctionalInterface
  interface MouseCallback {
	void mouseClicked(int x, int y);
  }

  void clear(Color color);

  void setColor(Color color);
  
  void drawLine(int x1, int y1, int x2, int y2);

  void drawRect(int x, int y, int width, int height);

  void drawOval(int x, int y, int width, int height);

  void waitForMouseEvents(MouseCallback callback);
  
  // Pour la 6
  // void render() pas d'argument car coolgraphics n'en n'a pas besoin
  // chainer les consumers avec andthen
}
