package net.fluxcapdata.repository;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class MarvelRepository {

    protected String BASE_URL = "https://gateway.marvel.com:443";

    @Value("${PRIV_KEY}")
    protected String PRIV_KEY;

    @Value("${API_KEY}")
    protected String API_KEY;

    protected static final Gson GSON = new Gson();
    protected static final RestTemplate REST_TEMPLATE = new RestTemplate();

    protected String generateTS() {
        String ts = "" + Instant.now().toEpochMilli();
        return ts;
    }
    protected String generateHash(String ts) {
        String hash = DigestUtils.md5DigestAsHex((ts + PRIV_KEY + API_KEY).getBytes());
        return hash;
    }

}