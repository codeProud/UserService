package pl.codepride.dailyadvisor.userservice.model.response;

import lombok.Data;
import pl.codepride.dailyadvisor.userservice.model.entity.User;

import java.util.UUID;

@Data
public class UserProfileResponse {
    private UUID userId;
    private String name;
    private String lastName;
    private String city;
    private String about;

    public UserProfileResponse(UUID userId, String name, String lastName, String city, String about) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.about = about;
    }

    public UserProfileResponse() {
    }

    public UserProfileResponse(User user) {
        this.userId=user.getId();
        this.name=user.getName();
        this.lastName=user.getLastName();
        this.about=user.getAbout();
        this.city=user.getCity();
    }

}
