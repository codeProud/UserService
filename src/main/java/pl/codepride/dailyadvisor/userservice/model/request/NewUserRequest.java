package pl.codepride.dailyadvisor.userservice.model.request;

import lombok.Data;

@Data
public class NewUserRequest {

    private String email;

    private String name;

    private String lastName;

    private String password;

    private String userType;


    public NewUserRequest() {
    }

    public NewUserRequest(String email, String name, String lastName, String password, String userType) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
    }
}
