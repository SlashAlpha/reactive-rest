package slash.code.spring6reactive.mapper;

import org.mapstruct.Mapper;
import slash.code.spring6reactive.domain.Beer;
import slash.code.spring6reactive.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDTO(Beer beer);
}
