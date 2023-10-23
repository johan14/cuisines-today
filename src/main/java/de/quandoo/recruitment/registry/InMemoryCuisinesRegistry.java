package de.quandoo.recruitment.registry;

import de.quandoo.recruitment.registry.api.CuisinesRegistry;
import de.quandoo.recruitment.registry.model.Cuisine;
import de.quandoo.recruitment.registry.model.CuisinesEnum;
import de.quandoo.recruitment.registry.model.Customer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InMemoryCuisinesRegistry implements CuisinesRegistry {

  // structure for accessing and adding cuisines or customers
  private final Map<Cuisine, Set<Customer>> cuisineSetMap = new HashMap<>();
  // structure for ordering cuisine based on customer data
  private final TreeMap<Cuisine, Integer> cuisineCustomerCountMap = new TreeMap<>(
      (c1, c2) -> Integer.compare(cuisineSetMap.get(c2).size(), cuisineSetMap.get(c1).size())
  );

  @Override
  public void register(final Customer customer, final Cuisine cuisine) {
      if (Arrays.stream(CuisinesEnum.values())
          .anyMatch(cuisinesEnum -> cuisinesEnum.getValue().equals(cuisine.getName()))) {
          cuisineSetMap.computeIfAbsent(cuisine, k -> new HashSet<>()).add(customer);

          //sorting cuisines
          cuisineCustomerCountMap.remove(cuisine);
          cuisineCustomerCountMap.put(cuisine, cuisineSetMap.get(cuisine).size());

      } else {
          System.err.println(
              "Unknown cuisine, please reach johny@bookthattable.de to update the code");
      }
  }

  @Override
  public List<Customer> cuisineCustomers(final Cuisine cuisine) {
    return new ArrayList<>(cuisineSetMap.get(cuisine));
  }

  @Override
  public List<Cuisine> customerCuisines(final Customer customer) {
    List<Cuisine> resultList = new ArrayList<>();
    for (Cuisine cuisine : cuisineSetMap.keySet()) {
        if (cuisineSetMap.get(cuisine).contains(customer)) {
            resultList.add(cuisine);
        }
    }
    return resultList;
  }

  @Override
  public List<Cuisine> topCuisines(final int n) {
    return cuisineCustomerCountMap.keySet().stream().limit(n).collect(Collectors.toList());
  }
}
