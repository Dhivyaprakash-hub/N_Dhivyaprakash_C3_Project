
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {

    RestaurantService service;
    Restaurant restaurant;

    @BeforeEach
    public void setup() throws restaurantNotFoundException {
        service = new RestaurantService();
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant = service.findRestaurantByName("Amelie's cafe");
    }
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant foundRestaurant = service.findRestaurantByName("Amelie's cafe");
        assertNotNull(foundRestaurant);
        assertEquals(restaurant, foundRestaurant);
    }

    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Amelie's cafe"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());

    }
}
