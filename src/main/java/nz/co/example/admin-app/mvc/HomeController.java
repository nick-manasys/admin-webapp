package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import nz.co.example.dev.integration.SessionBorderControllerServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends ExceptionHandlerController {
    @Autowired
    @Qualifier("real")
    private SessionBorderControllerServices service;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView home() {
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        String motd;
        motd = service.getLoginBanner();
        modelObjects.put("motd", motd);
        return new ModelAndView(ViewName.HOME.toString(), modelObjects);
    }

    /**
     * Redirects to the list screen.
     */
    @RequestMapping(value = "/mvc", method = RequestMethod.POST)
    public String enterApplication() {
        logger.debug(ViewName.REDIRECT_LIST_CIRCUITS.toString());
        return ViewName.REDIRECT_LIST_CIRCUITS.toString();
    }
}
