package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.component.DatabaseConduit;
import au.com.telstra.simcardactivator.foundation.SimCard;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.h2.engine.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private DatabaseConduit databaseConduit;
    private SimCard simCard;
    private ResponseEntity<SimCard> queryResponse;

    /**
     * Scenario: Successful SIM card activation, with ICCID 1255789453849037777
     * **/
    @Given("a SIM card with ICCID {string}")
    public void a_sim_card_with_iccid(String iccid) {
        simCard = new SimCard(iccid, "example1@gmail.com", false);
    }

    @When("I send an activation request")
    public void i_send_an_activation_request() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SimCard> request = new HttpEntity<>(simCard, headers);

        restTemplate.postForEntity("http://localhost:8080/activate", request, Void.class);
    }

    @And("I query the activation record with ID {int}")
    public void i_query_the_activation_record_with_id(int id) {
        queryResponse = restTemplate.getForEntity("http://localhost:8080/search?simCardId=" + id, SimCard.class);
    }

    @Then("the SIM card should be marked as active")
    public void the_sim_card_should_be_marked_as_active() {
        assertEquals(HttpStatus.OK, queryResponse.getStatusCode());
        assertTrue(Objects.requireNonNull(queryResponse.getBody()).getActive(), "Expected SIM card to be active");
    }

    /**
     * Scenario: Failed SIM card activation, with ICCID 8944500102198304826
     * **/
    @Then("the SIM card should not be marked as active")
    public void the_sim_card_should_not_be_marked_as_active() {
        assertEquals(HttpStatus.OK, queryResponse.getStatusCode());
        assertFalse(Objects.requireNonNull(queryResponse.getBody()).getActive(), "Expected SIM card to be inactive");
    }
}