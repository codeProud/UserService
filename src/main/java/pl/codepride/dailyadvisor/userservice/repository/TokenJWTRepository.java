package pl.codepride.dailyadvisor.userservice.repository;


import org.springframework.data.cassandra.repository.Query;
import pl.codepride.dailyadvisor.userservice.model.entity.TokenJWT;


public interface TokenJWTRepository extends SimplyRepository<TokenJWT> {

    @Query(value = "[SELECT * FROM token_jwt WHERE token_id=?0")
    TokenJWT findByTokenId(String tokenId);

}
