package com.crypto.marketplace.utils;

import com.crypto.marketplace.commands.CryptoForm;
import com.crypto.marketplace.domain.Crypto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Create By Muzammil on 10/06/2020.
 */
@Component
public class CryptoToCryptoForm implements Converter<Crypto, CryptoForm> {
    @Override
    public CryptoForm convert( Crypto crypto ) {
        CryptoForm cryptoForm = new CryptoForm();
        cryptoForm.setUserID( crypto.getUserId() );
        cryptoForm.setCoinType( crypto.getCoinType().name() );
        cryptoForm.setPricePerCoin( crypto.getPricePerCoin() );
        cryptoForm.setOrderQty( crypto.getOrderQty() );
        cryptoForm.setTransactionType( crypto.getTransactionType().name() );
        return cryptoForm;
    }
}
