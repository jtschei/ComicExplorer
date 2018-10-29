package net.fluxcapdata.repository;

import net.fluxcapdata.entity.Character;
import net.fluxcapdata.entity.CharacterDataContainer;
import net.fluxcapdata.entity.CharacterDataWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class MarvelCharacterRepository extends MarvelRepository {

    @Cacheable("characters")
    public List<Character> getAllCharacters() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> http = new HttpEntity<>(httpHeaders);

        String url = BASE_URL + "/v1/public/characters";
        String ts = generateTS();
        String hash = generateHash(ts);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ts", ts)
                .queryParam("hash", hash)
                .queryParam("apikey", API_KEY)
                .queryParam("orderBy", "name");

        List<CharacterDataContainer> allPages = fetchAll(http, uriBuilder);
        List<Character> allCharacters = new LinkedList<>();
        for (CharacterDataContainer page : allPages) {
            for (Character character : page.getResults()) {
                allCharacters.add(character);
            }
        }
        return allCharacters;
    }

    private List<CharacterDataContainer> fetchAll(HttpEntity<?> http, UriComponentsBuilder uriBuilder) throws Exception {
        List<CharacterDataContainer> responses = new LinkedList<>();

        int limit = 100;
        uriBuilder.replaceQueryParam("limit", limit);

        int offset = 0;
        while (true) {
            uriBuilder.replaceQueryParam("offset", offset);
            ResponseEntity<CharacterDataWrapper> response = REST_TEMPLATE.exchange(uriBuilder.toUriString(), HttpMethod.GET, http, CharacterDataWrapper.class);
            int totalCount = response.getBody().getData().getTotal();
            responses.add(response.getBody().getData());
            offset += limit;
            if (offset > totalCount) {
                break;
            }
        }
        return responses;
    }
}
