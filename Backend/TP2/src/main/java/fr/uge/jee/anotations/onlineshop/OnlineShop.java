package fr.uge.jee.anotations.onlineshop;

import java.util.*;

public class OnlineShop {
  private final String name;
  private final Set<Delivery> deliveryOptions;
  private final Set<Insurance> insurances;

  public OnlineShop(String name, Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
    this.name = Objects.requireNonNull(name);
    this.deliveryOptions = Set.copyOf(deliveryOptions);
    this.insurances = Set.copyOf(insurances);
  }

  public void printDescription() {
    System.out.println(name);
    System.out.println("Delivery options");
    deliveryOptions.forEach(opt -> System.out.println("\t" + opt.description()));
    System.out.println("Insurance options");
    insurances.forEach(insurance -> System.out.println("\t" + insurance.description()));
  }
}