package com.tricol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.entity.Fournisseur;
import com.tricol.response.ResponseObject;
import com.tricol.service.impl.FournisseurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FournisseurController implements Controller {
    private FournisseurService fournisseurService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setFournisseurService(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }




    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        // Extract the path after context path
        String path = requestURI.substring(contextPath.length());


        if (method.equals("POST") && path.endsWith("/add")) {
            handleAdd(request, response);
        } else if (method.equals("PUT") && path.contains("/update/")) {
            handleUpdate(request, response, path);
        } else if (method.equals("DELETE") && path.contains("/delete/")) {
            handleDelete(request, response, path);
        } else if (method.equals("GET") && path.endsWith("/all")) {
            handleGetAll(request, response);
        }else if (method.equals("GET") && path.contains("/id/")) {
            handleGetById(request, response, path);
        } else if (method.equals("GET") && path.endsWith("/sortBySociety")) {
            handleSortBySociety(request, response);
        }else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writeResponse(response, new ResponseObject("Endpoint not found for: " + path + " with method: " + method, 404, null));
        }

        return null;
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Fournisseur fournisseur = readRequestBody(request, Fournisseur.class);
            Fournisseur createdFournisseur = fournisseurService.saveFournisseur(fournisseur);

            response.setStatus(HttpServletResponse.SC_CREATED);
            writeResponse(response, new ResponseObject("Fournisseur created successfully", 201, createdFournisseur));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeResponse(response, new ResponseObject("Error creating fournisseur: " + e.getMessage(), 400, null));
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws IOException {
        try {
            Long id = extractIdFromPath(pathInfo, "/update/");
            Fournisseur fournisseur = readRequestBody(request, Fournisseur.class);
            Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(id, fournisseur);

            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, new ResponseObject("Fournisseur updated successfully", 200, updatedFournisseur));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writeResponse(response, new ResponseObject("Error updating fournisseur: " + e.getMessage(), 404, null));
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws IOException {
        try {
            Long id = extractIdFromPath(pathInfo, "/delete/");
            fournisseurService.deleteFournisseur(id);

            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, new ResponseObject("Fournisseur deleted successfully", 200, null));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writeResponse(response, new ResponseObject("Fournisseur not found or could not be deleted", 404, null));
        }
    }

    private void handleGetAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Fournisseur> fournisseurs = fournisseurService.findAllFournisseurs();

        if (fournisseurs.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            writeResponse(response, new ResponseObject("No fournisseurs found", 204, null));
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, new ResponseObject("Fournisseurs retrieved successfully", 200, fournisseurs));
        }
    }

    private void handleGetById(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws IOException {
        try {
            Long id = extractIdFromPath(pathInfo, "/id/");
            Fournisseur fournisseur = fournisseurService.findFournisseurById(id)
                    .orElseThrow(() -> new RuntimeException("Fournisseur not found with id " + id));

            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, new ResponseObject("Fournisseur retrieved successfully", 200, fournisseur));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writeResponse(response, new ResponseObject("Error retrieving fournisseur: " + e.getMessage(), 404, null));
        }
    }

    private void handleSortBySociety(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Fournisseur> fournisseurs = fournisseurService.sortFournisseursBySociety();

        if (fournisseurs.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            writeResponse(response, new ResponseObject("No fournisseurs found", 204, null));
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, new ResponseObject("Fournisseurs retrieved and sorted successfully", 200, fournisseurs));
        }
    }

    private <T> T readRequestBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return objectMapper.readValue(sb.toString(), clazz);
    }

    private void writeResponse(HttpServletResponse response, ResponseObject responseObject) throws IOException {
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(responseObject));
        out.flush();
    }

    private Long extractIdFromPath(String pathInfo, String prefix) {
        String idStr = pathInfo.substring(pathInfo.indexOf(prefix) + prefix.length());
        return Long.parseLong(idStr);
    }
}
