package it.uniroma3.siw.siwfood.response;

import it.uniroma3.siw.siwfood.model.Role;

import java.util.List;

public class LoginResponse {
    private String token;
    private String message;
    private List<Role> roles;
    private boolean success;

    public LoginResponse(String token, String message, boolean success, List<Role> roles) {
        this.token = token;
        this.message = message;
        this.success = success;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Role> getRole(){
        return roles;
    }

    public void setRole(List<Role> roles){
        this.roles = roles;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

