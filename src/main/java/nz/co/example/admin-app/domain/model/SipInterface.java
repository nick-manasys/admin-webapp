/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;

/**
 * The Sip Interface.
 * 
 * This encapsulates the configured values for a:
 * 
 * session-router->sip-interface sub-element within the dev.
 * 
 * @author nivanov
 */
public final class SipInterface implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1550097949861781602L;

    private static final Logger logger = LoggerFactory.getLogger(SipInterface.class);

    public static final String TYPE = "sipInterface";

    private Order order;

    private String realmId;

    private State state = State.ENABLED;

    private String description;

    private SipPort udpSipPort;

    private SipPort tcpSipPort;

    private RedirectAction redirectAction = RedirectAction.PROXY;

    private ContactMode contactMode = ContactMode.NONE;

    private NatTraversal natTraversal;

    private State registrationCaching = State.ENABLED;

    private int natInterval;

    private int minRegExpire = 3600;

    private int registrationInterval = 3600;

    private State routeToRegistrar = State.ENABLED;

    private Options options = new Options("+reg-via-key", "+reg-no-port-match");

    private State sipImsFeature = State.ENABLED;

    private TrustMode trustMode = TrustMode.REGISTERED;

    private Integer[] stopRecurse = new Integer[] { 401, 407 };

    public static SipInterface createPrimary(String realmId, String description, InetAddress udpSipPortAddress,
            InetAddress tcpSipPortAddress) {
        return new SipInterface(realmId, description, udpSipPortAddress, tcpSipPortAddress);
    }

    private SipInterface(String realmId, String description, InetAddress udpSipPortAddress,
            InetAddress tcpSipPortAddress) {
        super();
        this.realmId = realmId;
        this.description = description;
        this.udpSipPort = new SipPort(udpSipPortAddress, 5060, AnonymousMode.REGISTERED, TransportProtocol.UDP);
        this.tcpSipPort = new SipPort(tcpSipPortAddress, 5060, AnonymousMode.REGISTERED, TransportProtocol.TCP);
        this.natTraversal = NatTraversal.NONE;
        this.order = Order.PRIMARY;
    }

    public static SipInterface createSecondary(String realmId, String description, InetAddress udpSipPortAddress) {
        return new SipInterface(realmId, description, udpSipPortAddress);
    }

    private SipInterface(String realmId, String description, InetAddress udpSipPortAddress) {
        super();
        this.realmId = realmId;
        this.description = description;
        this.udpSipPort = new SipPort(udpSipPortAddress, 5080, AnonymousMode.REGISTERED, TransportProtocol.UDP);
        this.natTraversal = NatTraversal.ALWAYS;
        this.natInterval = 30;
        this.order = Order.SECONDARY;
    }

    public String getId() {
        return getRealmId() + "-" + getUdpSipPort();
    }

    @Override
    public String toString() {
        switch (this.order) {
        case PRIMARY:
            return String
                    .format("session-router%nsip-interface%nstate %s%nrealm-id %s%ndescription \"%s\"%n%s%n%sredirect-action %s%ncontact-mode %s%nnat-traversal %s%nregistration-caching %s%nmin-reg-expire %s%nregistration-interval %s%nroute-to-registrar %s%n%ssip-ims-feature %s%ntrust-mode %s%nstop-recurse %s%n",
                            state.getDisplayValue(), realmId, description, udpSipPort, tcpSipPort,
                            redirectAction.getDisplayValue(), contactMode.getDisplayValue(),
                            natTraversal.getDisplayValue(), registrationCaching.getDisplayValue(), minRegExpire,
                            registrationInterval, routeToRegistrar.getDisplayValue(), options.toString(),
                            sipImsFeature.getDisplayValue(), trustMode.getDisplayValue(), Joiner.on(",").skipNulls()
                                    .join(stopRecurse));
        case SECONDARY:
            return String
                    .format("session-router%nsip-interface%nstate %s%nrealm-id %s%ndescription \"%s\"%n%s%nredirect-action %s%ncontact-mode %s%nnat-traversal %s%nregistration-caching %s%nnat-interval %s%nmin-reg-expire %s%nregistration-interval %s%nroute-to-registrar %s%n%ssip-ims-feature %s%ntrust-mode %s%nstop-recurse %s%n",
                            state.getDisplayValue(), realmId, description, udpSipPort,
                            redirectAction.getDisplayValue(), contactMode.getDisplayValue(),
                            natTraversal.getDisplayValue(), registrationCaching.getDisplayValue(), natInterval,
                            minRegExpire, registrationInterval, routeToRegistrar.getDisplayValue(), options.toString(),
                            sipImsFeature.getDisplayValue(), trustMode.getDisplayValue(), Joiner.on(",").skipNulls()
                                    .join(stopRecurse));
        default:
            return String.format("Not a correctly configured primary or secondary Sip Interface description: %s%n",
                    description);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contactMode, description, minRegExpire, natInterval, natTraversal, options, order,
                realmId, redirectAction, registrationCaching, registrationInterval, routeToRegistrar, sipImsFeature,
                state, Arrays.hashCode(stopRecurse), tcpSipPort, trustMode, udpSipPort);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SipInterface)) {
            return false;
        }
        SipInterface other = (SipInterface) obj;

        return Objects.equal(contactMode, other.getContactMode()) && Objects.equal(description, other.getDescription())
                && Objects.equal(minRegExpire, other.getMinRegExpire())
                && Objects.equal(natInterval, other.getNatInterval())
                && Objects.equal(natTraversal, other.getNatTraversal()) && Objects.equal(options, other.getOptions())
                && Objects.equal(order, other.getOrder()) && Objects.equal(realmId, other.getRealmId())
                && Objects.equal(redirectAction, other.getRedirectAction())
                && Objects.equal(registrationCaching, other.getRegistrationCaching())
                && Objects.equal(registrationInterval, other.getRegistrationInterval())
                && Objects.equal(routeToRegistrar, other.getRouteToRegistrar())
                && Objects.equal(sipImsFeature, other.getSipImsFeature()) && Objects.equal(state, other.getState())
                && Arrays.equals(stopRecurse, other.getStopRecurse())
                && Objects.equal(tcpSipPort, other.getTcpSipPort()) && Objects.equal(trustMode, other.getTrustMode())
                && Objects.equal(udpSipPort, other.getUdpSipPort());
    }

    public Order getOrder() {
        return order;
    }

    public String getRealmId() {
        return realmId;
    }

    public State getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public SipPort getUdpSipPort() {
        return udpSipPort;
    }

    public SipPort getTcpSipPort() {
        return tcpSipPort;
    }

    public RedirectAction getRedirectAction() {
        return redirectAction;
    }

    public ContactMode getContactMode() {
        return contactMode;
    }

    public NatTraversal getNatTraversal() {
        return natTraversal;
    }

    public State getRegistrationCaching() {
        return registrationCaching;
    }

    public int getNatInterval() {
        return natInterval;
    }

    public int getMinRegExpire() {
        return minRegExpire;
    }

    public int getRegistrationInterval() {
        return registrationInterval;
    }

    public State getRouteToRegistrar() {
        return routeToRegistrar;
    }

    public Options getOptions() {
        return options;
    }

    public State getSipImsFeature() {
        return sipImsFeature;
    }

    public TrustMode getTrustMode() {
        return trustMode;
    }

    public Integer[] getStopRecurse() {
        return stopRecurse.clone();
    }

    /**
     * @return
     */
    public WSConfigElement toWSConfigElement() {
        WSConfigElement result = new WSConfigElement();
        ArrayList<WSConfigElement> subElements = new ArrayList<WSConfigElement>();

        // attributes
        result.setElementTypePath(TYPE);
        result.setType(TYPE);

        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();

        attributeList.add(WSConfigElementUtility.createAttribute("RealmID", this.getRealmId()));
        attributeList.add(WSConfigElementUtility.createAttribute("description", this.getDescription()));

        // fields that need to be there
        // attributeList.add(WSConfigElementUtility.createAttribute("domNameServer", ""));

        result.setAttributeList(attributeList);

        logger.debug("wsConfig=" + result);
        WSConfigElement child;

        // udp sip port
        child = new WSConfigElement();
        child.setElementTypePath(TYPE + "/sipPort");
        child.setType(TYPE + "/sipPort");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(
                WSConfigElementUtility.createAttribute("port", Integer.toString(this.getUdpSipPort().getPort())));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("tlsProfile", ""));
        child.getAttributeList().add(
                WSConfigElementUtility.createAttribute("transProtocol", this.getUdpSipPort().getTransportProtocol()
                        .name()));
        child.getAttributeList().add(
                WSConfigElementUtility.createAttribute("address", this.getUdpSipPort().getAddress().toString()
                        .replaceAll("/", "")));
        child.getAttributeList().add(
                WSConfigElementUtility
                        .createAttribute("anonMode", this.getUdpSipPort().getAnonMode().getDisplayValue()));
        child.getAttributeList().add(WSConfigElementUtility.createAttribute("imsAkaProfile", ""));
        subElements.add(child);

        // tcp sip port
        if (null != this.getTcpSipPort()) {
            child = new WSConfigElement();
            child.setElementTypePath(TYPE + "/sipPort");
            child.setType(TYPE + "/sipPort");
            child.setAttributeList(new ArrayList<WSConfigAttribute>());
            child.getAttributeList().add(
                    WSConfigElementUtility.createAttribute("port", Integer.toString(this.getTcpSipPort().getPort())));
            child.getAttributeList().add(WSConfigElementUtility.createAttribute("tlsProfile", ""));
            child.getAttributeList().add(
                    WSConfigElementUtility.createAttribute("transProtocol", this.getTcpSipPort().getTransportProtocol()
                            .name()));
            child.getAttributeList().add(
                    WSConfigElementUtility.createAttribute("address", this.getTcpSipPort().getAddress().toString()
                            .replaceAll("/", "")));
            child.getAttributeList().add(
                    WSConfigElementUtility.createAttribute("anonMode", this.getTcpSipPort().getAnonMode()
                            .getDisplayValue()));
            child.getAttributeList().add(WSConfigElementUtility.createAttribute("imsAkaProfile", ""));
            subElements.add(child);
        }

        if (null != subElements) {
            result.setChildren(subElements);
        }
        return result;
    }

    /**
     * @param element
     * @return
     */
    public static SipInterface fromWSConfigElement(WSConfigElement fullElement) {
        SipInterface result = null;
        String realmId = WSConfigElementUtility.getAttributeByName(fullElement, "RealmID");
        String description = WSConfigElementUtility.getAttributeByName(fullElement, "description");
        String udpSipPortAddress = WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(
                fullElement, "address", "transProtocol", "UDP");
        String tcpSipPortAddress = WSConfigElementUtility.getChildAttributeByNameWhichHasAttributeWithValue(
                fullElement, "address", "transProtocol", "TCP");
        InetAddress udp = null;
        InetAddress tcp = null;
        tcp = WSConfigElementUtility.parseAddress(tcpSipPortAddress);
        udp = WSConfigElementUtility.parseAddress(udpSipPortAddress);
        if (null != tcp) {
            // if (!WSConfigElementUtility.getAttributeByName(e, "RealmID").endsWith("n.acc")) {
            result = SipInterface.createPrimary(realmId, description, udp, tcp);
        } else {
            result = SipInterface.createSecondary(realmId, description, udp);
        }
        return result;
    }
}