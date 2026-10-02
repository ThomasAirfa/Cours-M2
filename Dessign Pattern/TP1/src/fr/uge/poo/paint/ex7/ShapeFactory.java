package fr.uge.poo.paint.ex7;

public class ShapeFactory {
  private ShapeFactory() {
  }

  static Shape create(String command, int[] args) {
	return switch (command) {
	case "line" -> new Line(args[0], args[1], args[2], args[3]);
	case "rectangle" -> new Rectangle(args[0], args[1], args[2], args[3]);
	case "ellipse" -> new Ellipse(args[0], args[1], args[2], args[3]);
	default -> throw new IllegalArgumentException("unknown shape : " + command);
	};
  }
}
