package fr.uge.jee.printers;

public class SlowContructionMessagePrinter implements MessagePrinter {

  public SlowContructionMessagePrinter() {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
  }
  @Override
  public void printMessage() {
    System.out.println("I'm slow but hello world!");
  }
}
