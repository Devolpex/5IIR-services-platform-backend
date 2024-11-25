package org._iir.backend.mail;

import jakarta.mail.MessagingException;

public interface  EmailService {

    void sendEmail(final AbsractEmailContext context)throws MessagingException;
}
