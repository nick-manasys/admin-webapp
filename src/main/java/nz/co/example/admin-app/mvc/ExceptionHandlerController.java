/**
 * 
 */
package nz.co.example.dev.mvc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for Exception handling.
 * 
 * @author nivanov
 *
 */
@Controller
public class ExceptionHandlerController {
    
    private static final Logger logger = LoggerFactory.getLogger(EditCircuitController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        logger.error("Exception occured - forwarding to standard error page.", ex);
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        modelObjects.put("name", ex.getClass().getSimpleName());
        modelObjects.put("ex", ex);
        return new ModelAndView(ViewName.ERROR.toString(), modelObjects);
    }
}
