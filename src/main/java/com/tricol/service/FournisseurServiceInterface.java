package com.tricol.service;

import com.tricol.entity.Fournisseur;

import java.util.List;
import java.util.Optional;

public interface FournisseurServiceInterface {
    Fournisseur saveFournisseur(Fournisseur fournisseur);

    Fournisseur updateFournisseur(Long id, Fournisseur fournisseur);

    void deleteFournisseur(Long id);

    List<Fournisseur> findAllFournisseurs();

    Optional<Fournisseur> findFournisseurById(Long id);

    List<Fournisseur> sortFournisseursBySociety();
}
