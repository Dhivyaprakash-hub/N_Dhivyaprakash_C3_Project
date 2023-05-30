import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setupRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        // Set the current time within the opening and closing time
        LocalTime currentTime = LocalTime.parse("12:00:00");
        assertTrue(restaurant.isRestaurantOpen(currentTime));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        // Set the current time outside the opening and closing time
        LocalTime currentTime = LocalTime.parse("23:00:00");
        assertFalse(restaurant.isRestaurantOpen(currentTime));
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }


    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

}
