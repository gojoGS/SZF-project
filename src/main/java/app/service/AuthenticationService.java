package app.service;

public interface AuthenticationService {
    AuthenticationResponse authenticate(String username, String password);
}
