package slash.code.spring6reactive.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.model.CustomerDTO;

public interface CustomerService {



    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> getCustomerById(Integer customerId);

    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<Void> deleteCustomerById(Integer customerId);

}
