package com.ensa.jibi.cmi;

import com.ensa.jibi.dto.CreanceFormDTO;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.model.Facture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmiService {

    private final RestTemplate restTemplate;
    private final String cmiUrl;

    @Autowired
    public CmiService(RestTemplate restTemplate, @Value("${cmi.service.url}") String cmiUrl) {
        this.restTemplate = restTemplate;
        this.cmiUrl = cmiUrl;
    }

    public boolean isResponseFavorable(Client client) {
        String url = cmiUrl + "/verify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ClientToSend c = new ClientToSend();
        c.setFirstName(client.getFirstname());
        c.setLastName(client.getLastname());
        c.setCin(client.getCin());

        HttpEntity<ClientToSend> requestEntity = new HttpEntity<>(c, headers);

        try {
            ResponseEntity<CmiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    CmiResponse.class
            );

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


    public Float getbalance(Long clientId){
        String url = cmiUrl + "/getbalance";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> requestEntity = new HttpEntity<>(clientId, headers);

        try {
            ResponseEntity<Float> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Float.class
            );
            return Float.parseFloat(response.getBody().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return 0F;
        }
    }
    public CreanceFormDTO getCreanceFormDetails(Long id){
        String url = cmiUrl + "/getbalance";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> requestEntity = new HttpEntity<>(id, headers);

        try {
            ResponseEntity<CreanceFormDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    CreanceFormDTO.class
            );
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Facture> getImpayeFacturesByRefAndCreance(String factref, Long creance){
        String url = cmiUrl + "/getImpayee";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a request object containing factref and creance
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("factref", factref);
        requestBody.put("creance", creance);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<List<Facture>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Facture>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }



    public boolean isSoldeSuffisant(Long userId, Double amount) {
        String url = cmiUrl + "/checkBalance";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        BalanceRequest balanceRequest = new BalanceRequest(userId, amount);

        HttpEntity<BalanceRequest> requestEntity = new HttpEntity<>(balanceRequest, headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );
            return response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean istransfer(double montant, Long senderId, Long recieverId) {
        String url = cmiUrl + "/maketransfert";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransferRequest transferRequest = new TransferRequest(senderId,recieverId, montant);

        HttpEntity<TransferRequest> requestEntity = new HttpEntity<>(transferRequest, headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );
            return response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody());
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
@Data
@NoArgsConstructor
class ClientToSend {
    private String firstName;
    private String lastName;
    private String cin;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class BalanceRequest {
    private Long userId;
    private Double amount;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class TransferRequest {
    private Long sender;
    private Long reciever;
    private Double amount;
}