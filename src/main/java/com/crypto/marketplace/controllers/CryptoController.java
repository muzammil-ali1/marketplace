package com.crypto.marketplace.controllers;

import com.crypto.marketplace.commands.CryptoForm;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.domain.TransactionType;
import com.crypto.marketplace.services.CryptoService;
import com.crypto.marketplace.utils.CryptoFormToCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create By Muzammil on 10/06/2020.
 */
@RestController
public class CryptoController {
    private final CryptoService cryptoService;
    private final CryptoFormToCrypto cryptoFormToCrypto;

    @Autowired
    public CryptoController(CryptoService cryptoService,
                            CryptoFormToCrypto cryptoFormToCrypto) {
        this.cryptoService = cryptoService;
        this.cryptoFormToCrypto = cryptoFormToCrypto;
    }

    @GetMapping(path = "/crypto/show/{transactionType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Crypto> getCrypto(@PathVariable String transactionType) {
        /**
         * Only applied group by on price per coin only as there was no requirement defined
         * to set make a check on Coin Type. Else I could do that in the same stream.
         */

        return cryptoService.getByTransactionType(TransactionType.valueOf(transactionType));
    }

    @PostMapping(path = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Crypto save(@RequestBody CryptoForm cryptoForm) {
        return cryptoService.save(cryptoFormToCrypto.convert(cryptoForm));
    }

    @DeleteMapping(path = "/crypto/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        cryptoService.delete(Long.valueOf(id));
    }


}
