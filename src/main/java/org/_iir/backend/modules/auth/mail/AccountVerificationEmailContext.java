package org._iir.backend.modules.auth.mail;



import org._iir.backend.modules.user.User;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbsractEmailContext {

    private String verifyToken;

    @Override
    public <T> void init(T context) {
        if (context instanceof User) {
            User user = (User) context;
            put("nom", user.getNom()); // Lowercased key for consistency
            setTemplateLocation("email/email-verification");
            setSubject("Vérification de votre compte");
            setFrom("devolpex@gmail.com");
            setTo(user.getEmail());
        } else {
            throw new IllegalArgumentException("Context must be an instance of User");
        }
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public void buildVerificationUrl(final String baseUrl, final String verifyToken) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/registration/verify")
                .queryParam("token", verifyToken)
                .toUriString();
        put("verificationURL", url); // Save the URL in the context for the template
    }

}
