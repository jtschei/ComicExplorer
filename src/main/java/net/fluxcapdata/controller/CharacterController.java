package net.fluxcapdata.controller;

import net.fluxcapdata.entity.Character;
import net.fluxcapdata.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping(path = "/", produces = "application/json")
    public List<Character> getAllCharacters() throws Exception {
        try {
            List<Character> characters = characterService.findAll();
            if (characters.size() == 0) {
                throw new ElementNotFoundException("No characters found");
            }
            return characters;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
