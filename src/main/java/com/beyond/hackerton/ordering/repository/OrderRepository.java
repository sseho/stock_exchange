package com.beyond.hackerton.ordering.repository;

import com.beyond.hackerton.ordering.domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Long> {
}
