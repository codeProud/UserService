package pl.codepride.dailyadvisor.userservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.utils.UUIDs;
import pl.codepride.dailyadvisor.userservice.model.entity.Customer;
import pl.codepride.dailyadvisor.userservice.repo.CustomerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository repository;

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		System.out.println("Get all Customers...");

		return repository.findAll();
	}

	@PostMapping("/customers/create")
	public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) {
		System.out.println("Create Customer: " + customer.getName() + "...");

		customer.setId(UUIDs.timeBased());
		Customer _customer = repository.save(customer);
		return new ResponseEntity<>(_customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") UUID id) {
		System.out.println("Delete Customer with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}

	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Delete All Customers...");

		repository.deleteAll();

		return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
	}

	@GetMapping("customers/age/{age}")
	public List<Customer> findByAge(@PathVariable int age) {

		List<Customer> customers = repository.findByAge(age);
		return customers;
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer) {
		System.out.println("Update Customer with ID = " + id + "...");

		Optional<Customer> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setName(customer.getName());
			_customer.setAge(customer.getAge());
			_customer.setActive(customer.isActive());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
