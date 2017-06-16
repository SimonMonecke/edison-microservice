package de.otto.edison.togglz.authentication;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Credentials {

    private final String username;
    private final String password;

    private Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static Optional<Credentials> readFrom(HttpServletRequest request) {
        String authenticationHeader = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authenticationHeader)) {
            String credentials = authenticationHeader.substring(6, authenticationHeader.length());
            String[] decodedCredentialParts = new String(Base64Utils.decode(credentials.getBytes())).split(":");
            String username = decodedCredentialParts.length >= 1 ? decodedCredentialParts[0] : "";
            String password = decodedCredentialParts.length >= 2 ? decodedCredentialParts[1] : "";
            return Optional.of(new Credentials(username, password));
        } else {
            return Optional.empty();
        }
    }
}
