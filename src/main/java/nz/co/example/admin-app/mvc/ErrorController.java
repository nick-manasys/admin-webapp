package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.BaseCircuit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for error handling
 * 
 * @author nivanov
 * 
 */
@Controller
@SessionAttributes("sessionData")
public class ErrorController extends ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private SipDomainLogic sipDomainLogic;

    @Autowired
    private CircuitConfigData circuitConfigData;

    @Autowired
    private Validator validator;

    @Autowired
    private SessionData sessionData;

    @RequestMapping("/error")
    public ModelAndView get(HttpSession session, @ModelAttribute("circuit") BaseCircuit circuit, BindingResult result) {
        logger.debug("get circuit : " + circuit.getKey() + " - using sipDomainLogic instance : " + sipDomainLogic);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        // CircuitForm circuitForm = sipDomainLogic.createCircuitForm(circuit);
        // CircuitForm savedCircuitForm = CircuitForm.newInstance(circuitForm);
        // sessionData.setCircuitPreModifications(savedCircuitForm);
        // circuitConfigData.populateSelectionOptions(circuitForm);
        // modelObjects.put("circuit", circuitForm);
        // Circuit fullCircuitDetails = sipDomainLogic.createCircuit(circuitForm);
        // modelObjects.put("fullCircuitDetails", fullCircuitDetails.toString());
        if (null != session.getAttribute("exception")) {
            Exception exception = (Exception) session.getAttribute("exception");
            modelObjects.put("name", exception.getClass());
            modelObjects.put("ex", exception);
            modelObjects.put("exception", exception);
        }
        return new ModelAndView(ViewName.ERROR.toString(), modelObjects);
    }
}
