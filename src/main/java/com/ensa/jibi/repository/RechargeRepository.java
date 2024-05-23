package com.ensa.jibi.repository;

import com.ensa.jibi.model.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeRepository extends JpaRepository<Recharge, Long> {
    // You can add custom query methods here if needed
}
