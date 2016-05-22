/**
 * 
 */
package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.Circuit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class to control the interaction with the New Circuit Screen
 * 
 * @author nivanov
 * 
 */
@Controller
public class NewCircuitController extends ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(NewCircuitController.class);

    @Autowired
    private Validator validator;

    @Autowired
    private SipDomainLogic sipDomainLogic;

    @Autowired
    private CircuitConfigData circuitConfigData;

    // @ModelAttribute("circuit")
    public CircuitForm formBackingObject() {
        logger.debug("formBackingObject - using sipDomainLogic instance : " + sipDomainLogic);
        CircuitForm newCircuit = new CircuitForm();
        circuitConfigData.populateSelectionOptions(newCircuit);
        return newCircuit;
    }

    @RequestMapping("/newCircuit")
    public ModelAndView get() {
        logger.debug("get - using sipDomainLogic instance : " + sipDomainLogic);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        modelObjects.put("circuit", formBackingObject());
        return new ModelAndView(ViewName.NEW_CIRCUIT.toString(), modelObjects);
    }

    @RequestMapping("/validateNewCircuit")
    public ModelAndView validateNewCircuit(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.debug("validateNewCircuit : " + circuit.getKey() + " - using sipDomainLogic instance : "
                + sipDomainLogic);
        validator.validate(circuit, result);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        if (result.hasErrors()) {
            logger.debug("validation failed");
        } else {
            circuit.setValidatedReadyForSave(true);
            logger.debug("validation passed");
            Circuit fullCircuitDetails = sipDomainLogic.createCircuit(circuit);
            modelObjects.put("fullCircuitDetails", fullCircuitDetails.toString());
        }
        circuitConfigData.populateSelectionOptions(circuit);
        modelObjects.put("circuit", circuit);
        return new ModelAndView(ViewName.NEW_CIRCUIT.toString(), modelObjects);
    }

    @RequestMapping("/saveNewCircuit")
    public String saveNewCircuit(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.debug("saveNewCircuit : " + circuit.getKey() + " - using sipDomainLogic instance : " + sipDomainLogic);
        Circuit validatedCircuit = sipDomainLogic.createCircuit(circuit);
        sipDomainLogic.addCircuit(validatedCircuit);
        return ViewName.REDIRECT_LIST_CIRCUITS.toString();
    }

    @RequestMapping("/cancelNewCircuit")
    public String cancelNewCircuit(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.info("cancelNewCircuit : " + circuit.getKey() + " - using sipDomainLogic instance : " + sipDomainLogic);
        return ViewName.REDIRECT_LIST_CIRCUITS.toString();
    }
}
