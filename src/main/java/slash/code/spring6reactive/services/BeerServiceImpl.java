package slash.code.spring6reactive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import slash.code.spring6reactive.mapper.BeerMapper;
import slash.code.spring6reactive.model.BeerDTO;
import slash.code.spring6reactive.repositories.BeerRepository;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

   private final BeerRepository beerRepository;
   private final BeerMapper beerMapper;


    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll().map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer id) {return beerRepository.findById(id).map(beerMapper::beerToBeerDTO);}

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {

        return beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)).map(beerMapper::beerToBeerDTO);

    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {

        return beerRepository.findById(beerId).map(foundBeer->{
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setPrice(beerDTO.getPrice());
            foundBeer.setUpc(beerDTO.getUpc());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            return foundBeer;
        }).flatMap(beerRepository::save).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO) {


      return beerRepository.findById(beerId).map(existing->{
            if (StringUtils.hasText(beerDTO.getBeerName())){
                existing.setBeerName(beerDTO.getBeerName());
            }

            if (beerDTO.getBeerStyle() != null) {
                existing.setBeerStyle(beerDTO.getBeerStyle());
            }

            if (beerDTO.getPrice() != null) {
                existing.setPrice(beerDTO.getPrice());
            }

            if (beerDTO.getQuantityOnHand() != null){
                existing.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }

            if (StringUtils.hasText(beerDTO.getUpc())) {
                existing.setUpc(beerDTO.getUpc());
            }

          return existing;
      }).flatMap(beerRepository::save).map(beerMapper::beerToBeerDTO);



    }

    @Override
    public Mono<Void> deleteBeerById(Integer beerId) {
       return beerRepository.deleteById(beerId);
    }
}
