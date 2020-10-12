package com.crypto.marketplace.services;

import java.util.List;

import com.crypto.marketplace.commands.CryptoForm;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.domain.TransactionType;

/**
 * Create By Muzammil on 10/06/2020.
 */
public interface CryptoService {
    
    Crypto save(Crypto crypto );
    
    void delete( Long id );
    
    List<Crypto> getByTransactionType(TransactionType transactionType );
    
}
