package nz.co.example.dev.domain.logic.temp;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import nz.co.example.applicationproperty.AppPropertyManager;
import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.Order;
import nz.co.example.dev.domain.model.RegionConfig;
import nz.co.example.dev.mvc.CircuitForm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Class to load configuration data from app_property table.
 * 
 * @author Nick
 */
// @Service
// @Qualifier("real")
public class ConfigurationData implements CircuitConfigData {
    // private static final String KEY_REGIONMAP = "config.region.map";

    private static final String KEY_CONFIG_ORDERREALMSUFFIXMAP = "config.order.realm.suffix.map";

    private static final String KEY_SECONDARY_SIP_INTERFACE_DESCRIPTION = "config.secondary.sip.interface.description";

    private static final String KEY_PRIMARY_ACCESS_REALM_REGEX = "config.primary.access.realm.regex";

    private static final String KEY_SECONDARY_ACCESS_REALM_REGEX = "config.secondary.access.realm.regex";

    private static final String KEY_PRIMARY_ACCESS_REALM_ID = "config.primary.access.realm.id";

    private static final String KEY_SECONDARY_ACCESS_REALM_ID = "config.secondary.access.realm.id";

    private static final String KEY_PRIMARY_ACCESS_REALM_PARENT_REALM = "config.primary.access.realm.parent.realm";

    private static final String KEY_SECONDARY_ACCESS_REALM_DESCRIPTION = "config.secondary.access.realm.description";

    private static final String KEY_PRIMARY_ACCESS_REALM_DESCRIPTION = "config.primary.access.realm.description";

    private static final String KEY_PRIMARY_SIP_INTERFACE_DESCRIPTION = "config.primary.sip.interface.description";

    private static final String KEY_SIP_DEF_ONE_MAP = "config.sip.def.one.map";

    private static final String KEY_SIP_DEF_TWO_MAP = "config.sip.def.two.map";

    private static final String KEY_ACCESS_NETWORK_INTERFACE_LAYER_TWO_MAP = "config.access.network.interface.layer.two.map";

    private static final String KEY_CIRCUIT_TYPE_MAP = "config.circuit.type.map";

    private static final String KEY_REGION_NAME_MAP = "config.region.name.map";

    private static final String KEY_REGION_TARGETNAME_MAP = "config.region.targetname.map";

    // AppPropertyManager manager;

    private Map<String, String> circuitTypes;

    private Map<String, String> regionToNameMap;

    private Map<String, String> regionToTargetNameMap;

    private Map<String, String> targetNameToRegionMap;

    private Map<String, RegionConfig> regionConfigMap;

    private HashMap<String, RegionConfig> regionConfigMapBySipDefinitionTwo;

    private String primarySipInterfaceDescription;

    private String secondarySipInterfaceDescription;

    private String primaryAccessRealmId;

    private String secondaryAccessRealmId;

    private String primaryAccessRealmParentRealm;

    private String secondaryAccessRealmDescription;

    private String primaryAccessRealmDescription;

    private Map<Order, String> orderRealmSuffixMap;

    private String primaryAccessRealmRegex;

    private String secondaryAccessRealmRegex;

    /**
     * 
     */
    public ConfigurationData() {
        // EMPTY
    }

    private Map<String, String> getCircuitTypeMap() {
        if (null == circuitTypes) {
            circuitTypes = new HashMap<String, String>();
            Map<String, String> map = AppPropertyManager.getNameValues(KEY_CIRCUIT_TYPE_MAP);
            for (String key : map.keySet()) {
                String value = map.get(key);
                circuitTypes.put(key, value);
            }
        }
        return circuitTypes;
    }

    public Map<String, String> getRegionToNameMap() {
        if (null == regionToNameMap) {
            regionToNameMap = new TreeMap<String, String>();
            Map<String, String> map = AppPropertyManager.getNameValues(KEY_REGION_NAME_MAP);
            for (String key : map.keySet()) {
                String value = map.get(key);
                regionToNameMap.put(key, value);
            }
        }
        return regionToNameMap;
    }

