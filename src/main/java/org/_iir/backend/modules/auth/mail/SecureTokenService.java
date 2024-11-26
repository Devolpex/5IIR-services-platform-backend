package org._iir.backend.modules.auth.mail;


import org._iir.backend.modules.user.SecureToken;

public interface SecureTokenService {

    SecureToken createSecureToken();

    void saveSecureToken(SecureToken secureToken);
    SecureToken findByToken(final String token);
    void removeToken(final String token);
}
