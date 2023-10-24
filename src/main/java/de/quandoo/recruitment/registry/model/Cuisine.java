package de.quandoo.recruitment.registry.model;

public class Cuisine {

  private final CuisinesEnum name;

  public Cuisine(final CuisinesEnum name) {
    this.name = name;
  }

  public CuisinesEnum getName() {
    return name;
  }

}
