package com.bns.bnsref.Controller;

import com.bns.bnsref.security.Classes.SignUpRequest;
import com.bns.bnsref.security.Classes.ForgotPasswordRequest;
import com.bns.bnsref.security.Classes.LoginRequest;
import com.bns.bnsref.security.Classes.LogoutRequest;
import com.bns.bnsref.security.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private KeycloakService keycloakService;


    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AccessTokenResponse token = keycloakService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            keycloakService.createUser(signUpRequest);
            return ResponseEntity.ok("Utilisateur créé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            keycloakService.resetPassword(forgotPasswordRequest.getUsername());
            return ResponseEntity.ok("Email de réinitialisation envoyé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            keycloakService.logout(logoutRequest.getRefreshToken());
            return ResponseEntity.ok("Déconnexion réussie");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la déconnexion : " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        AccessTokenResponse response = keycloakService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}

