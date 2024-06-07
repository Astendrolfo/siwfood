package it.uniroma3.siw.siwfood.response;

public class LoginResponse {
    private String token;
    private String message;
    private boolean success;

    public LoginResponse(String token, String message, boolean success) {
        this.token = token;
        this.message = message;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

