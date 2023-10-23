package de.quandoo.recruitment.registry.model;

public enum CuisinesEnum {

  FRENCH("french"), ITALIAN("italian"), GERMAN("german");

  private final String value;

  CuisinesEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
