package com.ensa.jibi.service;

import com.ensa.jibi.cmi.CmiService;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.model.Transaction;
import com.ensa.jibi.model.TransactionType;
import com.ensa.jibi.repository.ClientRepository;
import com.ensa.jibi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository TransactionRepository;
    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private CmiService cmiService;





    @Autowired
    public TransactionService(TransactionRepository TransactionRepository, ClientRepository clientRepository) {
        this.TransactionRepository = TransactionRepository;
        this.clientRepository = clientRepository;

    }
    public List<Transaction> getAllTransactions() {
        return TransactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return TransactionRepository.findById(id);
    }

    public Transaction saveOrUpdateTransaction(Transaction Transaction) {
        return TransactionRepository.save(Transaction);
    }

    public void deleteTransactionById(Long id) {
        TransactionRepository.deleteById(id);
    }


    public List<Transaction> getTransactionsByClientId(Long clientId) {
        return TransactionRepository.getTransactionsByIdClient(clientId);
    }

    public Long getTodayMontantSum(Long id) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        Long sum=TransactionRepository.findSumOfAllTransactionsToday(startOfDay,endOfDay,id);
        if(sum==null) return 0L;
        return sum;
    }

    public void effectuerTransfert(double montant, Long senderId, Long recieverId) {

        Optional<Client> sdr = clientRepository.findById(senderId);
        Optional<Client> rcvr = clientRepository.findById(recieverId);


        if (sdr.isPresent() && rcvr.isPresent()) {
            Client sender = sdr.get();
            Client reciever = rcvr.get();

            if (isBelowLimit(montant, sender)) {
                if (cmiService.istransfer(montant,senderId,recieverId)) {

                    Transaction transaction = new Transaction();
                    transaction.setIdClient((sender.getId()));
                    transaction.setMontant(montant);
                    transaction.setType(TransactionType.SORTIE);
                    LocalDateTime currentDate = LocalDateTime.now();
                    transaction.setDate(currentDate);
                    transaction.setBeneficaire(reciever.getPhone());
                    transaction.setLibelle("Transfert en faveur de "+reciever.getFirstname()+" "+reciever.getLastname()+"de valeur"+montant+" MAD");
                    TransactionRepository.save(transaction);

                    Transaction transaction1 = new Transaction();
                    transaction1.setIdClient((reciever.getId()));
                    transaction1.setMontant(montant);
                    transaction1.setType(TransactionType.ENTREE);
                    LocalDateTime currentDate1 = LocalDateTime.now();
                    transaction1.setDate(currentDate1);
                    transaction1.setBeneficaire(sender.getPhone());
                    transaction1.setLibelle("Transfert recu de la part de "+reciever.getFirstname()+" "+reciever.getLastname()+"de valeur"+montant+" MAD");
                    TransactionRepository.save(transaction1);

                } else {
                    throw new RuntimeException("Insufficient balance for client ID: " + senderId);
                }
            } else {
                throw new RuntimeException("Transaction amount exceeds limit for client ID: " + senderId);
            }
        } else {
            throw new IllegalArgumentException("Sender i same as Reciever or not found for IDs: " + senderId + ", " + recieverId);
        }
    }

    public boolean isBelowLimit(Double montant, Client client) {
        Double limit = client.getAccountType().getAccountLimit();
        return getTodayMontantSum(client.getId()) + montant < limit;
    }
}
