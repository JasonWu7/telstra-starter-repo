package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.SimCard;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivateController {
    private final SimCardActuationHandler simCardActuationHandler;
    private final DatabaseConduit databaseConduit;

    public SimCardActivateController(SimCardActuationHandler simCardActuationHandler, DatabaseConduit databaseConduit) {
        this.simCardActuationHandler = simCardActuationHandler;
        this.databaseConduit = databaseConduit;
    }

    @PostMapping(value = "/activate")
    public void handleActivationRequest(@RequestBody SimCard simCard) {
        var actuationResult = simCardActuationHandler.actuate(simCard);
        databaseConduit.save(simCard, actuationResult);
    }

    @GetMapping(value = "/search")
    public SimCard handleSearchRequest(@RequestParam Long simCardId) {
        return databaseConduit.querySimCard(simCardId);
    }
}
