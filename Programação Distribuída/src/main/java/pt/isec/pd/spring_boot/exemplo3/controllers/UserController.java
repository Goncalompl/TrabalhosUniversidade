package pt.isec.pd.spring_boot.exemplo3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pt.isec.pd.spring_boot.exemplo3.helpers.DatabaseHelper;
import pt.isec.pd.spring_boot.exemplo3.models.Event;
import pt.isec.pd.spring_boot.exemplo3.models.User;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("profile")
public class UserController {

    @GetMapping()
    public ResponseEntity<User> getProfile(Authentication authentication) {
        DatabaseHelper dbHelper = new DatabaseHelper();

        String email = authentication.getName();
        User user = (User) dbHelper.getUser(email);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/attendance")
    public ResponseEntity<?> getUserAttendance(Authentication authentication) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        String email = authentication.getName();
        List<Event> events = dbHelper.getUserAttendance(email);
        if (events != null)
            return ResponseEntity.ok().body(events);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/attendance")
    public ResponseEntity<?> createAttendance(Authentication authentication,
                                              @RequestBody Map<String, Object> payload) {
        DatabaseHelper dbHelper = new DatabaseHelper();

        String email = authentication.getName();
        String eventName = (String) payload.get("eventName");
        int confirmationCode = (int) payload.get("confirmationCode");

        boolean result = dbHelper.createAttendance(email, confirmationCode);
        if (result)
            return ResponseEntity.ok().body("Attendance entry created.");
        return ResponseEntity.badRequest().build();

    }
}
