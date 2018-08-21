package pl.codepride.dailyadvisor.userservice.model.request;

import lombok.Data;

@Data
public class UserProfileRequest {

    private String name;

    private String lastName;

    private String city;

    private String about;


    public UserProfileRequest() {
    }

    public UserProfileRequest(String name, String lastName, String city, String about) {
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.about = about;
    }

}
