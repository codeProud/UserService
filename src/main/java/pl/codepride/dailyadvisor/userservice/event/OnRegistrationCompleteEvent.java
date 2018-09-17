package pl.codepride.dailyadvisor.userservice.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;
import pl.codepride.dailyadvisor.userservice.model.entity.User;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
}
