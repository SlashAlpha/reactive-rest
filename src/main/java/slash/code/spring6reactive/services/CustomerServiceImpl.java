package slash.code.spring6reactive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.mapper.CustomerMapper;
import slash.code.spring6reactive.model.CustomerDTO;
import slash.code.spring6reactive.repositories.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

   private final CustomerRepository customerRepository;
   private final CustomerMapper customerMapper;


   @Override
   public Flux<CustomerDTO> listCustomers() {
      return customerRepository.findAll().map(customerMapper::customerToCustomerDTO);
   }

   @Override
   public Mono<CustomerDTO> getCustomerById(Integer customerId) {
      return customerRepository.findById(customerId).map(customerMapper::customerToCustomerDTO);
   }

   @Override
   public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
      return customerRepository.save(customerMapper.customerDTOTOCustomer(customerDTO)).map(customerMapper::customerToCustomerDTO);

   }

   @Override
   public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
      return customerRepository.findById(customerId).map(customer -> {
         customer.setCustomerName(customerDTO.getCustomerName());
         return customer;
      }).flatMap(customerRepository::save).map(customerMapper::customerToCustomerDTO);
   }

   @Override
   public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
     return customerRepository.findById(customerId).map(existing->{
         if (StringUtils.hasText(customerDTO.getCustomerName())){
            existing.setCustomerName(customerDTO.getCustomerName());
         }

         return existing;

      }).flatMap(customerRepository::save).map(customerMapper::customerToCustomerDTO);
   }

   @Override
   public Mono<Void> deleteCustomerById(Integer customerId) {
      return customerRepository.deleteById(customerId);
   }
}
