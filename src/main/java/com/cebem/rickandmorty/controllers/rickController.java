package com.cebem.rickandmorty.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cebem.rickandmorty.models.CharacterModel;
import com.cebem.rickandmorty.models.CharactersModel;
import com.cebem.rickandmorty.services.RickAndMortyService;
import com.cebem.rickandmorty.utils.Utils;

@RestController
@CrossOrigin(origins = "*")
public class rickController {

    // http://127.0.0.1/saluda
    // http://localhost/saluda
    @GetMapping("/saluda")
    public String saluda() {
        return "Bienvenid@ a mi api rest de RickAndMorty";
    }

    // http://localhost:8080/random
    @GetMapping("/random")
    public String random() {
        return Math.round(Math.random() * 10) + "";
    }

    // http://localhost:8080/palindromo/palabra
    @GetMapping("/palindromo/{palabra}")
    public String palindromo(@PathVariable String palabra) {
        return Utils.isPalindrome(palabra) ? "Si es un palindromo" : "No es un palindromo";
    }

    // http://localhost:8080/add?n1=2&n2=4
    @GetMapping("/add")
    public String add(@RequestParam String n1,
            @RequestParam String n2) {
        float resultado = Float.parseFloat(n1) + Float.parseFloat(n2);
        Object params[] = { n1, n2, resultado };
        return MessageFormat.format("La suma de {0} mas {1} es igual a {2}", params);
    }

    @PostMapping("/saveOnDisk")
    public String saveOnDisk(@RequestParam Map<String, String> body) {
        String name = body.get("name");
        String price = body.get("price");

        System.out.println(name + "-" + price);

        String info = name + " " + price + "\n";

        try {
            Utils.writeOnDisk("datos.txt", info);
        } catch (IOException e) {
            return "Error al intentar escribir en el fichero";
        }

        return "Gracias por enviar el formulario, los datos han sido guardados.";

    }

    @DeleteMapping("/removeFromDisk")
    public String removeFromDisk(){
            boolean r = Utils.deleteFile("datos.txt");
            return r ? "Borrado correcto" : "No se ha borrado";
        }
    @GetMapping("/cuadrado")
    public String cuadrado(@RequestParam String numero){
        try{
        float value = Float.parseFloat(numero);
        return value*value + "";
        } catch(NumberFormatException ex){
            return "El numero no es válido.";
        }    
    }
    @DeleteMapping("/clear")
    public String clear(){
        try{
        Utils.clearFile("datos.txt");
        return "Fichero limpiado correctamente";
        }catch(IOException ex){
            return "Error al limpiar el fichero " + ex.getMessage(); 
        }
    }
    @GetMapping("/products")
    public static String getProducts(){
        try{
        return Utils.readFromDisk("datos.txt");
        }catch(FileNotFoundException ex){
            return "No se encontró el fichero datos.txt";
        }catch(IOException ex){
            return "Falló al acceder al fichero: " + ex.getMessage();

        }
    }
    @GetMapping("/max")
    public static String max(@RequestParam String n1,@RequestParam String n2, @RequestParam String n3){
        float f1 = Float.parseFloat(n1);
        float f2 = Float.parseFloat(n2);
        float f3 = Float.parseFloat(n3);
        return Utils.maxOfElements(f1, f2, f3) + "";
    }
    @GetMapping("/capitalize/{text}")
        public static String capitalize(@PathVariable String text){
            return Utils.capitalizeText(text);
        }
    @GetMapping("/randomColors")
    public static String randomColors(){
        final int COLORS_COUNT =3;
        final String[] COLORS = new String[] {"negro", "azul", "marrón", "gris", "verde",
        "naranja", "rosa", "purpura", "rojo", "blanco", "amarillo"};
        if(COLORS_COUNT> COLORS.length) throw new RuntimeException("Solo puedes seleccionar 11 colores");
        ArrayList<String> colors = new ArrayList<String>(Arrays.asList(COLORS));
        String finalColors = "";
        for (int i = 0; i<COLORS_COUNT;i++){
            int random = Utils.getRandomValue(colors.size());
            finalColors += colors.remove(random) + " ";
        }
        return finalColors;
    }
    // https://rickandmortyapi.com/api/character/???

    @Autowired
    RickAndMortyService rickAndMortyService;

    @GetMapping("/rickandmorty/random")
        public String randomCharacter(){
            //RickAndMortyService service = new RickAndMortyService();
            CharacterModel characterModel = rickAndMortyService.getCharacterRandom();
            
            return characterModel.name + "<br/>" + "<img src='"+characterModel.image+"'/>";
        }

        @GetMapping("/rickandmorty/allcharacters")
            public String allCharacters(){
            CharactersModel charactersModel =  rickAndMortyService.getAllCharacters();
            String html="<html>";
            html += "<head>";
            html += "<body>";
            for(CharacterModel characterModel : charactersModel.results){
            html += characterModel.name;
            html += "<br/>";
            html += "<img width='200px' src='"+characterModel.image+"'>";
            html += "<hr/>";
            }
            html += "</head>";
            html += "</body>";
            return html;

            }


}