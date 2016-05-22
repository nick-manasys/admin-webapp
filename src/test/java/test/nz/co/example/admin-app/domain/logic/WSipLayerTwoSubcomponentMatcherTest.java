/**
 * 
 */
package test.nz.co.example.dev.domain.logic;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.logic.CircuitSubComponents;
import nz.co.example.dev.domain.logic.SortedCircuitResult;
import nz.co.example.dev.domain.logic.WSipLayerTwoCircuitFactory;
import nz.co.example.dev.domain.logic.WSipLayerTwoSubcomponentMatcher;
import nz.co.example.dev.domain.logic.temp.HardCodedCircuitConfigData;
import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.BaseCircuit;
import nz.co.example.dev.domain.model.CircuitType;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.mvc.CircuitForm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import test.nz.co.example.dev.testsupport.Fixtures;

/**
 * @author nivanov
 * 
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class WSipLayerTwoSubcomponentMatcherTest {

    @InjectMocks
    private WSipLayerTwoSubcomponentMatcher subcomponentMatcher;

    @Mock
    private CircuitConfigData circuitConfigData;

    private HardCodedCircuitConfigData hardCodedCircuitConfigData = new HardCodedCircuitConfigData();

    private WSipLayerTwoCircuitFactory circuitFactory = new WSipLayerTwoCircuitFactory();

    private List<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();
    private List<AccessRealm> accessRealms = new ArrayList<AccessRealm>();
    private List<SipInterface> sipInterfaces = new ArrayList<SipInterface>();
    private List<SteeringPool> steeringPools = new ArrayList<SteeringPool>();

    @Test
    public void matchSubComponentsGivenTwoCompleteCircuitsShouldMatchThem() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");
        BDDMockito.given(circuitConfigData.getRegionConfigMappedBySipDefinitionTwo()).willReturn(
                hardCodedCircuitConfigData.getRegionConfigMappedBySipDefinitionTwo());

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        // When
        SortedCircuitResult<WSipLayerTwoCircuit> matchedSubComponents = subcomponentMatcher
                .matchSubComponents(circuitSubComponents);

        // Then
        assertThat(matchedSubComponents.getSortedCircuits()).containsOnly(circuitOne, circuitTwo);
        assertThat(matchedSubComponents.getUnsortedSubComponents().isEmpty()).isTrue();
    }

    @Test
    public void matchSubComponentsGivenAnExtraNetworkInterfaceShouldLeaveItUnmatched() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        NetworkInterface extraNetworkInterface = Fixtures.createNetworkInterface("unmatched", 001);
        networkInterfaces.add(extraNetworkInterface);
        given(circuitConfigData.getRegionConfigMappedBySipDefinitionTwo()).willReturn(
                hardCodedCircuitConfigData.getRegionConfigMappedBySipDefinitionTwo());

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        // When
        SortedCircuitResult<WSipLayerTwoCircuit> matchedSubComponents = subcomponentMatcher
                .matchSubComponents(circuitSubComponents);

        // Then
        assertThat(matchedSubComponents.getSortedCircuits()).containsOnly(circuitOne, circuitTwo);
        assertThat(matchedSubComponents.getUnsortedSubComponents().isEmpty()).isFalse();

        assertThat(matchedSubComponents.getUnsortedSubComponents().getNetworkInterfaces()).containsExactly(
                extraNetworkInterface);

        assertThat(matchedSubComponents.getUnsortedSubComponents().getAccessRealm()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSipInterfaces()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSteeringPools()).isEmpty();

    }

    @Test
    public void matchSubComponentsGivenAnExtraAccessRealmShouldLeaveItUnmatched() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        AccessRealm extraAccessRealm = Fixtures.createPrimaryAccessRealm("unmatched:001");
        accessRealms.add(extraAccessRealm);
        given(circuitConfigData.getRegionConfigMappedBySipDefinitionTwo()).willReturn(
                hardCodedCircuitConfigData.getRegionConfigMappedBySipDefinitionTwo());

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        // When
        SortedCircuitResult<WSipLayerTwoCircuit> matchedSubComponents = subcomponentMatcher
                .matchSubComponents(circuitSubComponents);

        // Then
        assertThat(matchedSubComponents.getSortedCircuits()).containsOnly(circuitOne, circuitTwo);
        assertThat(matchedSubComponents.getUnsortedSubComponents().isEmpty()).isFalse();

        assertThat(matchedSubComponents.getUnsortedSubComponents().getAccessRealm()).containsOnly(extraAccessRealm);

        assertThat(matchedSubComponents.getUnsortedSubComponents().getNetworkInterfaces()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSipInterfaces()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSteeringPools()).isEmpty();
    }

    @Test
    public void matchSubComponentsGivenAnExtraSipInterfaceShouldLeaveItUnmatched() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        SipInterface extraPrimarySipInterface = Fixtures.createPrimarySipInterface("unmatched");
        sipInterfaces.add(extraPrimarySipInterface);
        given(circuitConfigData.getRegionConfigMappedBySipDefinitionTwo()).willReturn(
                hardCodedCircuitConfigData.getRegionConfigMappedBySipDefinitionTwo());

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        // When
        SortedCircuitResult<WSipLayerTwoCircuit> matchedSubComponents = subcomponentMatcher
                .matchSubComponents(circuitSubComponents);

        // Then
        assertThat(matchedSubComponents.getSortedCircuits()).containsOnly(circuitOne, circuitTwo);
        assertThat(matchedSubComponents.getUnsortedSubComponents().isEmpty()).isFalse();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSipInterfaces()).containsExactly(
                extraPrimarySipInterface);

        assertThat(matchedSubComponents.getUnsortedSubComponents().getNetworkInterfaces()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getAccessRealm()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSteeringPools()).isEmpty();
    }

    @Test
    public void matchSubComponentsGivenAnExtraSteeringPoolShouldLeaveItUnmatched() {
        // Given
        WSipLayerTwoCircuit circuitOne = createCircuit("unittest01", "10.1.1.101", "101", "wlgn");
        WSipLayerTwoCircuit circuitTwo = createCircuit("unittest02", "10.1.1.102", "102", "wlgn");

        SteeringPool extraSteeringPool = Fixtures.createSteeringPool();
        steeringPools.add(extraSteeringPool);
        given(circuitConfigData.getRegionConfigMappedBySipDefinitionTwo()).willReturn(
                hardCodedCircuitConfigData.getRegionConfigMappedBySipDefinitionTwo());

        CircuitSubComponents circuitSubComponents = new CircuitSubComponents(networkInterfaces, accessRealms,
                sipInterfaces, steeringPools);
        // When
        SortedCircuitResult<WSipLayerTwoCircuit> matchedSubComponents = subcomponentMatcher
                .matchSubComponents(circuitSubComponents);

        // Then
        assertThat(matchedSubComponents.getSortedCircuits()).containsOnly(circuitOne, circuitTwo);
        assertThat(matchedSubComponents.getUnsortedSubComponents().isEmpty()).isFalse();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSteeringPools()).containsOnly(extraSteeringPool);

        assertThat(matchedSubComponents.getUnsortedSubComponents().getNetworkInterfaces()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getAccessRealm()).isEmpty();
        assertThat(matchedSubComponents.getUnsortedSubComponents().getSipInterfaces()).isEmpty();
    }

    private WSipLayerTwoCircuit createCircuit(String carrierId, String ipAddress, String vLAN, String region) {
        CircuitForm circuitForm = Fixtures.createCircuitForm(new BaseCircuit(carrierId, ipAddress, vLAN, region,
                CircuitType.W_SIP_LAYER_TWO));
        WSipLayerTwoCircuit circuitOne = circuitFactory.create(circuitForm, hardCodedCircuitConfigData);
        networkInterfaces.add(circuitOne.getNetworkInterface());
        accessRealms.addAll(Arrays.asList(circuitOne.getPrimaryAccessRealm(), circuitOne.getSecondaryAccessRealm()));
        sipInterfaces.addAll(Arrays.asList(circuitOne.getPrimarySipInterface(), circuitOne.getSecondarySipInterface()));
        steeringPools.add(circuitOne.getSteeringPool());
        return circuitOne;
    }
}
