package pl.codepride.dailyadvisor.userservice.model.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Table
@Data
public class TokenJWT {

    @PrimaryKey
    private UUID tokenId;

    private User user;
    private Date timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenJWT tokenJWT = (TokenJWT) o;
        return Objects.equals(user, tokenJWT.user) &&
                Objects.equals(timestamp, tokenJWT.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId);
    }
}
