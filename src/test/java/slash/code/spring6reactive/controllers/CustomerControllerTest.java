package slash.code.spring6reactive.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.domain.Customer;
import slash.code.spring6reactive.model.CustomerDTO;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {
    @Autowired
    WebTestClient webTestClient;


    @Test
    @Order(2)
    void listCustomers() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(1)
    void getCustomerById() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(6)
    void createNewCustomer() {
        webTestClient.post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/4");

    }
    public static Customer getTestCustomer(){
        return Customer.builder()
                .customerName("Slash")
                .build();

    }

    @Test
    @Order(3)
    void updateBeerById() {
        webTestClient.put().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(4)
    void patchCustomerById() {
        webTestClient.patch().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(5)
    void deleteCustomerById() {
        webTestClient.delete().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();
    }
}