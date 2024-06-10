package com.ensa.jibi.repository;

import com.ensa.jibi.model.Recharge;
import com.ensa.jibi.model.StatusRecharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargeRepository extends JpaRepository<Recharge, Long> {
    List<Recharge> findByStatusRechargeAndPhonenumberAndCreance_Id(StatusRecharge statusRecharge, String phonenumber, Long creanceId);
}
