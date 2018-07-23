package pl.codepride.dailyadvisor.userservice.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import org.springframework.data.cassandra.repository.Query;
import pl.codepride.dailyadvisor.userservice.model.entity.Customer;

public interface CustomerRepository extends CassandraRepository<Customer, UUID> {

	List<Customer> findByAge(int age);
}
