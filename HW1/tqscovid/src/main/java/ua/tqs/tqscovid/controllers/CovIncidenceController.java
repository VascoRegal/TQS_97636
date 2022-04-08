package ua.tqs.tqscovid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.tqscovid.services.CovIncidenceService;

@RestController
@RequestMapping("/api/")
public class CovIncidenceController {
    
    @Autowired
    private CovIncidenceService covIncidenceService;

}
