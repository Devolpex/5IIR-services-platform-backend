package org._iir.backend.service;

import ch.qos.logback.core.util.StringUtil;
import jakarta.mail.MessagingException;
import org._iir.backend.bean.SecureToken;
import org._iir.backend.bean.User;
import org._iir.backend.dao.UserDao;
import org._iir.backend.exception.InvalidTokenException;
import org._iir.backend.exception.UserAleradyExistException;
import org._iir.backend.http.RegistrationRequest;
import org._iir.backend.mail.AccountVerificationEmailContext;
import org._iir.backend.mail.EmailService;
import org._iir.backend.mail.SecureTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {


    private String baseUrl = "http://localhost:8081";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    private EmailService emailService;

    //get all users
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    //get user by id
    public User getUserById(int id){
        return userDao.findById(id).orElse(null);
    }

    //delete user by id
    public void deleteUserById(int id){
        userDao.deleteById(id);
    }

    public void register(RegistrationRequest request)throws UserAleradyExistException {

        if(isUserExist(request.email())){
            throw new UserAleradyExistException("User already exist");
        }
        User user = new User();
        user.setNom(request.nom());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setAccountVerified(false);  // Assuming account is not verified initially
        user.setPassword(passwordEncoder.encode(request.password()));  // Hashing the password

        userDao.save(user);  // Save user to the database
        sendRegistrationEmail(user);  // Send a registration confirmation email (if required)
    }


    public boolean isUserExist(String email){
        return userDao.findByEmail(email).isPresent();
    }

    public void sendRegistrationEmail(User user){

        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);

        //send email
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setVerifyToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseUrl, secureToken.getToken());
        try {
            emailService.sendEmail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyAccount(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken)||secureToken.isExpired()){
            throw new InvalidTokenException("Invalid or expired token");
        }
        User user = userDao.getById(Math.toIntExact(secureToken.getUser().getId()));
       if (Objects.isNull(user)){
           throw new InvalidTokenException("User not found");

       }
       user.setAccountVerified(true);
         userDao.save(user);
            secureTokenService.removeToken(token);
            return true;
    }


}
