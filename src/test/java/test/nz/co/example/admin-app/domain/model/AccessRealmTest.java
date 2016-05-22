package test.nz.co.example.dev.domain.model;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.integration.calls.WSConfigElementUtility;

import org.junit.Test;

import test.nz.co.example.dev.testsupport.Fixtures;

import com.acmepacket.ems.ws.service.userobjects.WSConfigAttribute;
import com.acmepacket.ems.ws.service.userobjects.WSConfigElement;
import static test.nz.co.example.dev.testsupport.Fixtures.*;

public class AccessRealmTest {

    @Test
    public void shouldCreateCorrectToStringForPrimaryAccessRealm() {
        // Given
        AccessRealm accessRealm = Fixtures.createPrimaryAccessRealm();

        // When
        String accessRealmToString = accessRealm.toString();

        // Then
        String expectedAccessRealmToString = String
                .format("media-manager%nrealm-config%nidentifier %s%ndescription \"%s\"%naddr-prefix 0.0.0.0%nnetwork-interfaces %s%nmm-in-realm enabled%nmm-in-network enabled%nmm-same-ip disabled%nmm-in-system enabled%nbw-cac-non-mm disabled%nmsm-release disabled%naccess-control-trust-level medium%ninvalid-signal-threshold 1%naverage-rate-limit 0%nmaximum-signal-threshold 4000%nuntrusted-signal-threshold 0%noptions +sip-connect-pbx-reg=rewrite-new%nparent-realm %s",
                        PRIMARY_REALM_ID, PRIMARY_REALM_DESCRIPTION, REALM_NETWORK_INTERFACES,
                        PRIMARY_REALM_PARENT_REALM);
        assertThat(accessRealmToString).isEqualTo(expectedAccessRealmToString);
    }

    @Test
    public void shouldCreateCorrectToStringForSecondaryAccessRealm() {
        // Given
        AccessRealm accessRealm = Fixtures.createSecondaryAccessRealm();

        // When
        String accessRealmToString = accessRealm.toString();

        // Then
        String expectedAccessRealmToString = String
                .format("media-manager%nrealm-config%nidentifier %s%ndescription \"%s\"%naddr-prefix 0.0.0.0%nnetwork-interfaces %s%nmm-in-realm enabled%nmm-in-network enabled%nmm-same-ip disabled%nmm-in-system enabled%nbw-cac-non-mm disabled%nmsm-release disabled%naccess-control-trust-level medium%ninvalid-signal-threshold 1%naverage-rate-limit 0%nmaximum-signal-threshold 4000%nuntrusted-signal-threshold 0%noptions +sip-connect-pbx-reg=rewrite-new%nparent-realm %s%nsymmetric-latching enabled",
                        SECONDARY_REALM_ID, SECONDARY_REALM_DESCRIPTION, REALM_NETWORK_INTERFACES, PRIMARY_REALM_ID);
        assertThat(accessRealmToString).isEqualTo(expectedAccessRealmToString);
    }

    @Test
    public void shouldVerifyEqualsAndHashCode() throws UnknownHostException {
        EqualsVerifier
                .forClass(AccessRealm.class)
                .suppress(Warning.NULL_FIELDS, Warning.NONFINAL_FIELDS)
                .withPrefabValues(InetAddress.class, InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.1.1.2"))
                .verify();

    }

    /**
     * Test mapping from access realm to config element.
     * 
     * @throws Exception
     */
    @Test
    public void testFromWSConfigElement() throws Exception {
        // FIXME check all fields
        String identifier = "test_id";
        String description = "Test access realm description";
        // String networkInterfaces = "";
        String parentRealm = "CS2K-pabx.acc";
        // String regionCode = "lab";
        WSConfigElement fullElement = new WSConfigElement();
        fullElement.setType(AccessRealm.TYPE);
        fullElement.setElementTypePath(AccessRealm.TYPE);
        ArrayList<WSConfigAttribute> attributeList = new ArrayList<WSConfigAttribute>();
        WSConfigElementUtility.createAttribute("description", description);
        WSConfigElementUtility.createAttribute("parent", parentRealm);
        fullElement.setAttributeList(attributeList);
        ArrayList<WSConfigElement> children = new ArrayList<WSConfigElement>();
        fullElement.setChildren(children);
        WSConfigElement child = new WSConfigElement();
        child.setType(AccessRealm.TYPE + "/" + "networkInterfaceId");
        child.setElementTypePath(AccessRealm.TYPE + "/" + "networkInterfaceId");
        WSConfigAttribute attribute = new WSConfigAttribute();
        attribute.setName("name");
        attribute.setValue("value");
        child.setAttributeList(new ArrayList<WSConfigAttribute>());
        child.getAttributeList().add(attribute);
        children.add(child);

        AccessRealm accessRealm = null;
        try {
            accessRealm = AccessRealm.fromWSConfigElement(fullElement);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        // assertions
        assertNotNull(accessRealm);

        assertEquals(accessRealm.getIdentifier(), WSConfigElementUtility.getAttributeByName(fullElement, "id"));
        assertEquals(accessRealm.getIdentifier(), WSConfigElementUtility.getAttributeByName(fullElement, identifier));
        assertEquals(accessRealm.getDescription(),
                WSConfigElementUtility.getAttributeByName(fullElement, "description"));
        // assertEquals(accessRealm.getNetworkInterfaces(),
        // WSConfigElementUtility.getAttributeByName(result, "description"));
        assertEquals(accessRealm.getParentRealm(), WSConfigElementUtility.getAttributeByName(fullElement, "parent"));
        assertEquals(accessRealm.getRegionCode(), WSConfigElementUtility.getAttributeByName(fullElement, "description"));
    }

    @Test
    public void testToWSConfigElement() throws Exception {
        // FIXME check all fields
        String identifier = "test_id";
        String description = "Test access realm description";
        String networkInterfaces = "m01-access:3001";
        String parentRealm = "CS2K-pabx.acc";
        String regionCode = "lab";
        AccessRealm accessRealm = AccessRealm.createPrimary(identifier, description, networkInterfaces, parentRealm,
                regionCode);
        WSConfigElement fullElement;
        fullElement = accessRealm.toWSConfigElement();
        assertNotNull(fullElement);

        // assertions
        assertEquals(accessRealm.getIdentifier(), WSConfigElementUtility.getAttributeByName(fullElement, "id"));
        assertEquals(accessRealm.getDescription(),
                WSConfigElementUtility.getAttributeByName(fullElement, "description"));
        assertEquals(accessRealm.getParentRealm(), WSConfigElementUtility.getAttributeByName(fullElement, "parent"));
    }
}
