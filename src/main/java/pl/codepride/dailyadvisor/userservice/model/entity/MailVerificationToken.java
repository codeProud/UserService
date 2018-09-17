package pl.codepride.dailyadvisor.userservice.model.entity;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Data
@Table(value = "mail_verification_token")
public class MailVerificationToken {
    private static final int EXPIRATION = 60 * 60 * 24;


    @PrimaryKey
    private UUID id;

    @Column(value = "user_id")
    private UUID userId;

    @Column(value = "expire_date")
    private String expireDate;

    public MailVerificationToken(UUID userId, UUID id) {
        this.id = UUIDs.timeBased();
        this.userId = userId;
        this.expireDate = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toString();
    }

    public MailVerificationToken() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailVerificationToken that = (MailVerificationToken) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getExpireDate(), that.getExpireDate());
    }

}
