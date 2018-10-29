package net.fluxcapdata.service;

import net.fluxcapdata.entity.Character;
import net.fluxcapdata.repository.MarvelCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private MarvelCharacterRepository marvelCharacterRepository;

    public List<Character> findAll() throws Exception {
        return marvelCharacterRepository.getAllCharacters();
    }
}
