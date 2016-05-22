package test.nz.co.example.dev.domain.logic;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.logic.temp.HardCodedCircuitConfigData;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.nz.co.example.dev.testsupport.Fixtures;

/**
 * @author nivanov
 * 
 */
public class CircuitSubComponentsTest {
    private static final Logger logger = LoggerFactory.getLogger(CircuitSubComponentsTest.class);

    private WSipLayerTwoCircuitFactory circuitFactory = new WSipLayerTwoCircuitFactory();
    private HardCodedCircuitConfigData circuitConfigData = new HardCodedCircuitConfigData();

    private List<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();
    private List<AccessRealm> accessRealms = new ArrayList<AccessRealm>();
    private List<SipInterface> sipInterfaces = new ArrayList<SipInterface>();
    private List<SteeringPool> steeringPools = new ArrayList<SteeringPool>();

    @Test
    public void circuitSubComponentsShouldReturnCorrectlyMappedNetworkInterface() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, NetworkInterface> networkInterfacesMappedById = circuitSubComponents
                .getNetworkInterfacesMappedById();

        // Then
        assertThat(circuitSubComponents.getNetworkInterfaces()).containsExactly(circuitOne.getNetworkInterface(),
                circuitTwo.getNetworkInterface());
        assertThat(networkInterfacesMappedById).includes(entry("m01-access:101", circuitOne.getNetworkInterface()),
                entry("m01-access:102", circuitTwo.getNetworkInterface()));

    }

    @Test
    public void circuitSubComponentsShouldReturnCorrectlyMappedAccessRealms() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, AccessRealm> accessRealmsMapped = circuitSubComponents
                .getAccessRealmsMappedByNetworkInterfaceIdAndOrder();

        // Then
        assertThat(circuitSubComponents.getAccessRealm()).containsExactly(circuitOne.getPrimaryAccessRealm(),
                circuitOne.getSecondaryAccessRealm(), circuitTwo.getPrimaryAccessRealm(),
                circuitTwo.getSecondaryAccessRealm());
        assertThat(accessRealmsMapped).includes(entry("m01-access:101-PRIMARY", circuitOne.getPrimaryAccessRealm()),
                entry("m01-access:101-SECONDARY", circuitOne.getSecondaryAccessRealm()),
                entry("m01-access:102-PRIMARY", circuitTwo.getPrimaryAccessRealm()),
                entry("m01-access:102-SECONDARY", circuitTwo.getSecondaryAccessRealm()));

    }

    @Test
    public void circuitSubComponentsShouldReturnCorrectlyMappedSipInterfaces() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, SipInterface> sipInterfacesMapped = circuitSubComponents.getSipInterfacesMappedByRealmId();

        // Then
        assertThat(circuitSubComponents.getSipInterfaces()).containsExactly(circuitOne.getPrimarySipInterface(),
                circuitOne.getSecondarySipInterface(), circuitTwo.getPrimarySipInterface(),
                circuitTwo.getSecondarySipInterface());

        assertThat(sipInterfacesMapped).includes(entry("CS2K-unittest01-02.acc", circuitOne.getPrimarySipInterface()),
                entry("CS2K-unittest01-02n.acc", circuitOne.getSecondarySipInterface()),
                entry("CS2K-unittest02-02.acc", circuitTwo.getPrimarySipInterface()),
                entry("CS2K-unittest02-02n.acc", circuitTwo.getSecondarySipInterface()));
    }

    @Test
    public void circuitSubComponentsShouldReturnCorrectlyMappedSteeringPools() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, SteeringPool> steeringPoolsMappedByRealmId = circuitSubComponents.getSteeringPoolsMappedByRealmId();

        // Then
        assertThat(circuitSubComponents.getSteeringPools()).containsExactly(circuitOne.getSteeringPool(),
                circuitTwo.getSteeringPool());

        assertThat(steeringPoolsMappedByRealmId).includes(
                entry("CS2K-unittest01-02.acc", circuitOne.getSteeringPool()),
                entry("CS2K-unittest02-02.acc", circuitTwo.getSteeringPool()));
    }

    @Test
    public void circuitSubComponentsWhenDuplicatesShouldMapNonDuplicateNetworkInterfaces() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");
        WSipLayerTwoCircuit circuitThree = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, NetworkInterface> networkInterfacesMappedById = circuitSubComponents
                .getNetworkInterfacesMappedById();

        // Then
        // The list still contains all elements including duplicates
        assertThat(circuitSubComponents.getNetworkInterfaces()).containsExactly(circuitOne.getNetworkInterface(),
                circuitTwo.getNetworkInterface(), circuitThree.getNetworkInterface());

        // The map contains just non-duplicates
        assertThat(networkInterfacesMappedById).includes(entry("m01-access:102", circuitTwo.getNetworkInterface()));

        // The duplicate objects contains the duplicates
        assertThat(circuitSubComponents.getDuplicateObjects().keySet()).contains("m01-access:101");
        assertThat(circuitSubComponents.getDuplicateObjects().get("m01-access:101")).contains(
                circuitOne.getNetworkInterface(), circuitThree.getNetworkInterface());
    }

    @Test
    public void circuitSubComponentsWhenDuplicatesShouldMapNonDuplicateAccessRealms() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");
        WSipLayerTwoCircuit circuitThree = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, AccessRealm> accessRealmsMapped = circuitSubComponents
                .getAccessRealmsMappedByNetworkInterfaceIdAndOrder();

        // Then
        // The list still contains all elements including duplicates
        assertThat(circuitSubComponents.getAccessRealm()).containsExactly(circuitOne.getPrimaryAccessRealm(),
                circuitOne.getSecondaryAccessRealm(), circuitTwo.getPrimaryAccessRealm(),
                circuitTwo.getSecondaryAccessRealm(), circuitThree.getPrimaryAccessRealm(),
                circuitThree.getSecondaryAccessRealm());

        // The map contains just non-duplicates
        assertThat(accessRealmsMapped).includes(entry("m01-access:102-PRIMARY", circuitTwo.getPrimaryAccessRealm()),
                entry("m01-access:102-SECONDARY", circuitTwo.getSecondaryAccessRealm()));
        assertThat(accessRealmsMapped).excludes(entry("m01-access:101-PRIMARY", circuitTwo.getPrimaryAccessRealm()),
                entry("m01-access:101-SECONDARY", circuitTwo.getSecondaryAccessRealm()));

        // The duplicate objects contains the duplicates
        assertThat(circuitSubComponents.getDuplicateObjects().keySet()).contains("m01-access:101-PRIMARY",
                "m01-access:101-SECONDARY");
        assertThat(circuitSubComponents.getDuplicateObjects().get("m01-access:101-PRIMARY")).contains(
                circuitOne.getPrimaryAccessRealm(), circuitThree.getPrimaryAccessRealm());
        assertThat(circuitSubComponents.getDuplicateObjects().get("m01-access:101-SECONDARY")).contains(
                circuitOne.getSecondaryAccessRealm(), circuitThree.getSecondaryAccessRealm());
    }

    @Test
    public void circuitSubComponentsWhenDuplicatesShouldMapNonDuplicatdevInterfaces() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");
        WSipLayerTwoCircuit circuitThree = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, SipInterface> sipInterfacesMapped = circuitSubComponents.getSipInterfacesMappedByRealmId();

        // Then
        // The list still contains all elements including duplicates
        assertThat(circuitSubComponents.getSipInterfaces()).containsExactly(circuitOne.getPrimarySipInterface(),
                circuitOne.getSecondarySipInterface(), circuitTwo.getPrimarySipInterface(),
                circuitTwo.getSecondarySipInterface(), circuitThree.getPrimarySipInterface(),
                circuitThree.getSecondarySipInterface());

        // The map contains just non-duplicates
        assertThat(sipInterfacesMapped).includes(entry("CS2K-unittest02-02.acc", circuitTwo.getPrimarySipInterface()),
                entry("CS2K-unittest02-02n.acc", circuitTwo.getSecondarySipInterface()));

        // The duplicate objects contains the duplicates
        assertThat(circuitSubComponents.getDuplicateObjects().keySet()).contains("CS2K-unittest01-02.acc",
                "CS2K-unittest01-02n.acc");
        assertThat(circuitSubComponents.getDuplicateObjects().get("CS2K-unittest01-02.acc")).contains(
                circuitOne.getPrimarySipInterface(), circuitThree.getPrimarySipInterface());
        assertThat(circuitSubComponents.getDuplicateObjects().get("CS2K-unittest01-02n.acc")).contains(
                circuitOne.getSecondarySipInterface(), circuitThree.getSecondarySipInterface());
    }

    @Test
    public void circuitSubComponentsWhenDuplicatesShouldMapNonDuplicateSteeringPools() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");
        WSipLayerTwoCircuit circuitThree = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // When
        Map<String, SteeringPool> steeringPoolsMappedByRealmId = circuitSubComponents.getSteeringPoolsMappedByRealmId();

        // Then
        // The list still contains all elements including duplicates
        assertThat(circuitSubComponents.getSteeringPools()).containsExactly(circuitOne.getSteeringPool(),
                circuitTwo.getSteeringPool(), circuitThree.getSteeringPool());

        // The map contains just non-duplicates
        assertThat(steeringPoolsMappedByRealmId)
                .includes(entry("CS2K-unittest02-02.acc", circuitTwo.getSteeringPool()));

        // The duplicate objects contains the duplicates
        assertThat(circuitSubComponents.getDuplicateObjects().keySet()).contains("CS2K-unittest01-02.acc",
                "CS2K-unittest01-02n.acc");
        assertThat(circuitSubComponents.getDuplicateObjects().get("CS2K-unittest01-02.acc")).contains(
                circuitOne.getSteeringPool(), circuitThree.getSteeringPool());

        logger.info("Sub component duplicates found : ");

        for (Entry<String, Object> entry : circuitSubComponents.getDuplicateObjects().entries()) {
            logger.info("Duplicate with key - {} \n {}", entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void circuitSubComponentsWhenDuplicatesShouldFindAllDuplicates() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        createCircuit("unittest02", "10.1.1.102", "102", "wlgn");
        WSipLayerTwoCircuit circuitThree = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");

        // When
        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);

        // Then
        // The duplicate objects contains all of the duplicates and only the duplicates.
        assertThat(circuitSubComponents.getDuplicateObjects().keySet()).containsOnly("m01-access:101",
                "CS2K-unittest01-02.acc", "CS2K-unittest01-02n.acc", "m01-access:101-PRIMARY",
                "m01-access:101-SECONDARY");
        assertThat(circuitSubComponents.getDuplicateObjects().get("m01-access:101")).containsOnly(
                circuitOne.getNetworkInterface(), circuitThree.getNetworkInterface());
        assertThat(circuitSubComponents.getDuplicateObjects().get("CS2K-unittest01-02.acc")).containsOnly(
                circuitOne.getPrimarySipInterface(), circuitThree.getPrimarySipInterface(),
                circuitOne.getSteeringPool(), circuitThree.getSteeringPool());
        assertThat(circuitSubComponents.getDuplicateObjects().get("CS2K-unittest01-02n.acc")).containsOnly(
                circuitOne.getSecondarySipInterface(), circuitThree.getSecondarySipInterface());
        assertThat(circuitSubComponents.getDuplicateObjects().get("m01-access:101-PRIMARY")).containsOnly(
                circuitOne.getPrimaryAccessRealm(), circuitThree.getPrimaryAccessRealm());
        assertThat(circuitSubComponents.getDuplicateObjects().get("m01-access:101-SECONDARY")).containsOnly(
                circuitOne.getSecondaryAccessRealm(), circuitThree.getSecondaryAccessRealm());

    }

    private WSipLayerTwoCircuit createCircuit(String carrierId, String ipAddress, String vLAN, String region) {
        CircuitForm circuitForm = Fixtures.createCircuitForm(new BaseCircuit(carrierId, ipAddress, vLAN, region,
                CircuitType.W_SIP_LAYER_TWO));
        WSipLayerTwoCircuit circuitOne = circuitFactory.create(circuitForm, circuitConfigData);
        networkInterfaces.add(circuitOne.getNetworkInterface());
        accessRealms.addAll(Arrays.asList(circuitOne.getPrimaryAccessRealm(), circuitOne.getSecondaryAccessRealm()));
        sipInterfaces.addAll(Arrays.asList(circuitOne.getPrimarySipInterface(), circuitOne.getSecondarySipInterface()));
        steeringPools.add(circuitOne.getSteeringPool());
        return circuitOne;
    }

}
