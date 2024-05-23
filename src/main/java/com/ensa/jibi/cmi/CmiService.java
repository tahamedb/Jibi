package com.ensa.jibi.cmi;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CmiService {

    private final RestTemplate restTemplate;
    private final String cmiUrl;

    @Autowired
    public CmiService(RestTemplate restTemplate, @Value("${cmi.service.url}") String cmiUrl) {
        this.restTemplate = restTemplate;
        this.cmiUrl = cmiUrl;
    }

    public boolean isResponseFavorable() {
        String url = cmiUrl + "/verify";

        try {
            ResponseEntity<CmiResponse> response = restTemplate.postForEntity(url, null, CmiResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().isFavorable();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

@Getter
class CmiResponse {
    private boolean favorable;

    public void setFavorable(boolean favorable) {
        this.favorable = favorable;
    }
}

