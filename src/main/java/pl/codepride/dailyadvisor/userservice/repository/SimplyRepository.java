package pl.codepride.dailyadvisor.userservice.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface SimplyRepository<T> extends CassandraRepository<T, UUID> {
}
