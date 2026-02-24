package com.example;

import com.example.model.*;
import com.example.repository.*;
import com.example.service.*;
import com.example.util.PaginationResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestion-reservations");
        EntityManager em = emf.createEntityManager();

        try {

            SalleRepository salleRepository = new SalleRepositoryImpl(em);
            SalleService salleService = new SalleServiceImpl(em, salleRepository);

            initializeTestData(em);

            System.out.println("\n=== TEST SALLES DISPONIBLES ===");
            testAvailableRooms(salleService);

            System.out.println("\n=== TEST RECHERCHE MULTI-CRITÈRES ===");
            testMultiCriteriaSearch(salleService);

            System.out.println("\n=== TEST PAGINATION ===");
            testPagination(salleService);

        } finally {
            em.close();
            emf.close();
        }
    }

    private static void initializeTestData(EntityManager em) {

        em.getTransaction().begin();

        Equipement projecteur =
                new Equipement("Projecteur", "Projecteur HD");
        Equipement ecran =
                new Equipement("Écran interactif", "Écran tactile 65 pouces");

        em.persist(projecteur);
        em.persist(ecran);

        Utilisateur user =
                new Utilisateur("Dupont", "Jean", "jean@example.com");

        em.persist(user);

        Salle salle1 = new Salle("Salle A101", 30);
        salle1.setBatiment("Bâtiment A");
        salle1.setEtage(1);
        salle1.addEquipement(projecteur);

        Salle salle2 = new Salle("Salle B202", 20);
        salle2.setBatiment("Bâtiment B");
        salle2.setEtage(2);
        salle2.addEquipement(ecran);

        em.persist(salle1);
        em.persist(salle2);

        LocalDateTime now = LocalDateTime.now();

        Reservation reservation =
                new Reservation(
                        now.plusDays(1),
                        now.plusDays(1).plusHours(2),
                        "Réunion équipe"
                );

        reservation.setSalle(salle1);
        reservation.setUtilisateur(user);

        em.persist(reservation);

        em.getTransaction().commit();
    }

    private static void testAvailableRooms(SalleService salleService) {

        LocalDateTime now = LocalDateTime.now();

        List<Salle> salles =
                salleService.findAvailableRooms(
                        now.plusDays(1),
                        now.plusDays(1).plusHours(2)
                );

        for (Salle s : salles) {
            System.out.println(s.getNom());
        }
    }

    private static void testMultiCriteriaSearch(SalleService salleService) {

        Map<String, Object> criteria = new HashMap<>();
        criteria.put("capaciteMin", 20);

        List<Salle> result = salleService.searchRooms(criteria);

        for (Salle s : result) {
            System.out.println(s.getNom() + " - " + s.getCapacite());
        }
    }

    private static void testPagination(SalleService salleService) {

        int pageSize = 1;

        int totalPages = salleService.getTotalPages(pageSize);

        for (int page = 1; page <= totalPages; page++) {

            List<Salle> salles =
                    salleService.getPaginatedRooms(page, pageSize);

            for (Salle s : salles) {
                System.out.println("Page " + page + " : " + s.getNom());
            }
        }

        PaginationResult<Salle> result =
                new PaginationResult<>(
                        salleService.getPaginatedRooms(1, pageSize),
                        1,
                        pageSize,
                        salleService.getAllRooms().size()
                );

        System.out.println("Total pages : " + result.getTotalPages());
    }
}