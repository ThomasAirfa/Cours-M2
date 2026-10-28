package fr.uge.jee.onlineshop;

public class ReturnInsurance implements Insurance {
  private final boolean membersOnly;

  public ReturnInsurance(boolean membersOnly) {
    this.membersOnly = membersOnly;
  }

  @Override
  public String description() {
    return membersOnly ? "Return insurance only for members" : "Return insurance";
  }
}