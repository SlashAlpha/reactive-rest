package slash.code.spring6reactive.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.model.BeerDTO;

public interface BeerService {

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Integer id);

    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO);

    Mono<Void> deleteBeerById(Integer beerId);
}
