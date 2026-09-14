package io.github.halliwell29.harbour.site;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double longitude;
    private double latitude;
    private Instant createdAt;

    protected Site(){

    }

    public Site(String name, double latitude, double longitude,  String description){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createdAt = Instant.now();
        this.description = description;
    }

    public String getName(){
        return this.name;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public Instant getCreatedAt(){
        return this.createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

}
