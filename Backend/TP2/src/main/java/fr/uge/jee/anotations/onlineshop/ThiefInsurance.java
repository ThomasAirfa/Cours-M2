package fr.uge.jee.anotations.onlineshop;

public class ThiefInsurance implements Insurance {
  @Override
  public String description() {
    return "Thief insurance";
  }
}