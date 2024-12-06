package pt.isec.pd.spring_boot.exemplo3.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import pt.isec.pd.spring_boot.exemplo3.helpers.HttpResponse;
import pt.isec.pd.spring_boot.exemplo3.models.Event;
import pt.isec.pd.spring_boot.exemplo3.models.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

public class Client {
    private static User userInfo;


    private static final String BASE_URL = "http://localhost:8080"; // Substitua pelo URL correto da sua API

    public static void main(String[] args) throws IOException {
        String token = null;
        do {
            int choice = promptMenu(new String[]{"Register a new user", "Log in with existing credentials"});
            if (choice == 1)
                registerUser();
            else
                token = login();
            if (token == null)
                continue;

            boolean isAdmin = checkIfIsAdmin(token);
            while (true) {
                Commands cmd;
                if (isAdmin)
                    cmd = handleAdmin(token);
                else
                    cmd = handleUser(token);
                if (cmd == Commands.LOGOUT)
                    break;
                else if (cmd == Commands.EXIT) {
                    return;
                }
            }
        } while (true);
    }

    private static boolean checkIfIsAdmin(String token) throws JsonProcessingException {
        HttpResponse httpResponse = sendRequestAndShowResponse("http://localhost:8080/profile", "GET", "Bearer " + token, null);
        // Criar um ObjectMapper para fazer o parse do JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Fazer o parse do JSON a partir do corpo da resposta HTTP
        JsonNode jsonNode = objectMapper.readTree(String.valueOf(httpResponse.getResponse()));

        // Acessar a propriedade 'admin'
        return jsonNode.get("admin").asBoolean();

    }

    private static Commands handleAdmin(String token){
        Commands cmd = promptAdminMenu();
        switch (cmd) {
            case DELETE_EVENT:
                // Solicitar o nome do evento a ser excluído
                String name = promptForString("Enter the name of the event to delete: ");
                // Chamar a função para excluir o evento
                deleteEvent(token, name);
                return Commands.DELETE_EVENT;
            case CREATE_EVENT:
                Event event = promptForEventInfo();
                String eventId = createEvent(token, event);
                return Commands.CREATE_EVENT;
            case GET_EVENTS:
                getEvents(token);
                return Commands.GET_EVENTS;
            case GET_ATTENDANCE:
                name = promptForString("Enter the name of the event: ");
                getEventAttendance(token, name);
                return Commands.GET_ATTENDANCE;
            case GENERATE_CODE:
                name = promptForString("Enter event name: ");
                int minutes = promptForInt("Introduza os minutos: ");
                generateEventCode(token, name, minutes);
                return Commands.GENERATE_CODE;
            case LOGOUT:
                return Commands.LOGOUT;
            default:
                return Commands.EXIT;
        }
    }

    private static Commands handleUser(String token){
        Commands cmd = promptUserMenu();
        switch (cmd) {
            case GET_ATTENDANCES:
                getUserAttendance(token);
                return Commands.GET_ATTENDANCES;
            case REGISTER_ATTENDANCE:
                String eventName = promptForString("Nome do evento: "); // You need to implement this method
                int confirmationCode = promptForInt("Codigo: "); // You need to implement this method
                createAttendance(token, eventName, confirmationCode);
                return Commands.REGISTER_ATTENDANCE;
            case LOGOUT:
                return Commands.LOGOUT;
            default:
                return Commands.EXIT;
        }
    }

