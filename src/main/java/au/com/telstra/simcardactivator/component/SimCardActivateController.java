package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.database.SimInfo;
import au.com.telstra.simcardactivator.database.SimInfoRepository;
import au.com.telstra.simcardactivator.database.SimInfoResponse;
import au.com.telstra.simcardactivator.foundation.SimCard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivateController {
    private final SimCardActuationHandler simCardActuationHandler;
    private final SimInfoRepository simInfoRepository;

    public SimCardActivateController(SimCardActuationHandler simCardActuationHandler, SimInfoRepository simInfoRepository) {
        this.simCardActuationHandler = simCardActuationHandler;
        this.simInfoRepository = simInfoRepository;
    }

    @PostMapping(value = "/activate")
    public void handleActivationRequest(@RequestBody SimCard simCard) {
        var actuationResult = simCardActuationHandler.actuate(simCard);

        SimInfo record = new SimInfo(simCard.getIccid(), simCard.getCustomerEmail(), actuationResult.getSuccess());
        simInfoRepository.save(record);

        System.out.println(actuationResult);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<SimInfoResponse> handleSearchRequest(@RequestParam Long simCardId) {
        return simInfoRepository.findById(simCardId)
                .map(record -> new ResponseEntity<>(
                        new SimInfoResponse(record.getIccid(), record.getCustomerEmail(), record.isActive()),
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
