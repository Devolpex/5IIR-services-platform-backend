package org._iir.backend.mail;

import org._iir.backend.bean.SecureToken;
import org._iir.backend.dao.SecureTokenDao;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DefaultSecureTokenService implements SecureTokenService{


    private  static BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(12);

    //expiration time in seconds
    @Value("2800")
    private int tokenValiditySeconds ;

    @Autowired
    private SecureTokenDao secureTokenDao;

    @Override
    public SecureToken createSecureToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey()));
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpiredAt(LocalDate.from(LocalDateTime.now().plusSeconds(tokenValiditySeconds)));
        this.saveSecureToken(secureToken);
        return secureToken;


    }

    @Override
    public void saveSecureToken(SecureToken secureToken) {
        secureTokenDao.save(secureToken);

    }

    @Override
    public SecureToken findByToken(String token) {
        return secureTokenDao.findByToken(token);
    }

    @Override
    public void removeToken(String token) {
        secureTokenDao.deleteByToken(token);
    }
}
