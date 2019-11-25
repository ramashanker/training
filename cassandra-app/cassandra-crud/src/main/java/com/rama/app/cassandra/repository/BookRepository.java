package com.rama.app.cassandra.repository;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends MapIdCassandraRepository<Book> {

    @Query
    Optional<Book> findByIsbn(String isbn);

    @Query("update book set price = :price where isbn = :isbn")
    Boolean setPriceForBook(@Param("price") boolean active, @Param("isbn") String vin);
}
