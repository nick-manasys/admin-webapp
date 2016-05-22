/**
 * 
 */
package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.Circuit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class to control the interaction with the Edit Circuit Screen
 * 
 * @author nivanov
 * 
 */
@Controller
@SessionAttributes("sessionData")
public class EditCircuitController extends ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(EditCircuitController.class);

    @Autowired
    private SipDomainLogic sipDomainLogic;

    @Autowired
    private CircuitConfigData circuitConfigData;

    @Autowired
    private Validator validator;

    @Autowired
    private SessionData sessionData;

    @RequestMapping("/editCircuit")
    public ModelAndView get(@ModelAttribute("circuit") BaseCircuit circuit, BindingResult result) {
        logger.debug("get circuit : " + circuit.getKey() + " - using sipDomainLogic instance : " + sipDomainLogic);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        if (!StringUtils.hasLength(circuit.getRegion()) || !StringUtils.hasLength(circuit.getVLAN())) {
            logger.info("No circuit selected - Redirecting to New Circuit");
            return new ModelAndView(ViewName.REDIRECT_NEW_CIRCUIT.toString(), modelObjects);
        }
        CircuitForm circuitForm = sipDomainLogic.createCircuitForm(circuit);
        CircuitForm savedCircuitForm = CircuitForm.newInstance(circuitForm);
        sessionData.setCircuitPreModifications(savedCircuitForm);
        circuitConfigData.populateSelectionOptions(circuitForm);
        modelObjects.put("circuit", circuitForm);
        Circuit fullCircuitDetails = sipDomainLogic.createCircuit(circuitForm);
        modelObjects.put("fullCircuitDetails", fullCircuitDetails.toString());
        return new ModelAndView(ViewName.EDIT_CIRCUIT.toString(), modelObjects);
    }

    @RequestMapping("/validateCircuitModifications")
    public ModelAndView validateCircuitMods(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.debug("validateCircuitModifications : " + circuit.getKey() + " - using sipDomainLogic instance : "
                + sipDomainLogic);
        circuit.setAllowedDuplicateAccessVLan(sessionData.getCircuitPreModifications().getAccessVLan());
        validator.validate(circuit, result);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        if (result.hasErrors()) {
            logger.debug("validation failed");
            circuit.setValidatedReadyForSave(false);
        } else {
            circuit.setValidatedReadyForSave(true);
            logger.debug("validation passed");
            Circuit fullCircuitDetails = sipDomainLogic.createCircuit(circuit);
            modelObjects.put("fullCircuitDetails", fullCircuitDetails.toString());
        }
        circuitConfigData.populateSelectionOptions(circuit);
        modelObjects.put("circuit", circuit);
        return new ModelAndView(ViewName.EDIT_CIRCUIT.toString(), modelObjects);
    }

    @RequestMapping("/saveCircuitModifications")
    public String saveCircuitMods(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.debug("saveCircuitModifications");
        Circuit newCircuit = sipDomainLogic.createCircuit(circuit);
        Circuit oldCircuit = sipDomainLogic.createCircuit(sessionData.getCircuitPreModifications());
        sipDomainLogic.modifyCircuit(oldCircuit, newCircuit);
        return ViewName.REDIRECT_LIST_CIRCUITS.toString();
    }

    @RequestMapping("/cancelCircuitModifications")
    public String cancelCircuitMods(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.debug("cancelCircuitModifications");
        return ViewName.REDIRECT_LIST_CIRCUITS.toString();
    }
}
