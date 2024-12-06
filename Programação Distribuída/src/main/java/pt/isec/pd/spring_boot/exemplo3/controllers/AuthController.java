package pt.isec.pd.spring_boot.exemplo3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.isec.pd.spring_boot.exemplo3.helpers.DatabaseHelper;
import pt.isec.pd.spring_boot.exemplo3.helpers.HttpResponse;
import pt.isec.pd.spring_boot.exemplo3.models.User;
import pt.isec.pd.spring_boot.exemplo3.security.TokenService;

@RestController
public class AuthController
{
    private final TokenService tokenService;

    public AuthController(TokenService tokenService)
    {
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public static ResponseEntity<String> register(@RequestBody User user) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        HttpResponse response = dbHelper.createUser(user);
        if (response.getCode() == 200)
            return ResponseEntity.ok().body( (String) response.getResponse());
        return ResponseEntity.badRequest().body( (String) response.getResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(Authentication authentication)
    {
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(token);
    }
}
