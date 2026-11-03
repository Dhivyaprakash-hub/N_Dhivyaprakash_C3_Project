import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class Restaurant {
    private String name;
    private String location;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<Item> menu = new ArrayList<>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen(LocalTime currentTime) {
        Objects.requireNonNull(currentTime, "currentTime");
        if (openingTime.equals(closingTime)) return true;
        if (openingTime.isBefore(closingTime)) {
            return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
        }
        return !currentTime.isBefore(openingTime) || !currentTime.isAfter(closingTime);
    }

    public List<Item> getMenu() {
        return Collections.unmodifiableList(new ArrayList<>(menu));
    }

    private Item findItemByName(String itemName) {
        for (Item item : menu) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Item name is required");
        if (price < 0) throw new IllegalArgumentException("Item price cannot be negative");
        if (findItemByName(name) != null) throw new IllegalArgumentException("Item already exists: " + name);
        Item newItem = new Item(name, price);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws itemNotFoundException {
        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }

    public int calculateTotal(List<String> selectedItems) throws itemNotFoundException {
        int total = 0;
        for (String itemName : selectedItems) {
            Item item = findItemByName(itemName);
            if (item == null)
                throw new itemNotFoundException(itemName);
            total += item.getPrice();
        }
        return total;
    }

    public void displayDetails() {
        System.out.println("Restaurant: " + name + "\n" +
                "Location: " + location + "\n" +
                "Opening time: " + openingTime + "\n" +
                "Closing time: " + closingTime + "\n" +
                "Menu:\n" + getMenu());
    }

    public int getOrderValue(String... itemNames) {
        try {
            return calculateTotal(java.util.Arrays.asList(itemNames));
        } catch (itemNotFoundException exception) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }


    public String getName() {
        return name;
    }
}
