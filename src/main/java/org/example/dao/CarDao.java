package org.example.dao;

import org.example.model.Car;

import java.util.Optional;

public interface CarDao {
    Car save(Car car);
    Car get(Long id);
    Optional<Car> findById(Long id);
    Car update(Car car);
    boolean delete(Car car);
}
