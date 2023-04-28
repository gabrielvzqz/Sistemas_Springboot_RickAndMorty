package com.cebem.rickandmorty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cebem.rickandmorty.models.CharactersModel;
import com.cebem.rickandmorty.services.RickAndMortyService;

@Controller
public class WebController {

    @Autowired
    RickAndMortyService rickAndMortyService;
    
    @RequestMapping("/rickandmorty/allTemplate")
    public String CharactersTemplate(Model modelo){
        CharactersModel charactersModel =  rickAndMortyService.getAllCharacters();

        modelo.addAttribute("creator", "Creado por Gabriel");
        modelo.addAttribute("characters", charactersModel.results);
        return "rickandmortyall";
    }
    /*Mostrar en una página web el total de personajes y que aparezca en
     * mitad de la pantalla en color verde, usando un motor de plantillas (thymeleaf)
    */
    @RequestMapping("/rickandmorty/numbercharacters")
    public String numberCharacters(Model modelo) {
        int numberCharacters = rickAndMortyService.getNumberCharacters();

        modelo.addAttribute("number", numberCharacters);

        return "rickandmortynumber";
    }
}