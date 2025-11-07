package com.tricol.service.impl;

import com.tricol.entity.Fournisseur;
import com.tricol.repository.FournisseurRepository;
import com.tricol.service.FournisseurServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class FournisseurService implements FournisseurServiceInterface {
    private FournisseurRepository fournisseurRepository;

    public void setRepository(FournisseurRepository repository) {
        this.fournisseurRepository = repository;
    }

    @Override
    public Fournisseur saveFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur updateFournisseur(Long id, Fournisseur fournisseur) {
        Optional<Fournisseur> optional = fournisseurRepository.findById(id);
        if (optional.isPresent()) {
            Fournisseur existFournisseur = optional.get();
            if (fournisseur.getAdresse() != null) existFournisseur.setAdresse(fournisseur.getAdresse());
            if (fournisseur.getContact() != null) existFournisseur.setContact(fournisseur.getContact());
            if (fournisseur.getEmail() != null) existFournisseur.setEmail(fournisseur.getEmail());
            if (fournisseur.getIce() != null) existFournisseur.setIce(fournisseur.getIce());
            if (fournisseur.getSociete() != null) existFournisseur.setSociete(fournisseur.getSociete());
            if (fournisseur.getTelephone() != null) existFournisseur.setTelephone(fournisseur.getTelephone());
            if (fournisseur.getVille() != null) existFournisseur.setVille(fournisseur.getVille());
            return fournisseurRepository.save(existFournisseur);
        } else {
            throw new RuntimeException("Fournisseur not found with id " + id);
        }
    }

    @Override
    public void deleteFournisseur(Long id) {
        if (fournisseurRepository.findById(id).isPresent()) {
            fournisseurRepository.deleteById(id);
        } else {
            throw new RuntimeException("Fournisseur not found with id " + id);
        }
    }

    @Override
    public List<Fournisseur> findAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Optional<Fournisseur> findFournisseurById(Long id){
        return fournisseurRepository.findById(id);
    }

    @Override
    public List<Fournisseur> sortFournisseursBySociety(){
         return fournisseurRepository.findAll(Sort.by(Sort.Direction.ASC, "societe"));
    }
}
