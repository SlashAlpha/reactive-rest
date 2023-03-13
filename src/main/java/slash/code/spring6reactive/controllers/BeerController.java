package slash.code.spring6reactive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.model.BeerDTO;
import slash.code.spring6reactive.services.BeerService;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH="/api/v2/beer";
    public static final String BEER_PATH_ID=BEER_PATH+"/{beerId}";



  private final BeerService beerService;

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId){

        return beerService.getBeerById(beerId);
    }

    @PostMapping(BEER_PATH)
    ResponseEntity<Void> createNewBeer(@RequestBody BeerDTO beerDTO){

        AtomicInteger atomicInteger=new AtomicInteger();
        beerService.saveNewBeer(beerDTO).subscribe(savedDto->{
            atomicInteger.set(savedDto.getId());
        });

     return ResponseEntity.created(UriComponentsBuilder
             .fromHttpUrl("http://localhost:8080"+BEER_PATH+"/"+atomicInteger.get())
             .build().toUri())
             .build();
    }
    @PutMapping(BEER_PATH_ID)
    ResponseEntity<Void> updateExistingBeerById(@PathVariable("beerId") Integer beerId,@Validated  @RequestBody BeerDTO beerDTO){

        beerService.updateBeer(beerId,beerDTO).subscribe();
        return ResponseEntity.ok().build();

    }
    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingBeer(@PathVariable Integer beerId,
                                                @Validated @RequestBody BeerDTO beerDTO){
        return beerService.patchBeer(beerId, beerDTO)
                .map(updatedDto -> ResponseEntity.ok().build());
    }

    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteExistingBeer(@PathVariable("beerId") Integer beerId){

       return beerService.deleteBeerById(beerId).thenReturn(ResponseEntity.noContent().build());

    }




}