    public Map<String, String> getRegionToTargetNameMap() {
        if (null == regionToTargetNameMap) {
            regionToTargetNameMap = new TreeMap<String, String>();
            Map<String, String> map = AppPropertyManager.getNameValues(KEY_REGION_TARGETNAME_MAP);
            for (String key : map.keySet()) {
                String value = map.get(key);
                regionToTargetNameMap.put(key, value);
            }
        }
        return regionToTargetNameMap;
    }

    public Map<String, String> getTargetNameToRegionMap() {
        if (null == targetNameToRegionMap) {
            targetNameToRegionMap = new TreeMap<String, String>();
            Map<String, String> map = AppPropertyManager.getNameValues(KEY_REGION_TARGETNAME_MAP);
            for (String key : map.keySet()) {
                String value = map.get(key);
                targetNameToRegionMap.put(value, key);
            }
        }
        return targetNameToRegionMap;
    }

    @Override
    public void populateSelectionOptions(CircuitForm circuitForm) {
        circuitForm.setCircuitTypeOptions(getCircuitTypeMap());
        circuitForm.setRegionOptions(getRegionToNameMap());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nz.co.example.dev.domain.logic.CircuitConfigData#getRegionConfigMap()
     */
    @Override
    public Map<String, RegionConfig> getRegionConfigMap() {
        if (null == regionConfigMap) {
            regionConfigMap = new HashMap<String, RegionConfig>();
            Map<String, String> map = AppPropertyManager.getNameValues(KEY_REGION_NAME_MAP);
            Map<String, String> mapSipDefOne = AppPropertyManager.getNameValues(KEY_SIP_DEF_ONE_MAP);
            Map<String, String> mapSipDefTwo = AppPropertyManager.getNameValues(KEY_SIP_DEF_TWO_MAP);
            Map<String, String> mapAccessNetworkInterfaceLayerTwo = AppPropertyManager
                    .getNameValues(KEY_ACCESS_NETWORK_INTERFACE_LAYER_TWO_MAP);

            for (String regionName : map.keySet()) {
                // String name = map.get(regionName);
                String sipDefinitionOne = mapSipDefOne.get(regionName);
                String sipDefinitionTwo = mapSipDefTwo.get(regionName);
                String accessNetworkInterfaceLayerTwo = mapAccessNetworkInterfaceLayerTwo.get(regionName);
                RegionConfig regionConfig = new RegionConfig(regionName, sipDefinitionOne, sipDefinitionTwo,
                        accessNetworkInterfaceLayerTwo);
                regionConfigMap.put(regionName, regionConfig);
            }
        }
        return regionConfigMap;
    }

    @Override
    public Map<String, RegionConfig> getRegionConfigMappedBySipDefinitionTwo() {
        if (null == regionConfigMapBySipDefinitionTwo) {
            regionConfigMapBySipDefinitionTwo = new HashMap<String, RegionConfig>();

            Map<String, String> map = AppPropertyManager.getNameValues(KEY_REGION_NAME_MAP);
            Map<String, String> mapSipDefOne = AppPropertyManager.getNameValues(KEY_SIP_DEF_ONE_MAP);
            Map<String, String> mapSipDefTwo = AppPropertyManager.getNameValues(KEY_SIP_DEF_TWO_MAP);
            Map<String, String> mapAccessNetworkInterfaceLayerTwo = AppPropertyManager
                    .getNameValues(KEY_ACCESS_NETWORK_INTERFACE_LAYER_TWO_MAP);

            for (String regionName : map.keySet()) {
                String sipDefinitionOne = mapSipDefOne.get(regionName);
                String sipDefinitionTwo = mapSipDefTwo.get(regionName);
                String accessNetworkInterfaceLayerTwo = mapAccessNetworkInterfaceLayerTwo.get(regionName);
                regionConfigMapBySipDefinitionTwo.put(sipDefinitionTwo, new RegionConfig(regionName, sipDefinitionOne,
                        sipDefinitionTwo, accessNetworkInterfaceLayerTwo));
            }
        }
        return regionConfigMapBySipDefinitionTwo;
    }

    @Override
    public Map<Order, String> getOrderRealmSuffixMap() {
        if (null == orderRealmSuffixMap) {
            orderRealmSuffixMap = new HashMap<Order, String>();
            Map<String, String> map = AppPropertyManager.getNameValues(KEY_CONFIG_ORDERREALMSUFFIXMAP);
            for (String key : map.keySet()) {
                String realmSuffix = map.get(key);
                Order order = Order.valueOf(key);
                orderRealmSuffixMap.put(order, realmSuffix);
            }
        }
        return orderRealmSuffixMap;
    }

    @Override
    public String getNetworkInterfaceDescription() {
        if (null == secondaryAccessRealmDescription) {
            secondaryAccessRealmDescription = AppPropertyManager.getProperty(KEY_SECONDARY_ACCESS_REALM_DESCRIPTION);
        }
        return secondaryAccessRealmDescription;
    }

    @Override
    public String getPrimaryAccessRealmDescription() {
        if (null == primaryAccessRealmDescription) {
            primaryAccessRealmDescription = AppPropertyManager.getProperty(KEY_PRIMARY_ACCESS_REALM_DESCRIPTION);
        }
        return primaryAccessRealmDescription;
    }

    @Override
    public String getSecondaryAccessRealmDescription() {
        if (null == secondaryAccessRealmDescription) {
            secondaryAccessRealmDescription = AppPropertyManager.getProperty(KEY_SECONDARY_ACCESS_REALM_DESCRIPTION);
        }
        return secondaryAccessRealmDescription;
    }

    @Override
    public String getPrimaryAccessRealmParentRealm() {
        if (null == primaryAccessRealmParentRealm) {
            primaryAccessRealmParentRealm = AppPropertyManager.getProperty(KEY_PRIMARY_ACCESS_REALM_PARENT_REALM);
        }
        return primaryAccessRealmParentRealm;
    }

    @Override
    public String getPrimaryAccessRealmRegex() {
        if (null == primaryAccessRealmRegex) {
            primaryAccessRealmRegex = AppPropertyManager.getProperty(KEY_PRIMARY_ACCESS_REALM_REGEX);
        }
        return primaryAccessRealmRegex;
    }

    @Override
    public String getSecondaryAccessRealmRegex() {
        if (null == secondaryAccessRealmRegex) {
            secondaryAccessRealmRegex = AppPropertyManager.getProperty(KEY_SECONDARY_ACCESS_REALM_REGEX);
        }
        return secondaryAccessRealmRegex;
    }

    @Override
    public String getPrimaryAccessRealmId() {
        if (null == primaryAccessRealmId) {
            primaryAccessRealmId = AppPropertyManager.getProperty(KEY_PRIMARY_ACCESS_REALM_ID);
        }
        return primaryAccessRealmId;
    }

    @Override
    public String getSecondaryAccessRealmId() {
        if (null == secondaryAccessRealmId) {
            secondaryAccessRealmId = AppPropertyManager.getProperty(KEY_SECONDARY_ACCESS_REALM_ID);
        }
        return secondaryAccessRealmId;
    }

    @Override
    public String getPrimarySipInterfaceDescription() {
        if (null == primarySipInterfaceDescription) {
            primarySipInterfaceDescription = AppPropertyManager.getProperty(KEY_PRIMARY_SIP_INTERFACE_DESCRIPTION);
        }
        return primarySipInterfaceDescription;
    }

    @Override
    public String getSecondarySipInterfaceDescription() {
        if (null == secondarySipInterfaceDescription) {
            secondarySipInterfaceDescription = AppPropertyManager.getProperty(KEY_SECONDARY_SIP_INTERFACE_DESCRIPTION);
        }
        return secondarySipInterfaceDescription;
    }
}
