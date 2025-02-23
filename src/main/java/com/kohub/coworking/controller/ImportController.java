package com.kohub.coworking.controller;

import com.kohub.coworking.model.Option;
import com.kohub.coworking.model.Espace;
import com.kohub.coworking.model.Reservation;
import com.kohub.coworking.model.ReservationDetail;
import com.kohub.coworking.model.Paiement;
import com.kohub.coworking.repository.OptionRepository;
import com.kohub.coworking.repository.EspaceRepository;
import com.kohub.coworking.repository.ReservationRepository;
import com.kohub.coworking.repository.UserRepository;
import com.kohub.coworking.repository.PaiementRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.RequiredArgsConstructor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;


@Controller
@RequiredArgsConstructor
public class ImportController {

    private final OptionRepository optionRepository;
    private final EspaceRepository espaceRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PaiementRepository paiementRepository;

    @GetMapping("/import")
    public String showImportPage() {
        return "import";
    }

    @PostMapping("/import")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("type") String type,
                                 RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner un fichier");
            return "redirect:/import";
        }

        int importCount = 0;  // Compteur pour les éléments importés

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            // Skip header
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;  // Ignorer les lignes vides
                
                String[] data = line.split(",");
                switch (type) {
                    case "options":
                        processOptionData(data);
                        importCount++;
                        break;
                    case "users":
                        processUserData(data);
                        importCount++;
                        break;
                    case "espaces":
                        processEspaceData(data);
                        importCount++;
                        break;
                    case "reservations":
                        processReservationData(data);
                        importCount++;
                        break;
                    case "paiements":
                        processPaiementData(data);
                        importCount++;
                        break;
                }
            }
            
            if (importCount == 0) {
                redirectAttributes.addFlashAttribute("warning", 
                    "Aucune donnée n'a été importée du fichier " + file.getOriginalFilename());
            } else {
                redirectAttributes.addFlashAttribute("success", 
                    "Import réussi : " + importCount + " éléments importés du fichier " + file.getOriginalFilename());
            }
            
        } catch (Exception e) {
            e.printStackTrace(); // Pour le debugging
            redirectAttributes.addFlashAttribute("error", 
                "Erreur lors de l'import: " + e.getMessage());
        }
        
        return "redirect:/import";
    }

    private void processOptionData(String[] data) {
        if (data.length < 3) {
            throw new IllegalArgumentException("Format incorrect : l'option nécessite au moins 3 colonnes");
        }
        
        try {
            String code = data[0].trim();
            String name = data[1].trim();
            double price = Double.parseDouble(data[2].trim());

            if (code.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException("Le code et le nom de l'option sont obligatoires");
            }

            Option option = new Option();
            option.setCode(code);
            option.setName(name);
            option.setPrice(price);
            
            Option savedOption = optionRepository.findByCodeIgnoreCase(option.getCode())
                .map(existingOption -> {
                    existingOption.setName(option.getName());
                    existingOption.setPrice(option.getPrice());
                    return optionRepository.save(existingOption);
                })
                .orElseGet(() -> optionRepository.save(option));

            if (savedOption == null || savedOption.getCode() == null) {
                throw new RuntimeException("Échec de l'enregistrement de l'option: " + code);
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format de prix invalide pour l'option: " + data[0]);
        }
    }

    private void processUserData(String[] data) {
        // Implémenter le traitement des données utilisateur
    }

    private void processEspaceData(String[] data) {
        if (data.length < 2) {
            throw new IllegalArgumentException("Format incorrect : l'espace nécessite au moins 2 colonnes");
        }
        
        try {
            String nom = data[0].trim();
            double prix = Double.parseDouble(data[1].trim());

            if (nom.isEmpty()) {
                throw new IllegalArgumentException("Le nom de l'espace est obligatoire");
            }

            Espace espace = new Espace();
            espace.setName(nom);
            espace.setPrice(prix);
            
            Espace savedEspace = espaceRepository.findByName(espace.getName())
                .map(existingEspace -> {
                    existingEspace.setPrice(espace.getPrice());
                    return espaceRepository.save(existingEspace);
                })
                .orElseGet(() -> espaceRepository.save(espace));

            if (savedEspace == null || savedEspace.getName() == null) {
                throw new RuntimeException("Échec de l'enregistrement de l'espace: " + nom);
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format de prix invalide pour l'espace: " + data[0]);
        }
    }

    private void processReservationData(String[] data) {
        if (data.length < 7) {
            throw new IllegalArgumentException("Format incorrect : la réservation nécessite au moins 7 colonnes");
        }

        try {
            // Extraire d'abord la chaîne d'options (dernière colonne)
            String optionsStr = data[6].trim();
            Set<Option> optionsToAdd = new HashSet<>();

            System.out.println("\n=== Traitement de la réservation ===");
            System.out.println("Options brutes: " + optionsStr);

            // Traiter les options en premier
            if (!optionsStr.isEmpty()) {
                // Extraire les codes d'options, qu'ils soient entre guillemets ou non
                Set<String> optionCodes;
                if (optionsStr.startsWith("\"") || optionsStr.startsWith("'")) {
                    // Options entre guillemets - enlever les guillemets externes puis split sur "- "
                    optionCodes = Arrays.stream(optionsStr.substring(1, optionsStr.length() - 1).split("-"))
                        .map(String::trim)
                        .filter(code -> !code.isEmpty())
                        .collect(Collectors.toSet());
                } else {
                    // Options sans guillemets
                    optionCodes = Arrays.stream(optionsStr.split("-"))
                        .map(String::trim)
                        .filter(code -> !code.isEmpty())
                        .collect(Collectors.toSet());
                }

                System.out.println("Options détectées: " + optionCodes);

                // Vérifier et collecter toutes les options valides
                for (String optionCode : optionCodes) {
                    try {
                        Option option = optionRepository.findByCodeIgnoreCase(optionCode)
                            .orElseThrow(() -> new IllegalArgumentException("Option non trouvée: " + optionCode));
                        optionsToAdd.add(option);
                        System.out.println("Option trouvée: " + option.getCode() + " - " + option.getName());
                    } catch (Exception e) {
                        System.err.println("Erreur lors du traitement de l'option '" + optionCode + "': " + e.getMessage());
                    }
                }
            }

            // Traiter ensuite les autres données
            String ref = data[0].trim();
            String espaceName = data[1].trim();
            String numClient = data[2].trim();
            LocalDate dateReservation = LocalDate.parse(data[3].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime heureDebut = LocalTime.parse(data[4].trim(), DateTimeFormatter.ofPattern("HH:mm"));
            int duree = Integer.parseInt(data[5].trim());

            System.out.println("Référence: " + ref);

            // Créer ou mettre à jour la réservation
            Reservation reservation = reservationRepository.findByRef(ref)
                .orElse(new Reservation());
            
            reservation.setRef(ref);
            reservation.setEspace(espaceRepository.findByName(espaceName)
                .orElseThrow(() -> new IllegalArgumentException("Espace non trouvé: " + espaceName)));
            reservation.setUser(userRepository.findByNumber(numClient)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé: " + numClient)));
            reservation.setDateReservation(dateReservation);
            reservation.setHeureDebut(heureDebut);
            reservation.setDuree(duree);

            // Sauvegarder d'abord la réservation
            reservation = reservationRepository.save(reservation);

            // Nettoyer les anciennes options
            reservation.getOptions().clear();
            reservationRepository.save(reservation);

            // Ajouter les nouvelles options
            for (Option option : optionsToAdd) {
                ReservationDetail detail = new ReservationDetail();
                detail.setReservation(reservation);
                detail.setOption(option);
                reservation.addOption(detail);
            }

            // Sauvegarder la réservation avec ses options
            reservation = reservationRepository.save(reservation);
            System.out.println("Réservation sauvegardée avec " + reservation.getOptions().size() + " option(s)");

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date ou heure invalide", e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format de durée invalide", e);
        }
    }

    private void processPaiementData(String[] data) {
        if (data.length < 3) {
            throw new IllegalArgumentException("Format incorrect : le paiement nécessite 3 colonnes");
        }

        try {
            String refPaiement = data[0].trim();
            String refReservation = data[1].trim();
            LocalDate datePaiement = LocalDate.parse(data[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.println("\n=== Traitement du paiement ===");
            System.out.println("Référence paiement: " + refPaiement);
            System.out.println("Référence réservation: " + refReservation);

            // Vérifier si la réservation existe
            Reservation reservation = reservationRepository.findByRef(refReservation)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée: " + refReservation));

            // Créer ou mettre à jour le paiement
            Paiement paiement = paiementRepository.findByRefPaiement(refPaiement)
                .orElse(new Paiement());

            paiement.setRefPaiement(refPaiement);
            paiement.setReservation(reservation);
            paiement.setDatePaiement(datePaiement);

            // Sauvegarder le paiement
            paiement = paiementRepository.save(paiement);
            System.out.println("Paiement sauvegardé: " + paiement.getRefPaiement());

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide", e);
        }
    }
} 