package de.quandoo.recruitment.registry;

import de.quandoo.recruitment.registry.api.CuisinesRegistry;
import de.quandoo.recruitment.registry.model.Cuisine;
import de.quandoo.recruitment.registry.model.Customer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InMemoryCuisinesRegistry implements CuisinesRegistry {

  private static final Logger LOGGER = Logger.getLogger(InMemoryCuisinesRegistry.class.getName());

  // Structure for accessing and adding cuisines or customers
  private static final Map<Cuisine, Set<Customer>> cuisineSetMap = new HashMap<>();

  // Structure for ordering cuisine based on customers number
  private static final Set<Cuisine> sortedSet = new TreeSet<>(
      (c1, c2) -> {
        int sizeComparison = Integer.compare(cuisineSetMap.get(c2).size(),
            cuisineSetMap.get(c1).size());
        return sizeComparison != 0 ? sizeComparison : Integer.compare(c1.hashCode(), c2.hashCode());
      });

  public Map<Cuisine, Set<Customer>> getCuisineSetMap() {
    return cuisineSetMap;
  }

  public Set<Cuisine> getSortedSet() {
    return sortedSet;
  }

  @Override
  public void register(final Customer customer, final Cuisine cuisine) {

    if (customer == null || cuisine == null) {
      LOGGER.warning("Customer or cuisine cannot be null");
      return;
    }
    Set<Customer> customerSet = cuisineSetMap.computeIfAbsent(cuisine, k -> new HashSet<>());
    LOGGER.info(String.format("Added customer %s to cuisine %s", customer.getUuid(), cuisine.getName().getValue()));

    if (customerSet.add(customer)) {
      // Customer was added, update counts
      sortedSet.remove(cuisine);
      sortedSet.add(cuisine);
      LOGGER.info(String.format("Added cuisine %s in sorted order", cuisine.getName().getValue()));
    }
  }

  @Override
  public List<Customer> cuisineCustomers(final Cuisine cuisine) {
    if (cuisine == null || cuisineSetMap.get(cuisine) == null) {
      LOGGER.warning("Customer or cuisine cannot be null");
      return Collections.emptyList();
    } else {
      return new ArrayList<>(cuisineSetMap.get(cuisine));
    }
  }

  @Override
  public List<Cuisine> customerCuisines(final Customer customer) {
    if (customer == null) {
      LOGGER.warning("Customer cannot be null");
      return Collections.emptyList();
    } else {
      return cuisineSetMap.keySet().stream()
          .filter(cuisine -> cuisineSetMap.get(cuisine).contains(customer))
          .collect(Collectors.toList());
    }
  }

  @Override
  public List<Cuisine> topCuisines(final int n) {
    return sortedSet.stream().limit(n).collect(Collectors.toList());
  }
}
