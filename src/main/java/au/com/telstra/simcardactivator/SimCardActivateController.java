package au.com.telstra.simcardactivator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SimCardActivateController {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/activate")
    public ResponseEntity<String> activate(@RequestBody UserRequest userRequest) {

        String actuatorUrl = "http://localhost:8444/actuate";
        Map<String, String> payload = new HashMap<>();
        payload.put("iccid", userRequest.getIccid());

        try {
            ActivationResponse response = restTemplate.postForObject(
                    actuatorUrl,
                    payload,
                    ActivationResponse.class
            );

            if (response != null && response.isSuccess()) {
                System.out.println("SIM activation successful for ICCID: " + userRequest.getIccid());
                return ResponseEntity.ok("Activation successful");
            } else {
                System.out.println("SIM activation failed for ICCID: " + userRequest.getIccid());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Activation failed");
            }

        } catch (Exception e) {
            System.err.println("Error contacting actuator: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Actuator error");
        }
    }
}
