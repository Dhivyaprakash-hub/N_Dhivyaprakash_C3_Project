package com.dhivya.restaurant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@RestController @RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurants;
    public RestaurantController(RestaurantRepository restaurants){this.restaurants=restaurants;}
    @GetMapping public List<Restaurant> list(){return restaurants.findAll();}
    @GetMapping("/{id}") public Restaurant get(@PathVariable long id){return restaurants.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Restaurant not found"));}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Restaurant create(@Valid @RequestBody CreateRestaurant request){
        if(restaurants.findByNameIgnoreCase(request.name()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"A restaurant with this name already exists");
        return restaurants.save(new Restaurant(request.name().trim(),request.location().trim(),request.openingTime(),request.closingTime(),request.menu().stream().map(i->new Restaurant.MenuItem(i.name().trim(),i.price())).toList()));
    }
    @PostMapping("/{id}/quote") public Quote quote(@PathVariable long id,@Valid @RequestBody QuoteRequest request){
        Restaurant restaurant=get(id); var menu=restaurant.getMenu(); BigDecimal total=BigDecimal.ZERO;
        for(var line:request.items()) { var item=menu.stream().filter(i->i.getName().equalsIgnoreCase(line.name())).findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Item is not on this menu: "+line.name())); total=total.add(item.getPrice().multiply(BigDecimal.valueOf(line.quantity()))); }
        return new Quote(restaurant.getId(),restaurant.getName(),total.setScale(2),request.items());
    }
    public record MenuRequest(@NotBlank @Size(max=100) String name,@NotNull @DecimalMin("0.01") @Digits(integer=8,fraction=2) BigDecimal price){}
    public record CreateRestaurant(@NotBlank @Size(max=120) String name,@NotBlank @Size(max=180) String location,@NotNull LocalTime openingTime,@NotNull LocalTime closingTime,@NotEmpty @Size(max=100) List<@Valid MenuRequest> menu){}
    public record LineItem(@NotBlank @Size(max=100) String name,@Min(1) @Max(50) int quantity){}
    public record QuoteRequest(@NotEmpty @Size(max=30) List<@Valid LineItem> items){}
    public record Quote(long restaurantId,String restaurant,BigDecimal total,List<LineItem> items){}
}
