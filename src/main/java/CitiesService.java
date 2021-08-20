import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitiesService {
    static String url = "jdbc:sqlite:homeworks.bd";

    public void addCity(City city) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String query = "INSERT INTO cities VALUES('" + city.getId() + "','" + city.getName() + "');";
        statement.execute(query);
        statement.close();
        connection.close();
    }

    public void addPopulation(String nameOfCity, int population) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String selectId = "Select id FROM cities WHERE name LIKE '" + nameOfCity + "';";
            ResultSet resultSet = statement.executeQuery(selectId);
            String idCity = resultSet.getString("id");
            String query = "INSERT INTO city_details VALUES('" + idCity + "','" + population + "');";
            statement.execute(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void printCitiesToConsole() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM cities;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            System.out.println("ID: " + id + " name: " + name);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }


    public List<City> getCities() {
        List<City> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();) {
            String query = "SELECT * FROM cities;";
            try (ResultSet resultSet = statement.executeQuery(query);) {
                while (resultSet.next()) {
                    list.add(new City(resultSet.getString("id"), resultSet.getString("name")));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public void printCitiesWithPopulation() {
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
        ) {
            String query = "Select cities.name, city_details.population FROM cities JOIN city_details WHERE cities.id = city_details.city_id";
            try (
                    ResultSet resultSet = statement.executeQuery(query);
            ) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int population = resultSet.getInt("population");
                    System.out.println(name + " " + population);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


}
