package pl.codepride.dailyadvisor.userservice.model.entity;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("token_jwt")
@Data
public class TokenJWT {

    @PrimaryKey
    private UUID id;

    @Column("user_id")
    private UUID userId;

    private LocalDate timestamp;


    public TokenJWT() {
        this.id = UUIDs.timeBased();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenJWT tokenJWT = (TokenJWT) o;
        return Objects.equals(userId, tokenJWT.userId) &&
                Objects.equals(timestamp, tokenJWT.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
