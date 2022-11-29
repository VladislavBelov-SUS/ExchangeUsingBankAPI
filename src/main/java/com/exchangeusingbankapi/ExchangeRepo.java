package com.exchangeusingbankapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExchangeRepo extends JpaRepository<ExchangeRate,Long> {
    Optional<ExchangeRate> findByDate(Date date);

    @Query("select e from ExchangeRate e where e.date >= :dateFrom and e.date <= :dateTo")
    List<ExchangeRate> findByDate(Date dateFrom, Date dateTo);
}
