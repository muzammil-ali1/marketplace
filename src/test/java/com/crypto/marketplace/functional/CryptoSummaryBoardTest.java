package com.crypto.marketplace.functional;

import com.crypto.marketplace.commands.CryptoForm;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.domain.TransactionType;
import com.crypto.marketplace.repositories.CryptoRepository;
import com.crypto.marketplace.utils.CryptoFormToCrypto;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.crypto.marketplace.domain.CoinType.Ethereum;
import static com.crypto.marketplace.domain.CoinType.Litecoin;
import static com.crypto.marketplace.domain.TransactionType.BUY;
import static com.crypto.marketplace.domain.TransactionType.SELL;
import static org.junit.Assert.assertEquals;

public class CryptoSummaryBoardTest extends AbstractTest {

    @Autowired
    CryptoFormToCrypto cryptoFormToCrypto;

    @Autowired
    CryptoRepository cryptoRepository;

    @After
    public void cleanup() {
        cryptoRepository.deleteAll();
    }

    @Test
    public void successfullyPlacedOrder() throws Exception {
        CryptoForm form = new CryptoForm(10L, Ethereum.name(), 10.0, 4.5, BUY.name());

        String uri = "/order";
        String inputJson = super.mapToJson(form);
        System.out.println("inputJson = " + inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Crypto expected = mapFromJson(content, Crypto.class);

        Crypto actual = cryptoRepository.findOne(expected.getId());

        assertEquals(expected, actual);
//        assertEquals(expected.getPricePerCoin(), form.getPricePerCoin());
//        assertEquals(expected.getTransactionType(), form.getTransactionType());
    }

    @Test
    public void givenABuyTransaction_summaryShouldShowIt() throws Exception {
        cryptoRepository.save(new Crypto(101L, Ethereum, 10.0, 4.5, BUY));

        List<Crypto> cryptos = getResponseFromSummaryBoard(BUY);

        assertEquals(1, cryptos.size());
        assertEquals(new Crypto(10.0, 4.5, BUY), cryptos.get(0));
    }

    @Test
    public void givenTransactions_shouldDisplayBuyInOrder() throws Exception {
        cryptoRepository.save(new Crypto(10L, Ethereum, 10.0, 4.5, BUY));
        cryptoRepository.save(new Crypto(20L, Litecoin, 30.0, 10.5, BUY));
        cryptoRepository.save(new Crypto(10L, Ethereum, 10.0, 4.5, SELL));

        List<Crypto> cryptos = getResponseFromSummaryBoard(BUY);

        assertEquals(2, cryptos.size());
        assertEquals(new Crypto(30.0, 10.5, BUY), cryptos.get(0));
        assertEquals(new Crypto( 10.0, 4.5, BUY), cryptos.get(1));
    }

    @Test
    public void givenTransactions_shouldDisplaySellInOrder() throws Exception {
        cryptoRepository.save(new Crypto(10L, Ethereum, 10.0, 4.5, BUY));
        cryptoRepository.save(new Crypto(20L, Litecoin, 30.0, 10.5, SELL));
        cryptoRepository.save(new Crypto(10L, Ethereum, 10.0, 7.5, SELL));

        List<Crypto> cryptos = getResponseFromSummaryBoard(SELL);

        assertEquals(2, cryptos.size());
        assertEquals(new Crypto(10.0, 7.5, SELL), cryptos.get(0));
        assertEquals(new Crypto(30.0, 10.5, SELL), cryptos.get(1));
    }


    @Test
    public void givenTransactions_shouldAggregateInOrder() throws Exception {
        cryptoRepository.save(new Crypto(10L, Ethereum, 10.0, 4.5, BUY));
        cryptoRepository.save(new Crypto(20L, Ethereum, 20.0, 4.5, BUY));
        cryptoRepository.save(new Crypto(30L, Ethereum, 30.0, 4.5, SELL));
        cryptoRepository.save(new Crypto(20L, Litecoin, 25.0, 5.5, BUY));

        List<Crypto> cryptos = getResponseFromSummaryBoard(BUY);

        assertEquals(2, cryptos.size());
        assertEquals(new Crypto( 25.0, 5.5, BUY), cryptos.get(0));
        assertEquals(new Crypto(30.0, 4.5, BUY), cryptos.get(1));
    }

    @Test
    public void givenTransaction_whenCancelled_removedFromSummaryBoard() throws Exception {
        Crypto c1 = cryptoRepository.save(new Crypto(10L, Ethereum, 10.0, 4.5, BUY));
        cryptoRepository.save(new Crypto(20.0, 4.5, BUY));
        cryptoRepository.save(new Crypto(30.0, 4.5, SELL));

        //WHEN
        String uri = "/crypto/cancel/" + c1.getId();
        mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        List<Crypto> cryptos = getResponseFromSummaryBoard(BUY);

        assertEquals(1, cryptos.size());
        assertEquals(new Crypto(20.0, 4.5, BUY), cryptos.get(0));
    }

    private List<Crypto> getResponseFromSummaryBoard(TransactionType transactionType) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/crypto/show/" + transactionType)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        return listFromJson(content, Crypto.class);
    }
}
