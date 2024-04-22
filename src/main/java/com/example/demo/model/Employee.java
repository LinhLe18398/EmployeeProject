package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "khong duoc de trong")
    private String name;
    @NotEmpty(message = "khong duoc de trong")
    private String email;
    @Min(value = 18, message = "tuoi phai tren 18")
    private int age;

    @ManyToOne
    private Office Office;


}
