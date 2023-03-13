package slash.code.spring6reactive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.model.CustomerDTO;
import slash.code.spring6reactive.services.CustomerService;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public static final String CUSTOMER_PATH="/api/v2/customer";
    public static final String CUSTOMER_PATH_ID=CUSTOMER_PATH+"/{customerId}";

    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> listCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Integer customerId){
       return customerService.getCustomerById(customerId);

    }

    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<Void>> createNewCustomer(@RequestBody CustomerDTO customerDTO){
//        AtomicInteger atomicInteger=new AtomicInteger();
//        customerService.saveNewCustomer(customerDTO).subscribe(saveDTO -> {
//            atomicInteger.set(saveDTO.getId());});
//        return ResponseEntity.created(UriComponentsBuilder
//                        .fromHttpUrl("http://localhost:8080"+CUSTOMER_PATH+"/"+atomicInteger.get())
//                        .build().toUri())
//                .build();
            return customerService.saveNewCustomer(customerDTO)
                    .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                    .fromHttpUrl("http://localhost:8080/" + CUSTOMER_PATH
                                            + "/" + savedDto.getId())
                                    .build().toUri())
                            .build());
        }


    @PutMapping(CUSTOMER_PATH_ID)
    ResponseEntity<Void> updateBeerById(@PathVariable("customerId")Integer customerId,@RequestBody CustomerDTO customerDTO){
        customerService.updateCustomer(customerId,customerDTO).subscribe();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(CUSTOMER_PATH_ID)
   Mono< ResponseEntity<Void>>patchCustomerById(@PathVariable("customerId")Integer customerId,@RequestBody CustomerDTO customerDTO){
       return customerService.patchCustomer(customerId,customerDTO).map(updated->ResponseEntity.ok().build());

    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCustomerById(@PathVariable("customerId")Integer customerId){
        return customerService.deleteCustomerById(customerId).thenReturn(ResponseEntity.noContent().build());
    }


}
