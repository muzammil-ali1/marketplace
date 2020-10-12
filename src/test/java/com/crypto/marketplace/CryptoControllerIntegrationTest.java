package com.crypto.marketplace;

import com.crypto.marketplace.controllers.CryptoController;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.repositories.CryptoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.crypto.marketplace.domain.CoinType.Ethereum;
import static com.crypto.marketplace.domain.TransactionType.BUY;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketplaceApplication.class)
public class CryptoControllerIntegrationTest {

    @Autowired
    private CryptoRepository repository;

    @Autowired
    private CryptoController controller;


    @Test
    public void givenCryptoBuy_whenSaveAndFind_thenOK() {
        Crypto c1 = new Crypto(10L, Ethereum, 10.0, 120.0, BUY);

        Crypto saved = repository.save(c1);

//        List<Crypto> cryptos = controller.get;
//        assertNotNull(cryptos);
//        assertEquals(cryptos.iterator().next(), saved);
    }

}