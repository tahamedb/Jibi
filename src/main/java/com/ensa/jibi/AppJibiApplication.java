package com.ensa.jibi;

import com.ensa.jibi.config.TwilioConfig;
import com.ensa.jibi.model.*;
import com.ensa.jibi.repository.*;
import com.ensa.jibi.service.DonnationService;
import com.ensa.jibi.service.TransactionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.ValidationRequest;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class AppJibiApplication implements CommandLineRunner {
	@Autowired
	CreancierRepository creancierRepository;
	@Autowired
	private CreanceRepository creanceRepository;
	@Autowired
    private RechargeRepository rechargeRepository;
	@Autowired
	private  BackOfficeRepository backOfficeRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	TransactionService transactionService;

	@Autowired
	DonnationRepository donnationRepository;

	@Autowired
	FactureRepository factureRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private TwilioConfig twilioConfig;
	private String accountSid;

	@Value("${twilio.auth.token}")
	private String authToken;

	@PostConstruct
	public void setup(){

	}


	public static void main(String[] args) {
		SpringApplication.run(AppJibiApplication.class, args);
	}
	private void generateMockTransactions(int n) {
		List<Creance> creances = creanceRepository.findAll();
		Random random = new Random();

		for (int i = 0; i < n; i++) {
			Transaction transaction = new Transaction();
			transaction.setIdClient(123L); // For testing purposes, using a fixed idClient
			transaction.setDate(LocalDateTime.now());
			transaction.setMontant(random.nextDouble() * 1000); // Random amount between 0 and 1000

			// Randomly select a creance
			Creance creance = creances.get(random.nextInt(creances.size()));

			// Generate libelle using creancier and creance name
			String libelle = String.format("%s - %s", creance.getCreancier().getName(), creance.getName());
			transaction.setLibelle(libelle);

			// Set transaction type
			transaction.setType(random.nextBoolean() ? TransactionType.ENTREE : TransactionType.SORTIE);

			// Set beneficiary as one of the creancier names
			List<Creancier> creanciers = creancierRepository.findAll();
			Creancier beneficiary = creanciers.get(random.nextInt(creanciers.size()));
			transaction.setBeneficaire(beneficiary.getName());

			// Save the transaction
			transactionService.saveOrUpdateTransaction(transaction);
		}
	}
	@Override
	public void run(String... args) throws Exception {
		//BackOffice a = new BackOffice();
		//a.setBackOffice_id(Long.parseLong("8"));
		//a.setEmail("lalos@gmail.com");
		//a.setUsername("lolos");
		//a.setPassword(passwordEncoder.encode("123"));
		//a.setFirstName("sbaxi");
		//a.setLastName("sbaxi");
		//backOfficeRepository.save(a);

		}

	}


