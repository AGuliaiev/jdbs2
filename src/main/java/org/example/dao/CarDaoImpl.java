package org.example.dao;

import org.example.ConnectionUtil;
import org.example.model.Car;

import java.sql.*;
import java.util.Optional;

public class CarDaoImpl implements CarDao{
    @Override
    public Car save(Car car) {
        String sql = "INSERT INTO car (model, year) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, car.getModel());
            statement.setInt(2, car.getYear());

            int affectedRose = statement.executeUpdate();
            if (affectedRose < 1) {
                throw new RuntimeException("Expected to insert at leas one row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                Long id = generatedKeys.getObject(1, Long.class);
                car.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can not add new car: " + car, e);
        }
        return car;
    }

    @Override
    public Car get(Long id) {
        String sql = "SELECT * FROM car WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){

                String model = resultSet.getString("model");
                Integer year = resultSet.getObject("year", Integer.class);
                Car car = new Car();
                car.setId(id);
                car.setModel(model);
                car.setYear(year);
                return car;
            }


        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to the DB", e);
        }
        return null;
    }

    @Override
    public Optional<Car> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Car update(Car car) {
        return null;
    }

    @Override
    public boolean delete(Car car) {
        return false;
    }
}
