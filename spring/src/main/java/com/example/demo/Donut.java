package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "donuts")
public class Donut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String topping;
    Date expiration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getExpiration() {
        return expiration;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
