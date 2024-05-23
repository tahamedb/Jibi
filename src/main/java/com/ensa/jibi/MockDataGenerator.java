package com.ensa.jibi;

import com.ensa.jibi.model.*;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockDataGenerator {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static List<Creancier> generateMockCreanciers(int count) {
        List<Creancier> creanciers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Creancier creancier = new Creancier();
            creancier.setCategorieCreancier(randomEnum(CreancierType.class));
            creancier.setName(faker.company().name());
            creancier.setLogo(faker.internet().avatar());
            creancier.setCreances(generateMockCreances(creancier, random.nextInt(5) + 1));
            creanciers.add(creancier);
        }
        return creanciers;
    }

    public static List<Creance> generateMockCreances(Creancier creancier, int count) {
        List<Creance> creances = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Creance creance = new Creance();
            creance.setName(faker.commerce().productName());
            creance.setCreancetype(randomEnum(CreanceType.class));
            creance.setCreancier(creancier);
            creances.add(creance);
        }
        return creances;
    }

    public static List<Transaction> generateMockTransactions(int count) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setIdClient((long) faker.number().numberBetween(1, 100));
            transaction.setLibelle(faker.commerce().productName());
            transaction.setDate(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));
            transaction.setMontant(faker.number().randomDouble(2, 10, 1000));
            transaction.setType(randomEnum(TransactionType.class));
            transaction.setBeneficaire(faker.name().fullName());
            transactions.add(transaction);
        }
        return transactions;
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    public static void main(String[] args) {
        List<Creancier> creanciers = generateMockCreanciers(10);

        // Print out the generated mock data
        for (Creancier creancier : creanciers) {
            System.out.println("Creancier: " + creancier.getName() + ", Type: " + creancier.getCategorieCreancier());
            for (Creance creance : creancier.getCreances()) {
                System.out.println("  Creance: " + creance.getName() + ", Type: " + creance.getCreancetype());
            }
        }
    }
}
