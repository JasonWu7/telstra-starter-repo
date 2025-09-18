package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.SimCard;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivateController {
    private final SimCardActuationHandler simCardActuationHandler;

    public SimCardActivateController(SimCardActuationHandler simCardActuationHandler) {
        this.simCardActuationHandler = simCardActuationHandler;
    }

    @PostMapping(value = "/activate")
    public void handleActivationRequest(@RequestBody SimCard simCard) {
        var actuationResult = simCardActuationHandler.actuate(simCard);
        System.out.println(actuationResult.getSuccess());
    }
}
