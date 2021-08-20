import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        CitiesService service = new CitiesService();
        addTable();
        addPopulation();
        service.printCitiesWithPopulation();
    }

    public static void addTable() {
        String urlDb = "jdbc:sqlite:homeworks.bd";
        try (Connection connection = DriverManager.getConnection(urlDb);
             Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS city_details(" +
                    "city_id TEXT," +
                    "population INTEGER);";
            statement.execute(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void addPopulation() throws IOException {
        int count = 3;
        CitiesService service = new CitiesService();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите имя города");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String city = reader.readLine();
            int population = Integer.parseInt(reader.readLine());
            service.addPopulation(city, population);
        }
    }

    public static void addCity() {
        CitiesService service = new CitiesService();
        int count = 3;
        for (int i = 0; i < count; i++) {
            System.out.println("Введите  город");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                City city = new City(UUID.randomUUID().toString(), reader.readLine());
                service.addCity(city);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
