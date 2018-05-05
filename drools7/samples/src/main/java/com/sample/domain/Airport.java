package com.sample.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Airport implements Serializable {

    private static final long serialVersionUID = 4697074365620662652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer airportid;
    
    private String airportcode;
    
    private String airportname;
    
    private String city;
    
    private String country;
    
    private BigDecimal latitude;
    
    private BigDecimal longitude;
    
    @Column(name = "state_prov")
    private String stateProv;
    
    @OneToMany(mappedBy = "airport1")
    private Set<Route> routes1;
    
    @OneToMany(mappedBy = "airport2")
    private Set<Route> routes2;
    
    public Airport() {
        
    }

    public Integer getAirportid() {
        return airportid;
    }

    public void setAirportid(Integer airportid) {
        this.airportid = airportid;
    }

    public String getAirportcode() {
        return airportcode;
    }

    public void setAirportcode(String airportcode) {
        this.airportcode = airportcode;
    }

    public String getAirportname() {
        return airportname;
    }

    public void setAirportname(String airportname) {
        this.airportname = airportname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getStateProv() {
        return stateProv;
    }

    public void setStateProv(String stateProv) {
        this.stateProv = stateProv;
    }

    public Set<Route> getRoutes1() {
        return routes1;
    }

    public void setRoutes1(Set<Route> routes1) {
        this.routes1 = routes1;
    }

    public Set<Route> getRoutes2() {
        return routes2;
    }

    public void setRoutes2(Set<Route> routes2) {
        this.routes2 = routes2;
    }
    
}
