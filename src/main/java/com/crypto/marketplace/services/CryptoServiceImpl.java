package com.crypto.marketplace.services;

import java.util.*;
import java.util.stream.Collectors;

import com.crypto.marketplace.commands.CryptoForm;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.domain.TransactionType;
import com.crypto.marketplace.repositories.CryptoRepository;
import com.crypto.marketplace.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.crypto.marketplace.utils.CryptoFormToCrypto;

import static java.util.Collections.*;

/**
 * Create By Muzammil on 10/06/2020.
 */
@Service
public class CryptoServiceImpl implements CryptoService, Constants {

    private final CryptoRepository cryptoRepository;

    @Autowired
    public CryptoServiceImpl(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public List<Crypto> getByTransactionType(TransactionType transactionType) {

        Comparator<Crypto> sort = TransactionType.SELL.equals(transactionType)
                ? Comparator.comparing(Crypto::getPricePerCoin)
                : Comparator.comparing(Crypto::getPricePerCoin).reversed();

        return cryptoRepository.findByTransactionType(transactionType)
                .stream()
                .collect(Collectors.groupingBy(Crypto::getPricePerCoin))
                .values()
                .stream()
                .map(cryptos -> cryptos
                        .stream()
                        .reduce(this::aggregateCryptoTransactions))
                .map(Optional::get)
                .map(c -> new Crypto(c.getOrderQty(), c.getPricePerCoin(), c.getTransactionType()))
                .sorted(sort)
                .collect(Collectors.toList());
    }

    @Override
    public Crypto save(Crypto crypto) {
        return cryptoRepository.save(crypto);
    }

    @Override
    public void delete(Long id) {
        cryptoRepository.delete(id);

    }

    private Crypto aggregateCryptoTransactions(Crypto p1, Crypto p2) {
        return new Crypto(p1.getOrderQty() + p2.getOrderQty(),
                p2.getPricePerCoin(),
                p1.getTransactionType());
    }
}
