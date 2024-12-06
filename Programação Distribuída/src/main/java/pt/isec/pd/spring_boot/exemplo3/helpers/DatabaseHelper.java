package pt.isec.pd.spring_boot.exemplo3.helpers;


import org.sqlite.SQLiteException;
import pt.isec.pd.spring_boot.exemplo3.models.Event;
import pt.isec.pd.spring_boot.exemplo3.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private final String url = "jdbc:sqlite:PD-2024-24-TP";
    private Connection connection;
    int version;
    boolean isUpdating;

    public DatabaseHelper() {
        this.version = 0;
        isUpdating = false;
        try {
            connection = DriverManager.getConnection(url);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        Statement stmt = this.connection.createStatement();
        String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS User (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                isAdmin BOOLEAN NOT NULL);
        """;
        stmt.executeUpdate(createUserTableSQL);

        String createEventTableSQL = """
            CREATE TABLE IF NOT EXISTS Event (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(255) NOT NULL,
                address VARCHAR(255) NOT NULL,
                date DATE NOT NULL,
                beginHour TIME NOT NULL,
                endHour TIME NOT NULL,
                confirmationCode int,
                codeTimestamp TIME);
        """;
        stmt.executeUpdate(createEventTableSQL);

        String createEventAttendanceTableSQL = """
            CREATE TABLE IF NOT EXISTS EventAttendance (
                eventId INT,
                userId INT,
                FOREIGN KEY (eventId) REFERENCES Event (id) ON DELETE CASCADE,
                FOREIGN KEY (userId) REFERENCES User (id) ON DELETE CASCADE,
                PRIMARY KEY (eventId, userId));
        """;

        stmt.executeUpdate(createEventAttendanceTableSQL);

        // Insert admin
        try {
            String insertAdmin = """
                    INSERT INTO User (name, password, email, isAdmin)
                    VALUES('admin', 'admin', 'admin', true);
                    """;
            stmt.executeUpdate(insertAdmin);
            System.out.println("DB created with 1 admin");
        } catch (SQLException e){
            if (e.getErrorCode() == 19) {
                // Admin is already created
            } else {
                System.out.println(e.getSQLState());
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUserFromCredentials(String email, String password) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM User WHERE email = ? AND password = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int selectedUserId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                // Create and return a User object with the retrieved data
                return new User(selectedUserId, email, password, name, isAdmin);
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createEvent(Event event) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Event (name, address, date, beginHour, endHour) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getAddress());
            preparedStatement.setDate(3, event.getDate());
            preparedStatement.setTime(4, event.getStartTime());
            preparedStatement.setTime(5, event.getEndTime());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Event> getEvents(){
        List<Event> events = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement())
        {
            String query = "SELECT * FROM Event";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date");
                Time startTime = resultSet.getTime("beginHour");
                Time endTime = resultSet.getTime("endHour");
                int code = resultSet.getInt("confirmationCode");
                Timestamp codeTimestamp = resultSet.getTimestamp("codeTimestamp");

                Event event = new Event(id, name, address, date, startTime, endTime, code, codeTimestamp);
                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getEvent(Integer id) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Event WHERE id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date");
                Time startTime = resultSet.getTime("beginHour");
                Time endTime = resultSet.getTime("endHour");
                int code = resultSet.getInt("confirmationCode");
                Timestamp codeTimestamp = resultSet.getTimestamp("codeTimestamp");

                return new Event(id, name, address, date, startTime, endTime, code, codeTimestamp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEvent(Integer id, Event eventUpdates) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Event SET name = ?, address = ?, date = ?, beginHour = ?, endHour = ? WHERE id = ?")) {

            preparedStatement.setString(1, eventUpdates.getName());
            preparedStatement.setString(2, eventUpdates.getAddress());
            preparedStatement.setDate(3, new java.sql.Date(eventUpdates.getDate().getTime()));
            preparedStatement.setTime(4, new java.sql.Time(eventUpdates.getStartTime().getTime()));
            preparedStatement.setTime(5, new java.sql.Time(eventUpdates.getEndTime().getTime()));;
            preparedStatement.setInt(6, id);
            System.out.println(preparedStatement);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("[Events] Updated " + rowsUpdated + " rows");
            return  rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEvent(String name) {

        if (!deleteEventAttendanceByEvent(name))
            return false;

        String query = """
            DELETE FROM Event WHERE name = ?
        """;
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, name);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEventAttendanceByEvent(String name) {
        String query = """
            DELETE FROM EventAttendance
            WHERE eventId = (SELECT id FROM Event WHERE name = ?);
        """;
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, name);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getEventAttendance(String eventName) {
        String query = """
        SELECT User.id, User.name, User.email, User.isAdmin
        FROM EventAttendance
        JOIN User
            ON EventAttendance.userId = User.id
        WHERE EventAttendance.eventId IN (SELECT id FROM Event WHERE name = ?)
    """;

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, eventName);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> attendance = new ArrayList<>();
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("name");
                String userEmail = resultSet.getString("email");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                User user = new User(userId, userEmail, "", userName, isAdmin);
                attendance.add(user);
            }
            return attendance;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean createAttendance(String email, int confirmationCode) {
        String query = """
            INSERT INTO EventAttendance (eventId, userId) 
            SELECT e.id, u.id
            FROM Event as e
            JOIN User as u
            WHERE confirmationCode = ? and u.email = ?;
        """;
        try (Connection connection = DriverManager.getConnection(url)) {
//            // Verificar se já existe uma associação entre o usuário e o evento
//            if (checkEventAttendanceExists(connection, eventId, userId)) {
//                System.err.println("EventAttendance record already exists.");
//                return false;
//            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(query))
            {
                preparedStatement.setInt(1, confirmationCode);
                preparedStatement.setString(2, email);
                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCode(String name, int code, Timestamp expirationTimer) {
        String query = """
            UPDATE Event
            SET confirmationCode = ?, codeTimestamp = ?
            WHERE name = ?
        """;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            statement.setTimestamp(2, expirationTimer);
            statement.setString(3, name);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public HttpResponse createUser(User user) {
        String query = """
            INSERT INTO User (name, password, email, isAdmin)
            VALUES (?, ?, ?, false)
        """;
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0){
                return new HttpResponse(200, "Success");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 19)
                return new HttpResponse(409, "Email already in use");
        }
        return new HttpResponse(400, "Something bad happen...");
    }

    public User getUser(String email) {
        String query = """
            SELECT *
            FROM User
            WHERE email = ?
        """;
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                return new User(id, email, password, name, isAdmin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getUserAttendance(String email) {
        String query = """
            SELECT 
                Event.id, 
                Event.name, 
                Event.address, 
                Event.date,
                Event.beginHour,
                Event.endHour,
                Event.confirmationCode,
                Event.codeTimestamp
            FROM Event
            JOIN EventAttendance
                ON EventAttendance.eventId = Event.id
            JOIN User
                ON EventAttendance.userId = User.id
            WHERE User.email = ?
        """;

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Event> events = new ArrayList<>();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date");
                Time startTime = resultSet.getTime("beginHour");
                Time endTime = resultSet.getTime("endHour");
                int code = resultSet.getInt("confirmationCode");
                Timestamp codeTimestamp = resultSet.getTimestamp("codeTimestamp");

                Event event = new Event(eventId, name, address, date, startTime, endTime, code, codeTimestamp);
                events.add(event);
            }
            return events;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public boolean deleteEventByName(String eventName) {
        String query = "DELETE FROM events WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, eventName);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
