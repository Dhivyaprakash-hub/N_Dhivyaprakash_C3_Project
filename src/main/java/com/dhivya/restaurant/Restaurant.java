package com.dhivya.restaurant;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "restaurants", uniqueConstraints = @UniqueConstraint(name = "uk_restaurant_name", columnNames = "name"))
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 120) private String name;
    @Column(nullable = false, length = 180) private String location;
    @Column(name = "opening_time", nullable = false) private LocalTime openingTime;
    @Column(name = "closing_time", nullable = false) private LocalTime closingTime;
    @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(name = "menu_items", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<MenuItem> menu = new ArrayList<>();
    protected Restaurant() {}
    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime, List<MenuItem> menu) {
        this.name=name; this.location=location; this.openingTime=openingTime; this.closingTime=closingTime; this.menu=new ArrayList<>(menu);
    }
    public Long getId(){return id;} public String getName(){return name;} public String getLocation(){return location;}
    public LocalTime getOpeningTime(){return openingTime;} public LocalTime getClosingTime(){return closingTime;}
    public List<MenuItem> getMenu(){return List.copyOf(menu);}
    @Embeddable public static class MenuItem {
        @Column(name="item_name", nullable=false, length=100) private String name;
        @Column(nullable=false, precision=10, scale=2) private BigDecimal price;
        protected MenuItem() {}
        public MenuItem(String name, BigDecimal price){this.name=name;this.price=price;}
        public String getName(){return name;} public BigDecimal getPrice(){return price;}
    }
}
