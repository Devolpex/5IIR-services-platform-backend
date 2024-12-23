package org._iir.backend.modules.auth.mail;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public abstract class AbsractEmailContext {
    private String from;
    private String to;
    private String subject;
    private String email;
    private String templateLocation;

    private Map<String,Object> context;


    public AbsractEmailContext() {
        this.context = new HashMap<>();
    }

    public <T> void init(T context){

    }
    public Object put(String key, Object value){
        return key == null ? null : this.context.put(key, value);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}
