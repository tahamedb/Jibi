package com.ensa.jibi.service;

import com.ensa.jibi.model.Recharge;
import com.ensa.jibi.repository.RechargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RechargeService {

    @Autowired
    private RechargeRepository rechargeRepository;

    public List<Recharge> getAllRecharges() {
        return rechargeRepository.findAll();
    }

    public Optional<Recharge> getRechargeById(Long id) {
        return rechargeRepository.findById(id);
    }

    public Recharge saveOrUpdateRecharge(Recharge recharge) {
        return rechargeRepository.save(recharge);
    }

    public void deleteRechargeById(Long id) {
        rechargeRepository.deleteById(id);
    }
}
