package com.crypto.marketplace.utils;

import com.crypto.marketplace.commands.CryptoForm;
import com.crypto.marketplace.domain.CoinType;
import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.domain.TransactionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Create By Muzammil on 10/06/2020.
 */
@Component
public class CryptoFormToCrypto implements Converter<CryptoForm, Crypto> {
    
    @Override
    public Crypto convert( CryptoForm cryptoForm ) {
        Crypto crypto = new Crypto();
        if ( cryptoForm.getUserID() != null && !StringUtils.isEmpty( cryptoForm.getUserID() ) ) {
            crypto.setUserId( cryptoForm.getUserID() );
        }
        crypto.setCoinType(CoinType.valueOf(cryptoForm.getCoinType()));
        crypto.setPricePerCoin( cryptoForm.getPricePerCoin() );
        crypto.setOrderQty( cryptoForm.getOrderQty() );
        crypto.setTransactionType(TransactionType.valueOf(cryptoForm.getTransactionType()));
        return crypto;
    }
}
