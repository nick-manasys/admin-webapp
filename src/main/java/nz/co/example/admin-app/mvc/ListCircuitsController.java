/**
 * 
 */
package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.presentation.SessionKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class to control the interaction with the Circuit List Screen
 * 
 * @author nivanov
 * 
 */
@Controller
@RequestMapping("/")
public class ListCircuitsController extends ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ListCircuitsController.class);

    @Autowired
    private SipDomainLogic sipDomainLogic;

    @RequestMapping("/listCircuits")
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response) {
        // ModelAndView result = new ModelAndView(ViewName.LIST_CIRCUITS.toString());
        logger.debug("get - using sipDomainLogic instance : " + sipDomainLogic);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        modelObjects.put("circuits", sipDomainLogic.getAllCircuits());

        if (null != request) {
            HttpSession session = request.getSession(true);
            modelObjects.put("userId", session.getAttribute(SessionKeys.PERSONID));
        }
        return new ModelAndView(ViewName.LIST_CIRCUITS.toString(), modelObjects);
    }
}
