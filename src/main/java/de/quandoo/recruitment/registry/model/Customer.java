package de.quandoo.recruitment.registry.model;

public class Customer {

  private final String uuid;

  public Customer(final String uuid) {
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }
}
