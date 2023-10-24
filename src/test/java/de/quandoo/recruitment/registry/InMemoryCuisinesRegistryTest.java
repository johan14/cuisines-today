package de.quandoo.recruitment.registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.quandoo.recruitment.registry.model.Cuisine;
import de.quandoo.recruitment.registry.model.CuisinesEnum;
import de.quandoo.recruitment.registry.model.Customer;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class InMemoryCuisinesRegistryTest {

  final InMemoryCuisinesRegistry cuisinesRegistry = new InMemoryCuisinesRegistry();

  @Before
  public void cleanStateInitialization() {
    cuisinesRegistry.getCuisineSetMap().clear();
    cuisinesRegistry.getSortedSet().clear();
  }

  @Test
  public void givenValidCuisineAndValidCustomer_whenRegisteringCustomerToCuisine_thenAddSuccessfully() {

    Customer customer = new Customer(UUID.randomUUID());
    Cuisine cuisine = new Cuisine(CuisinesEnum.FRENCH);

    cuisinesRegistry.register(customer, cuisine);

    assertTrue(cuisinesRegistry.getCuisineSetMap().containsKey(cuisine));
    assertEquals(1, cuisinesRegistry.getCuisineSetMap().get(cuisine).size());
  }

  @Test
  public void givenInValidCuisineAndInValidCustomer_whenRegisteringCustomerToCuisine_thenHandleWarning() {

    cuisinesRegistry.register(null, null);

    assertEquals(0, cuisinesRegistry.getCuisineSetMap().size());
  }

  @Test
  public void givenRegisteredCuisineAndCustomers_whenRetrievingCustomersOfOneCuisine_thenReturnListOfCustomers() {

    Customer customer1 = new Customer(UUID.randomUUID());
    Customer customer2 = new Customer(UUID.randomUUID());
    Cuisine cuisine = new Cuisine(CuisinesEnum.FRENCH);

    cuisinesRegistry.register(customer1, cuisine);
    cuisinesRegistry.register(customer2, cuisine);

    assertTrue(cuisinesRegistry.getCuisineSetMap().containsKey(cuisine));
    assertEquals(2, cuisinesRegistry.cuisineCustomers(cuisine).size());
    assertTrue(cuisinesRegistry.cuisineCustomers(cuisine).contains(customer1));
    assertTrue(cuisinesRegistry.cuisineCustomers(cuisine).contains(customer2));
  }

  @Test
  public void givenRegisteredCuisineAndCustomers_whenRetrievingCustomersOfInValidCuisine_thenHandleWarning() {

    Customer customer1 = new Customer(UUID.randomUUID());
    Customer customer2 = new Customer(UUID.randomUUID());
    Cuisine cuisine = new Cuisine(CuisinesEnum.FRENCH);

    cuisinesRegistry.register(customer1, cuisine);
    cuisinesRegistry.register(customer2, cuisine);

    assertEquals(0, cuisinesRegistry.cuisineCustomers(null).size());
    assertEquals(0, cuisinesRegistry.cuisineCustomers(new Cuisine(CuisinesEnum.ITALIAN)).size());
  }

  @Test
  public void givenRegisteredCuisinesAndCustomers_whenRetrievingCuisinesOfOneCustomer_thenReturnListOfCuisines() {

    Customer customer1 = new Customer(UUID.randomUUID());
    Customer customer2 = new Customer(UUID.randomUUID());
    Cuisine frenchCuisine = new Cuisine(CuisinesEnum.FRENCH);
    Cuisine germanCuisine = new Cuisine(CuisinesEnum.GERMAN);

    cuisinesRegistry.register(customer1, frenchCuisine);
    cuisinesRegistry.register(customer1, germanCuisine);
    cuisinesRegistry.register(customer2, frenchCuisine);

    assertEquals(2, cuisinesRegistry.customerCuisines(customer1).size());
    assertTrue(cuisinesRegistry.customerCuisines(customer1).contains(frenchCuisine));
    assertTrue(cuisinesRegistry.customerCuisines(customer1).contains(germanCuisine));
  }

  @Test
  public void givenRegisteredCuisinesAndCustomers_whenRetrievingCuisinesOfInvalidCustomer_thenHandleWarning() {

    Customer customer1 = new Customer(UUID.randomUUID());
    Customer customer2 = new Customer(UUID.randomUUID());
    Cuisine frenchCuisine = new Cuisine(CuisinesEnum.FRENCH);
    Cuisine germanCuisine = new Cuisine(CuisinesEnum.GERMAN);

    cuisinesRegistry.register(customer1, frenchCuisine);
    cuisinesRegistry.register(customer1, germanCuisine);
    cuisinesRegistry.register(customer2, frenchCuisine);

    assertEquals(0, cuisinesRegistry.customerCuisines(null).size());
    assertEquals(0, cuisinesRegistry.customerCuisines(new Customer()).size());
  }

  @Test
  public void givenSeveralRegisteredCustomersAndCuisines_whenRetrievingTopNCuisines_thenReturnSuccessfullyCuisineWithMostCustomers() {

    Customer customer1 = new Customer(UUID.randomUUID());
    Customer customer2 = new Customer(UUID.randomUUID());
    Cuisine frenchCuisine = new Cuisine(CuisinesEnum.FRENCH);
    Cuisine germanCuisine = new Cuisine(CuisinesEnum.GERMAN);

    cuisinesRegistry.register(customer1, frenchCuisine);
    cuisinesRegistry.register(customer1, germanCuisine);
    cuisinesRegistry.register(customer2, frenchCuisine);

    assertEquals(2, cuisinesRegistry.topCuisines(5).size());
    assertEquals(frenchCuisine, cuisinesRegistry.topCuisines(2).get(0));
    assertEquals(germanCuisine, cuisinesRegistry.topCuisines(2).get(1));
  }


}