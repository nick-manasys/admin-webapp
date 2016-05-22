/**
 * 
 */
package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.Circuit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class to control the interaction with the Relinquish Circuit Functionality.
 * 
 * @author nivanov
 * 
 */
@Controller
public class RelinquishCircuitController extends ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(RelinquishCircuitController.class);

    @Autowired
    private SipDomainLogic sipDomainLogic;

    @RequestMapping("/relinquishCircuit")
    public ModelAndView relinquish(@ModelAttribute("circuit") CircuitForm circuit, BindingResult result) {
        logger.debug("relinquish circuit : " + circuit.getKey() + " - using sipDomainLogic instance : "
                + sipDomainLogic);
        Circuit validatedCircuit = sipDomainLogic.createCircuit(circuit);
        sipDomainLogic.removeCircuit(validatedCircuit);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        return new ModelAndView(ViewName.REDIRECT_LIST_CIRCUITS.toString(), modelObjects);
    }
}
