package com.bns.bnsref.security;

import com.bns.bnsref.security.Classes.SignUpRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class KeycloakService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public void createUser(SignUpRequest signUpRequest) {
        // Configurer les informations de l'utilisateur
        UserRepresentation user = new UserRepresentation();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setRequiredActions(Collections.singletonList("VERIFY_EMAIL"));

        // Configurer le mot de passe
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(signUpRequest.getPassword());
        user.setCredentials(Collections.singletonList(passwordCred));

        // Accéder au realm
        try {
            Response response = keycloak.realm(realm).users().create(user);

            // Gérer les erreurs
            if (response.getStatus() == 201) {
                // Envoyer l'email de vérification
                String userId = CreatedResponseUtil.getCreatedId(response);
                try {
                    keycloak.realm(realm).users().get(userId).sendVerifyEmail();
                } catch (Exception e) {
                    throw new RuntimeException("Erreur lors de l'envoi de l'email de vérification : " + e.getMessage());
                }
            } else if (response.getStatus() == 409) {
                throw new RuntimeException("Utilisateur déjà existant : le nom d'utilisateur ou l'email est déjà utilisé.");
            } else if (response.getStatus() == 404) {
                throw new RuntimeException("Ressource non trouvée : vérifiez que le realm " + realm + " existe.");
            } else if (response.getStatus() == 400) {
                String errorMessage = response.readEntity(String.class);
                throw new RuntimeException("Requête invalide : " + errorMessage + " (Code: 400)");
            } else {
                throw new RuntimeException("Erreur lors de la création de l'utilisateur : " + response.getStatusInfo().getReasonPhrase() + " (Code: " + response.getStatus() + ")");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel à l'API Keycloak : " + e.getMessage());
        }
    }

    public AccessTokenResponse login(String username, String password) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String tokenEndpoint = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            // Préparer le corps de la requête
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "password");
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            requestBody.add("username", username);
            requestBody.add("password", password);

            // Configurer les en-têtes
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Créer la requête
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

            // Appeler l'endpoint de token
            return restTemplate.postForObject(tokenEndpoint, request, AccessTokenResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'authentification : " + e.getMessage());
        }
    }

    public void resetPassword(String username) {
        try {
            // Recherche de l'utilisateur par nom d'utilisateur
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .search(username, true);

            if (users.isEmpty()) {
                throw new RuntimeException("Utilisateur non trouvé : " + username);
            }

            // Récupérer l'ID de l'utilisateur
            String userId = users.get(0).getId();

            // Déclencher l'action de réinitialisation de mot de passe
            keycloak.realm(realm)
                    .users()
                    .get(userId)
                    .executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la réinitialisation du mot de passe : " + e.getMessage());
        }
    }

    public void logout(String refreshToken) {
        try {
            // Appeler l'endpoint de logout de Keycloak pour révoquer le refresh token
            RestTemplate restTemplate = new RestTemplate();
            String logoutEndpoint = serverUrl + "/realms/" + realm + "/protocol/openid-connect/logout";

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            requestBody.add("refresh_token", refreshToken);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

            restTemplate.postForEntity(logoutEndpoint, request, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la déconnexion : " + e.getMessage());
        }
    }

    public String getUserIdByUsername(String username) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .search(username, true);
            if (users.isEmpty()) {
                throw new RuntimeException("Utilisateur non trouvé : " + username);
            }
            return users.get(0).getId();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
        }
    }
}