package pl.codepride.dailyadvisor.userservice.model.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("oauth_preserved_state")
@Data
public class OAuth2PreservedState {

    @PrimaryKey
    private UUID id;

    @Column("state_key")
    private String stateKey;

    @Column("preserved_state")
    private String preservedState;


    public OAuth2PreservedState() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2PreservedState that = (OAuth2PreservedState) o;
        return Objects.equals(getStateKey(), that.getStateKey()) &&
                Objects.equals(getPreservedState(), that.getPreservedState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
