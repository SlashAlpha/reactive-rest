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

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {
    @Autowired
    WebTestClient webTestClient;


    @Test
    @Order(2)
    void listCustomers() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(1)
    void getCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(6)
    void createNewCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/4");

    }
    @Test
    @Order(3)
    void updateCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNoContent();

    }
    @Test
    @Order(4)
    void patchCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(7)
    void deleteCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CustomerController.CUSTOMER_PATH_ID,2)
                .exchange()
                .expectStatus().isNoContent();
    }

    public static Customer getTestCustomer(){
        return Customer.builder()
                .customerName("Slash")
                .build();

    }

    @Test
    @Order(10)
    void updateCustomerByIdBadData() {
        Customer customerTest=getTestCustomer();
        customerTest.setCustomerName("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(customerTest),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    @Order(11)
    void updateCustomerByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID,999)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNotFound();

    }





    @Test
    @Order(13)
    void createNewCustomerBadData() {
        Customer customerTest=getTestCustomer();
        customerTest.setCustomerName("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(customerTest),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();

    }




    @Test
    @Order(14)
    void patchCustomerByIdBadData() {
        Customer customerTest=getTestCustomer();
        customerTest.setCustomerName("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(customerTest),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    @Order(15)
    void patchCustomerByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID,999)
                .body(Mono.just(getTestCustomer()),CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test

    void deleteCustomerByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CustomerController.CUSTOMER_PATH_ID,999)
                .exchange()
                .expectStatus().isNotFound();
    }
}