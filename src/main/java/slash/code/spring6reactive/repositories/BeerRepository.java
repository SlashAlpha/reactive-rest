package slash.code.spring6reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import slash.code.spring6reactive.domain.Beer;

public interface BeerRepository extends ReactiveCrudRepository<Beer,Integer> {

}
