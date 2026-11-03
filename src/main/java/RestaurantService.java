import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private List<Restaurant> restaurants;

    public RestaurantService() {
        restaurants = new ArrayList<>();
    }

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException("Restaurant not found: " + restaurantName);
    }

    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Restaurant name is required");
        if (findRestaurantOrNull(name) != null) throw new IllegalArgumentException("Restaurant already exists: " + name);
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return java.util.Collections.unmodifiableList(new ArrayList<>(restaurants));
    }

    private Restaurant findRestaurantOrNull(String name) {
        return restaurants.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }
}
