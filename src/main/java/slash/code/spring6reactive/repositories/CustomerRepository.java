package slash.code.spring6reactive.repositories;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import slash.code.spring6reactive.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {
}
