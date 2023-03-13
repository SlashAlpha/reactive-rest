package slash.code.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import slash.code.spring6reactive.config.DatabaseConfig;
import slash.code.spring6reactive.domain.Beer;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveNewBeer() {
        beerRepository.save(getTestBeer())
                .subscribe(beer -> {
                    System.out.println(beer.toString());

                });

    }
   public static Beer getTestBeer(){
       return Beer.builder()
               .beerName("Space Dust")
               .beerStyle("IPA")
               .price(BigDecimal.TEN)
               .upc("123123123")
               .build();

    }

    @Test
    void createNewBeer() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(getTestBeer()));
    }

}