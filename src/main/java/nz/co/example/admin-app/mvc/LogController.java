package nz.co.example.dev.mvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.co.example.dev.integration.LogEntry;
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
public class LogController extends ExceptionHandlerController {
    @Autowired
    @Qualifier("real")
    private SessionBorderControllerServices service;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/viewLog", method = RequestMethod.GET)
    public ModelAndView viewLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("viewLog()");
        // String path = request.getParameter("path");
        File file = new File(System.getProperty("catalina.home") + "/logs/admin-app_operations.log");
        // new BufferedReader(new FileReader(file));
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // String outgoingFileName = file.getName();
        Map<String, Object> modelObjects = new HashMap<String, Object>();
        // response.setContentType("application/download");
        // response.setHeader("Content-Disposition", "attachment; filename=\"" + outgoingFileName + "\"");
        List<LogEntry> logEntries = new ArrayList<LogEntry>();
        String line;
        LogEntry logEntry;
        while (((line = in.readLine()) != null)) {
            logEntry = LogEntry.parseLogLine(line);
            logEntries.add(logEntry);
        }
        in.close();
        modelObjects.put("catalinaHome", System.getProperty("catalina.home"));
        modelObjects.put("logEntries", logEntries);
        // return new ModelAndView(new RedirectView(successView));
        return new ModelAndView(ViewName.LOG.toString(), modelObjects);
    }
}
