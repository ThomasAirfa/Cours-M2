package fr.uge.duck;

public interface Duck {
   void quack();

  default void quackManyTimes(int n) {
	for (var i = 0; i < n; i++) {
	  quack();
	}
  }
}
