package com.cebem.rickandmorty.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cebem.rickandmorty.models.CharacterModel;
import com.cebem.rickandmorty.models.CharactersModel;
import com.cebem.rickandmorty.models.NumberCharacters;
import com.cebem.rickandmorty.utils.Utils;

@Service
public class RickAndMortyService  {

    @Autowired
    RestTemplate restTemplate;

    final static String BASE_API = "https://rickandmortyapi.com/api";

    public CharacterModel getCharacterRandom(){
        final int TOTAL_CHARACTERS = 826;
        int random = Utils.getRandomValue(TOTAL_CHARACTERS-1)+1;   //1...826
        String url = "/character/" + random;

        //libreria para hacer una peticion a un api y que devuelva un resultado.

        //RestTemplate restTemplate = new RestTemplate();
        CharacterModel datos = restTemplate.getForObject(BASE_API+url, CharacterModel.class);
        return datos;

    }
    public CharactersModel getAllCharacters(){
        String url = "/character/";
        CharactersModel datos = restTemplate.getForObject(BASE_API+url, CharactersModel.class);

        return datos;
    }
    public int getNumberCharacters() {
        String url = "/character/";
        NumberCharacters datos = restTemplate.getForObject(BASE_API+url, NumberCharacters.class);
        int count = datos.getInfo().getCount();
        return count;
    }

}
