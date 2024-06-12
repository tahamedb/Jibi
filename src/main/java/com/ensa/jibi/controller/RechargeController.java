package com.ensa.jibi.controller;



import com.ensa.jibi.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recharges")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @PostMapping("/payer")
    public ResponseEntity<String> payerRecharge(@RequestParam Long rechargeId, @RequestParam Long clientId) {
        try {
            rechargeService.PayerRecharge(rechargeId, clientId);
            return ResponseEntity.ok("Recharge paid successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}
