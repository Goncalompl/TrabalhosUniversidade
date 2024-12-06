package pt.isec.pd.spring_boot.exemplo3.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isec.pd.spring_boot.exemplo3.helpers.DatabaseHelper;
import pt.isec.pd.spring_boot.exemplo3.models.Event;
import pt.isec.pd.spring_boot.exemplo3.models.User;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("events")
public class EventController {
    // TODO should only be available to admins
    // if (!hasAdminRole()) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body("Access denied. You must be an admin to access this endpoint.");
//        }
    @GetMapping()
    public List<Event> getEvents(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "date", required = false) Date date) {
//        TODO if (name != null && date != null) {
//            events = eventsService.findEventsByNameAndDate(name, date);
//        } else if (name != null) {
//            events = eventsService.findEventsByName(name);
//        } else if (date != null) {
//            events = eventsService.findEventsByDate(date);
//        } else {
//            events = eventsService.findAllEvents();
//        }
        DatabaseHelper dbHelper = new DatabaseHelper();
        return dbHelper.getEvents();
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        boolean result = dbHelper.createEvent(event);
        if (result)
            return ResponseEntity.ok().body("Event created successfully.");
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteEvent(@PathVariable String name) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        boolean result = dbHelper.deleteEvent(name);
        if (result)
            return ResponseEntity.ok().body("Event deleted successfully.");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{name}/attendance")
    public ResponseEntity<?> getAttendance(@PathVariable String name) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        List<User> attendance = dbHelper.getEventAttendance(name);
        if (attendance != null)
            return ResponseEntity.ok().body(attendance);
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{name}/generateCode")
    public ResponseEntity<String> updateConfirmationCode(@PathVariable String name,
                                                         @RequestBody Map<String, Integer> payload)
    {
        int codeValidityTimer = payload.get("minutes");
        java.util.Date currentTime = new java.util.Date();
        Timestamp codeTimestamp = new Timestamp(currentTime.getTime() + (60L * 1000 * codeValidityTimer));

        Random random = new Random();
        int code = random.nextInt(900000) + 100000;

        DatabaseHelper dbHelper = new DatabaseHelper();
        boolean result = dbHelper.updateCode(name, code, codeTimestamp);
        if (result)
            return ResponseEntity.ok().body("New confirmation code: " + code + ".");
        return ResponseEntity.badRequest().build();
    }
}