package com.crypto.marketplace.repositories;

import java.util.List;

import com.crypto.marketplace.domain.Crypto;
import com.crypto.marketplace.domain.TransactionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Create By Muzammil on 10/06/2020.
 */
public interface CryptoRepository extends CrudRepository<Crypto, Long> {
    /*
     * I could use CrudRepsitory Naming Convention but, for that had to use Paging or Sorting
     * implementations for a simple task
     */
    @Query( "FROM Crypto WHERE transactionType = :transactionType" )
    List<Crypto> findByTransactionType(@Param( "transactionType" ) TransactionType transactionType);
}
