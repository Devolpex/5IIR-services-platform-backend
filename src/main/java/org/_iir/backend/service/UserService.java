package org._iir.backend.service;

import jakarta.mail.MessagingException;
import org._iir.backend.bean.SecureToken;
import org._iir.backend.bean.User;
import org._iir.backend.dao.UserDao;
import org._iir.backend.exception.UserAleradyExistException;
import org._iir.backend.mail.AccountVerificationEmailContext;
import org._iir.backend.mail.EmailService;
import org._iir.backend.mail.SecureTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void register(User user)throws UserAleradyExistException {

        if(isUserExist(user.getEmail())){
            throw new UserAleradyExistException("User already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        sendRegistrationEmail(user);
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


}
