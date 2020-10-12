package com.crypto.marketplace.unit.controllers;

import com.crypto.marketplace.controllers.CryptoController;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.repositories.CryptoRepository;
import com.crypto.marketplace.services.CryptoService;
import com.crypto.marketplace.services.CryptoServiceImpl;
import com.crypto.marketplace.utils.CryptoFormToCrypto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static com.crypto.marketplace.domain.CoinType.Ethereum;
import static com.crypto.marketplace.domain.CoinType.Litecoin;
import static com.crypto.marketplace.domain.TransactionType.BUY;
import static com.crypto.marketplace.domain.TransactionType.SELL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CryptoTransactionTest {

    public static final String SORT_FIELD_NAME = "pricePerCoin";
    @Mock
    private CryptoRepository cryptoRepository;
    private final CryptoFormToCrypto cryptoFormToCrypto = new CryptoFormToCrypto();
    @InjectMocks
    private final CryptoService cryptoService = new CryptoServiceImpl(cryptoRepository);
    private final CryptoController controller = new CryptoController(cryptoService, cryptoFormToCrypto);

    @Test
    public void givenBuyTransactions_returnAllInAscendingOrder() {
        //GIVEN
        Crypto buy1 = new Crypto(10L, Ethereum, 10.0, 120.0, BUY);
        Crypto buy2 = new Crypto(20L, Ethereum, 30.0, 125.0, BUY);
        Crypto buy3 = new Crypto(20L, Ethereum, 10.0, 122.5, BUY);

        List<Crypto> provided = Arrays.asList(buy2, buy1, buy3);
        Sort sort = new Sort(Sort.Direction.DESC, "pricePerCoin");
        when(cryptoRepository.findByTransactionType(BUY)).thenReturn(provided);

        //WHEN
        List<Crypto> cryptos = controller.getCrypto("BUY");

        //THEN
        assertEquals(3, cryptos.size());
        assertEquals(cryptos.get(0), buy3);
        assertEquals(cryptos.get(1), buy1);
        assertEquals(cryptos.get(2), buy2);
    }

    @Test
    public void givenSamePriceBuyTransaction_returnedAggregateByPrice() {
        //GIVEN
        Crypto b1 = new Crypto(20L, Litecoin, 10.0, 122.0, BUY);
        Crypto b2 = new Crypto(30L, Litecoin, 35.5, 122.0, BUY);
        Crypto b3 = new Crypto(20L, Ethereum, 12.5, 124.0, BUY);
        Crypto b4 = new Crypto(10L, Ethereum, 10.5, 120.0, BUY);
        Crypto aggregate = new Crypto(45.5, 122.0, BUY);

        List<Crypto> provided = Arrays.asList(b1,b2,b3,b4);
        Sort sort = new Sort(Sort.Direction.DESC, "pricePerCoin");
        when(cryptoRepository.findByTransactionType(BUY)).thenReturn(provided);

        //WHEN
        List<Crypto> cryptos = controller.getCrypto("BUY");

        //THEN
        assertEquals(3, cryptos.size());
        assertEquals(cryptos.get(0), b4);
        assertEquals(cryptos.get(1), aggregate);
        assertEquals(cryptos.get(2), b3);
    }

    @Test
    public void givenSamePriceSellTransaction_returnedAggregateByPrice() {
        //GIVEN
        Crypto b1 = new Crypto(20L, Litecoin, 10.0, 122.0, SELL);
        Crypto b2 = new Crypto(30L, Ethereum, 35.5, 122.0, SELL);
        Crypto b3 = new Crypto(20L, Ethereum, 12.5, 124.0, SELL);
        Crypto b4 = new Crypto(10L, Ethereum, 10.5, 120.0, SELL);
        Crypto aggregate = new Crypto(45.5, 122.0, SELL);

        List<Crypto> provided = Arrays.asList(b1,b2,b3,b4);
        Sort sort = new Sort(Sort.Direction.ASC, SORT_FIELD_NAME);
        when(cryptoRepository.findByTransactionType(SELL)).thenReturn(provided);

        //WHEN
        List<Crypto> cryptos = controller.getCrypto("SELL");

        //THEN
        assertEquals(3, cryptos.size());
        assertEquals(cryptos.get(0), b4);
        assertEquals(cryptos.get(1), aggregate);
        assertEquals(cryptos.get(2), b3);
    }

}