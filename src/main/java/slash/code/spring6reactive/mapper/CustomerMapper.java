package slash.code.spring6reactive.mapper;

import org.mapstruct.Mapper;
import slash.code.spring6reactive.domain.Customer;
import slash.code.spring6reactive.model.CustomerDTO;

@Mapper
public interface CustomerMapper {


    Customer customerDTOTOCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);



}
