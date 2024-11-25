package org._iir.backend.mail;

import org._iir.backend.bean.SecureToken;

public interface SecureTokenService {

    SecureToken createSecureToken();

    void saveSecureToken(SecureToken secureToken);
    SecureToken findByToken(final String token);
    void removeToken(final String token);
}
