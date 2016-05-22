package nz.co.example.dev.domain.logic;

import java.util.Map;

import nz.co.example.dev.domain.model.Order;
import nz.co.example.dev.domain.model.RegionConfig;
import nz.co.example.dev.mvc.CircuitForm;

/**
 * Provides the configured data that is used to create/modify circuits.
 * 
 * @author nivanov
 */
public interface CircuitConfigData {

    void populateSelectionOptions(CircuitForm circuitForm);

    Map<String, RegionConfig> getRegionConfigMap();

    Map<String, String> getRegionToTargetNameMap();

    Map<String, String> getTargetNameToRegionMap();

    Map<String, RegionConfig> getRegionConfigMappedBySipDefinitionTwo();

    Map<Order, String> getOrderRealmSuffixMap();

    String getNetworkInterfaceDescription();

    String getPrimaryAccessRealmDescription();

    String getSecondaryAccessRealmDescription();

    String getPrimaryAccessRealmParentRealm();

    String getPrimaryAccessRealmRegex();

    String getSecondaryAccessRealmRegex();

    String getPrimaryAccessRealmId();

    String getSecondaryAccessRealmId();

    String getPrimarySipInterfaceDescription();

    String getSecondarySipInterfaceDescription();

}