    private static String promptForString(String question) {
        String answer;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print(question);
            answer = scanner.nextLine();
        } while (answer.isEmpty());
        return answer;
    }

    private static int promptForInt(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);
        return Integer.parseInt(scanner.nextLine());
    }

    private static Time promptForTime(String question) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println(question);
                return Time.valueOf(inputReader.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Formato inválido. Tente de novo.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static Date promptForDate(String question) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println(question);
                return Date.valueOf(inputReader.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Formato inválido. Tente de novo.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Event promptForEventInfo() {
        String name = promptForString("Nome do Evento: ");
        String address = promptForString("Local do Evento: ");
        Date date = promptForDate("Data do Evento (YYYY-MM-DD): ");
        Time startTime = promptForTime("Hora de Início (HH:mm:ss): ");
        Time endTime = promptForTime("Hora de Término (HH:mm:ss): ");

        return new Event(0, name, address, date, startTime, endTime, 0, null);
    }

    private static Commands promptUserMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==== Main Menu ====");
            System.out.println("1. Registar presença");
            System.out.println("2. Consultar presenças");
            System.out.println("3. Logout");
            System.out.println("4. Sair");
            System.out.print(">");

            int choice;
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 3) {
                    System.out.println("Escolha inválida. Escolha outra vez.");
                    continue;
                }
                switch (choice) {
                    case 1:
                        return Commands.REGISTER_ATTENDANCE;
                    case 2:
                        return Commands.GET_ATTENDANCES;
                    case 3:
                        return Commands.LOGOUT;
                    case 4:
                        return Commands.EXIT;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static Commands promptAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==== Main Menu ====");
            System.out.println("1. Criar evento");
            System.out.println("2. Eliminar evento");
            System.out.println("3. Ver eventos");
            System.out.println("4. Gerar código de presença");
            System.out.println("5. Ver presenças");
            System.out.println("6. Logout");
            System.out.println("7. Sair");
            System.out.print(">");

            int choice;
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 7) {
                    System.out.println("Invalid choice. Please select a valid option.");
                    continue;
                }
                switch (choice) {
                    case 1:
                        return Commands.CREATE_EVENT;
                    case 2:
                        return Commands.DELETE_EVENT;
                    case 3:
                        return Commands.GET_EVENTS;
                    case 4:
                        return Commands.GENERATE_CODE;
                    case 5:
                        return Commands.GET_ATTENDANCE;
                    case 6:
                        return Commands.LOGOUT;
                    case 7:
                        return Commands.EXIT;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }


    static String login() throws IOException {
        String email = promptForString("Enter your email: ");
        String password = promptForString("Enter your password: ");
        String credentials = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes());

        HttpResponse httpResponse = sendRequestAndShowResponse("http://localhost:8080/login", "POST", "basic " + credentials, null);
        if (httpResponse.getCode() == 200)
            return (String) httpResponse.getResponse();
        System.out.println((String) httpResponse.getResponse());
        return null;
    }


    private static int promptMenu(String[] options) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        int choice = 0;
        while (choice <= 0 || choice > options.length) {
            System.out.print("Enter your choice (1/2): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        }
        return choice;
    }


    public static HttpResponse sendRequestAndShowResponse(String uri, String verb, String authorizationValue, String body) {

        try {
            String responseBody = null;
            URL url = null;
            url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(verb);
            connection.setRequestProperty("Accept", "application/xml, */*");

            if (authorizationValue != null) {
                connection.setRequestProperty("Authorization", authorizationValue);
            }

            if (body != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "Application/Json");
                connection.getOutputStream().write(body.getBytes());
            }

            connection.connect();
            if (connection.getResponseCode() != 200){
                System.out.println("errorrr:" + connection.getResponseMessage());
                return new HttpResponse(connection.getResponseCode(), "Something went bad...");
            }
            Scanner s;
            if (connection.getErrorStream() != null) {
                s = new Scanner(connection.getErrorStream()).useDelimiter("\\A");
                responseBody = s.hasNext() ? s.next() : null;
            }

            s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
            responseBody = s.hasNext() ? s.next() : null;

            connection.disconnect();
            return new HttpResponse(connection.getResponseCode(), responseBody);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createEvent(String token, Event event) {
        try {
            // Adicione verificações para garantir que os campos de data e hora sejam válidos
            if (event.getStartTime().toLocalTime().isAfter(event.getEndTime().toLocalTime())) {
                System.out.println("Error: Event start time cannot be after end time.");
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String eventJson = objectMapper.writeValueAsString(event);

            HttpResponse httpResponse = sendRequestAndShowResponse("http://localhost:8080/events",
                                                                  "POST",
                                                        "Bearer " + token,
                                                                        eventJson);

            // Verifique se a solicitação foi bem-sucedida (código de resposta 200)
            if (httpResponse.getCode() == 200) {
                System.out.println("Event created successfully.");
                return (String) httpResponse.getResponse(); // Pode retornar algum identificador ou mensagem relevante
            } else {
                System.out.println("Failed to create event. HTTP response code: " + httpResponse.getCode());
                return null;
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error processing event to JSON: " + e.getMessage());
            return null;
        }
    }



    static String promptForUserJson() {
        String email = promptForString("Enter your email: ");
        String name = promptForString("Enter your name: ");
        String password = promptForString("Enter your password: ");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("name", name);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject.toString();
    }

    private static void deleteEvent(String token, String eventName) {
        // Construímos a URI para excluir o evento pelo nome
        String uri = "http://localhost:8080/events/" + eventName;

        // Chamamos a função para excluir o evento
        HttpResponse httpResponse = sendRequestAndShowResponse(uri, "DELETE", "Bearer " + token, null);

        // Analisamos a resposta
        if (httpResponse.getCode() == 200) {
            // Se a resposta for bem-sucedida, imprima uma mensagem de sucesso
            System.out.println("Event deleted successfully.");
        } else {
            // Se não foi possível excluir o evento, imprima uma mensagem de erro
            System.out.println("Failed to delete event. HTTP response code: " + httpResponse.getCode());
        }
    }

    private static void getEventAttendance(String token, String eventName) {
        // Construir a URL
        String url = "http://localhost:8080/events/" + eventName + "/attendance";

        // Fazer a chamada GET para a API
        HttpResponse httpResponse = sendRequestAndShowResponse(url, "GET", "Bearer " + token, null);

        // Verificar a resposta da API
        if (httpResponse.getCode() == 200) {
            String responseContent = (String) httpResponse.getResponse();
            if (responseContent.trim().isEmpty()) {
                // Array vazio indica que o evento não foi encontrado
                System.out.println("Event not found in the database.");
            } else {
                // Sucesso - Exibir a presença dos usuários no evento
                System.out.println("Event Attendance:\n" + responseContent);
            }
        } else {
            // Outro código de resposta - Exibir mensagem de erro
            System.out.println("Failed to get event attendance. HTTP response code: " + httpResponse.getCode());
        }
    }

    private static void generateEventCode(String token, String eventName, int minutes) {
        String url = "http://localhost:8080/events/" + eventName + "/generateCode";

        Map<String, Integer> payload = new HashMap<>();
        payload.put("minutes", 20);
        String requestBody;
        try {
            requestBody = new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Fazer a chamada PUT para a API
        HttpResponse httpResponse = sendRequestAndShowResponse(url, "PUT", "Bearer " + token, requestBody);

        // Verificar a resposta da API
        if (httpResponse.getCode() == 200) {
            // Sucesso - Exibir a mensagem da API
            System.out.println(httpResponse.getResponse());
        } else {
            // Falha - Exibir mensagem de erro
            System.out.println("Failed to generate event code. HTTP response code: " + httpResponse.getCode());
        }
    }

    private static void getUserAttendance(String token) {
        System.out.println("==== Get User Attendance ====");
        String url = "http://localhost:8080/profile/attendance";
        HttpResponse httpResponse = sendRequestAndShowResponse(url, "GET", "Bearer " + token, null);

        if (httpResponse.getCode() == 200) {
            System.out.println("User Attendance Events:\n" + httpResponse.getResponse());
        } else {
            System.out.println("Failed to get user attendance events. HTTP response code: " + httpResponse.getCode());
        }
    }

    private static void getEvents(String token) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== Get Events ====");

        String url = "http://localhost:8080/events";
        HttpResponse httpResponse = sendRequestAndShowResponse(url, "GET", "Bearer " + token, null);

        if (httpResponse.getCode() == 200) {
            String response = (String) httpResponse.getResponse();

            ObjectMapper objectMapper = new ObjectMapper();
            List<JsonNode> events = null;
            try {
                events = objectMapper.readValue(response, new TypeReference<List<JsonNode>>() {
                });
            } catch (JsonProcessingException e) {
                System.out.println("Error parsing events");
                throw new RuntimeException(e);
            }

            // Imprimir cada evento formatado
            for (JsonNode eventNode : events) {
                String name = eventNode.get("name").asText();
                String address = eventNode.get("address").asText();
                String date = eventNode.get("date").asText();
                String startTime = eventNode.get("startTime").asText();
                String endTime = eventNode.get("endTime").asText();
                int code = eventNode.get("code").asInt();
                String codeTimestamp = eventNode.get("codeTimestamp").asText();

                System.out.println("Event:");
                System.out.println("  Name: " + name);
                System.out.println("  Address: " + address);
                System.out.println("  Date: " + date);
                System.out.println("  Start Time: " + startTime);
                System.out.println("  End Time: " + endTime);
                System.out.println("  Code: " + code);
                System.out.println("  Timestamp: " + codeTimestamp);
            }
        } else {
            // Falha - Exibir mensagem de erro
            System.out.println("Failed to get events. HTTP response code: " + httpResponse.getCode());
        }
    }


// ...

    private static void createAttendance(String token, String eventName, int confirmationCode) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== Create Attendance ====");

        // Construct the URL
        String url = "http://localhost:8080/profile/attendance";

        // Construct the payload
        JSONObject payload = new JSONObject();
        try {
            payload.put("eventName", eventName);
            payload.put("confirmationCode", confirmationCode);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Make the POST request to the API
        HttpResponse httpResponse = sendRequestAndShowResponse(url, "POST", "Bearer " + token, payload.toString());

        // Check the API response
        if (httpResponse.getCode() == 200) {
            // Success - Display the response
            System.out.println(httpResponse.getResponse());
        } else {
            // Failure - Display an error message
            System.out.println("Failed to create attendance entry. HTTP response code: " + httpResponse.getCode());
        }
    }

    private static void registerUser() {
        String json = promptForUserJson();
//            ObjectMapper objectMapper = new ObjectMapper();
//            String userJson = objectMapper.writeValueAsString(user);

        // Defina a URL correta para registrar usuários
        String registerUserUrl = "http://localhost:8080/register";

        // Envie a solicitação para registrar o usuário
        HttpResponse httpResponse = sendRequestAndShowResponse(registerUserUrl, "POST", null, json);

        // Verifique se a solicitação foi bem-sucedida (código de resposta 200)
        if (httpResponse.getCode() == 200) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Failed to register user. HTTP response code: " + httpResponse.getCode());
        }

        return;
    }

}
