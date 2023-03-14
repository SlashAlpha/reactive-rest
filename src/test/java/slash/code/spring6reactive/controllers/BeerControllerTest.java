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
import slash.code.spring6reactive.domain.Beer;
import slash.code.spring6reactive.model.BeerDTO;
import slash.code.spring6reactive.repositories.BeerRepositoryTest;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerControllerTest {


    @Autowired
     WebTestClient webTestClient;

    @Test
    @Order(2)
    void testListBeers() {
        webTestClient.get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);

    }

    @Test
    @Order(1)
    void testGetById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BeerDTO.class);
    }
    @Test
    @Order(12)
    void testGetByIdNotFound() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID,999)
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    @Order(7)
    void testCreateBeerBadData() {
        Beer testBeer=BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");
        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(testBeer),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(6)
    void testCreateBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    @Order(11)
    void testPatchBeer() {
        webTestClient.patch().uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isOk();



    }

    @Test
    @Order(4)
    void testPatchBeerBadData() {
        Beer testBeer=BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");
        webTestClient.patch().uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(testBeer),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    @Order(10)
    void testPatchBeerNotFound() {
        webTestClient.patch().uri(BeerController.BEER_PATH_ID,999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNotFound();



    }

    @Test
    @Order(99)
    void testDeleteBeer() {
        webTestClient.delete().uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    @Order(3)
    void testUpdateBeer() {
        webTestClient.put().uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    @Order(9)
    void testUpdateBeerNotFound() {
        webTestClient.put().uri(BeerController.BEER_PATH_ID,999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    @Order(8)
    void testUpdateBeerBadData() {
        Beer testBeer=BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");
        webTestClient.put().uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(testBeer),BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    void testDeleteNotFound() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }
}