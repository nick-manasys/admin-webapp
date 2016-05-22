package nz.co.example.dev.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import nz.co.example.dev.integration.NNCService;
import nz.co.example.dev.integration.NNCServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.acmepacket.ems.ws.service.fault.AcmeAdminWSFault;
import com.acmepacket.ems.ws.service.fault.AcmeDeviceWSFault;
import com.acmepacket.ems.ws.service.userobjects.NNCDetails;
import com.acmepacket.ems.ws.service.userobjects.NNCServerIPInfo;

public class StatusServlet implements Servlet {
    private static final Logger logger = LoggerFactory.getLogger(StatusServlet.class);

    private ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("\n" + getServletInfo() + "\n" + "Starting...\n");
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        // this must go first
        response.setContentType(MediaType.TEXT_HTML.getType());
        response.setCharacterEncoding("UTF-8");
        String result = "";
        PrintWriter printWriter = response.getWriter();
        result = result + "<html>\n<head>\n</head>\n<body>\n";
        result = result + getServletInfo() + "<br/>\n<br/>\n";
        String catalinaHome = System.getProperty("catalina.home");

        // admin-app.properties
        String filename = catalinaHome + "/conf/" + "admin-app.properties";
        File file = new File(filename);
        result = result + "<br/><img src=\"" + getServletConfig().getServletContext().getContextPath()
                + "/resources/css/images/" + (file.exists() ? "" : "un") + "checked.gif\" />";
        result = result + "Properties file " + "file: '" + filename + "'" + (file.exists() ? " found " : " not found!");

        // nnc.store
        filename = catalinaHome + "/conf/" + "nnc.store";
        file = new File(filename);
        result = result + "<br/><img src=\"" + getServletConfig().getServletContext().getContextPath() + "/resources/css/images/"
                + (file.exists() ? "" : "un") + "checked.gif\" />";
        result = result + "Trust store file " + "file: '" + filename + "'"
                + (file.exists() ? " found " : " not found!");

        // nnc.cer
        filename = catalinaHome + "/conf/" + "nnc.cer";
        file = new File(filename);
        result = result + "<br/><img src=\"" + getServletConfig().getServletContext().getContextPath() + "/resources/css/images/"
                + (file.exists() ? "" : "un") + "checked.gif\" />";
        result = result + "Trust store file " + "file: '" + filename + "'"
                + (file.exists() ? " found " : " not found!");

        // NNC service
        NNCService nncService = NNCServiceImpl.getInstance();
        boolean nncServiceIsUp = null != nncService && null != nncService.getDeviceMgmtIF();
        NNCDetails nncDetails = null;
        try {
            nncDetails = nncService.getDeviceMgmtIF().getNNCDetails();
        } catch (AcmeDeviceWSFault e) {
            nncServiceIsUp = false;
        } catch (AcmeAdminWSFault e) {
            nncServiceIsUp = false;
        } catch (Exception e) {
            nncServiceIsUp = false;
        }
        result = result + "<br/><img src=\"" + getServletConfig().getServletContext().getContextPath() + "/resources/css/images/"
                + (nncServiceIsUp ? "" : "un") + "checked.gif\" />";
        result = result + "NNC service " + (null == nncDetails ? "" : getNNCDetails(nncDetails))
                + (nncServiceIsUp ? " is up" : " is down!");
        result = result + "<br/><br/><a href=\"" + getServletConfig().getServletContext().getContextPath() + "\">"
                + getServletConfig().getServletContext().getContextPath() + "/</a><br/>\n";
        if (!file.exists() || !nncServiceIsUp) {
            result = result + "Service is down";
        } else {
            result = result + "Service is up";
        }
        result = result + "</body>\n</html>\n";
        if (!file.exists() || !nncServiceIsUp) {
            // ((HttpServletResponse) res).sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, result);
            printWriter.write(result);
        } else {
            printWriter.write(result);
        }
    }

    private String getNNCDetails(NNCDetails nncDetails) {
        String result = "";
        result = result + nncDetails.getVersion() + " at " + nncDetails.getMasterHostIP() + "<br/>\n";
        for (NNCServerIPInfo info : nncDetails.getServerIPInfo()) {
            result = result + info.toString();
        }
        return result;
    }

    @Override
    public String getServletInfo() {
        return "Status servlet for ${project.name} v${project.version}";
    }

    @Override
    public void destroy() {
        logger.info("\n" + getServletInfo() + "\n" + "Stopping..." + "\n");
    }
}
