package pl.codepride.dailyadvisor.userservice.model.entity;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Table(value = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 60 * 24;


    @PrimaryKey
    private UUID id;

    private String token;
    usuniecie tokena rozpierdala

    private UUID userId;

    @Column(value = "expire_date")
    private OffsetDateTime expiryDate;

    public VerificationToken(UUID userId, UUID id) {
        this.id = UUIDs.timeBased();
        this.userId = userId;
        OffsetDateTime zdt = OffsetDateTime.now();
        this.expiryDate = zdt.plusSeconds(EXPIRATION);
    }

    public VerificationToken() {}

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getExpiryDate(), that.getExpiryDate());
    }

}
