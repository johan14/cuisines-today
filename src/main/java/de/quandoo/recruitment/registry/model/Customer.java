package de.quandoo.recruitment.registry.model;

import java.util.Objects;
import java.util.UUID;

public class Customer {

  private UUID uuid;

  public Customer() {
    this(UUID.randomUUID());
  }

  public Customer(final UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return Objects.equals(uuid, customer.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
