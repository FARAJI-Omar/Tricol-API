package com.tricol.controller;

import com.tricol.entity.Fournisseur;
import com.tricol.response.ResponseObject;
import com.tricol.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fournisseurs")
public class FournisseurController {
    @Autowired
    private FournisseurService fournisseurService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addFournisseur(@RequestBody Fournisseur fournisseur) {
        try {
            Fournisseur createdFournisseur = fournisseurService.saveFournisseur(fournisseur);
            ResponseObject response = new ResponseObject(
                    "Fournisseur created successfully",
                    201,
                    createdFournisseur
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject(
                    "Error creating fournisseur: " + e.getMessage(),
                    400,
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateFournisseur(@PathVariable("id") Long id, @RequestBody Fournisseur fournisseur) {
        try {
            Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(id, fournisseur);
            ResponseObject response = new ResponseObject(
                    "Fournisseur updated successfully",
                    200,
                    updatedFournisseur
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            ResponseObject response = new ResponseObject(
                    "Error updating fournisseur: " + e.getMessage(),
                    404,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteFournisseur(@PathVariable("id") Long id) {
        try {
            fournisseurService.deleteFournisseur(id);

            ResponseObject response = new ResponseObject(
                    "Fournisseur deleted successfully",
                    HttpStatus.OK.value(),
                    null
            );

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject(
                    "Fournisseur not found or could not be deleted",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllFournisseur() {
        List<Fournisseur> fournisseurs = fournisseurService.findAllFournisseurs();

        if (fournisseurs.isEmpty()) {
            ResponseObject response = new ResponseObject(
                    "No fournisseurs found",
                    HttpStatus.NO_CONTENT.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        ResponseObject response = new ResponseObject(
                "Fournisseurs retrieved successfully",
                HttpStatus.OK.value(),
                fournisseurs
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/sortBySociety")
    public ResponseEntity<ResponseObject> getFournisseursSortedBySociety() {
        List<Fournisseur> fournisseurs = fournisseurService.findAllFournisseurs();

        if (fournisseurs.isEmpty()) {
            ResponseObject response = new ResponseObject(
                    "No fournisseurs found",
                    HttpStatus.NO_CONTENT.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        ResponseObject response = new ResponseObject(
                "Fournisseurs retrieved and sorted successfully",
                HttpStatus.OK.value(),
                fournisseurs
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
