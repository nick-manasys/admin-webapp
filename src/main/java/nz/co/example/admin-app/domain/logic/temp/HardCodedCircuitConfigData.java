package nz.co.example.dev.domain.logic.temp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import nz.co.example.dev.domain.logic.CircuitConfigData;
import nz.co.example.dev.domain.model.Order;
import nz.co.example.dev.domain.model.RegionConfig;
import nz.co.example.dev.mvc.CircuitForm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Hard coded version of the configured data that is used to create/modify
 * circuits.
 * 
 * Eventual this will be replaced by a version that retrieves the data from
 * external configurable sources.
 * 
 * @author nivanov
 */
@Service
@Qualifier("hardcoded")
public class HardCodedCircuitConfigData implements CircuitConfigData {

    private static final String CHCH_SIP_DEF_TWO = "218.101.10.136";
    private static final String CHCH_SIP_DEF_ONE = "218.101.10.135";
    private static final String WLGN_SIP_DEF_TWO = "218.101.10.72";
    private static final String WLGN_SIP_DEF_ONE = "218.101.10.71";
    private static final String AUC_SIP_DEF_TWO = "218.101.10.8";
    private static final String AUC_SIP_DEF_ONE = "218.101.10.7";
    private Map<String, RegionConfig> regionConfigMap;
    private Map<String, String> circuitTypes;
    private Map<String, String> regionTypes;
    private Map<Order, String> regionRealmSuffix;
    private Map<String, RegionConfig> regionConfigMapBySipDefinitionTwo;
    private Map<String, String> regionToTargetNameMap;
    private Map<String, String> targetNameToRegionMap;

    public HardCodedCircuitConfigData() {
        circuitTypes = new LinkedHashMap<String, String>();
        circuitTypes.put("W_SIP_LAYER_TWO", "W-SIP Layer 2");

        regionTypes = new LinkedHashMap<String, String>();
        regionTypes.put("auc", "Auckland");
        regionTypes.put("wlgn", "Wellington");
        regionTypes.put("chch", "Christchurch");

        regionRealmSuffix = new HashMap<Order, String>();
        regionRealmSuffix.put(Order.PRIMARY, ".acc");
        regionRealmSuffix.put(Order.SECONDARY, "n.acc");

        regionConfigMap = new HashMap<String, RegionConfig>();
        regionConfigMap.put("auc", new RegionConfig("auc", AUC_SIP_DEF_ONE, AUC_SIP_DEF_TWO, "m01-access"));
        regionConfigMap.put("wlgn", new RegionConfig("wlgn", WLGN_SIP_DEF_ONE, WLGN_SIP_DEF_TWO, "m01-access"));
        regionConfigMap.put("chch", new RegionConfig("chch", CHCH_SIP_DEF_ONE, CHCH_SIP_DEF_TWO, "m01-access"));

        regionConfigMapBySipDefinitionTwo = new HashMap<String, RegionConfig>();
        regionConfigMapBySipDefinitionTwo.put(AUC_SIP_DEF_TWO, new RegionConfig("auc", AUC_SIP_DEF_ONE,
                AUC_SIP_DEF_TWO, "m01-access"));
        regionConfigMapBySipDefinitionTwo.put(WLGN_SIP_DEF_TWO, new RegionConfig("wlgn", WLGN_SIP_DEF_ONE,
                WLGN_SIP_DEF_TWO, "m01-access"));
        regionConfigMapBySipDefinitionTwo.put(CHCH_SIP_DEF_TWO, new RegionConfig("chch", CHCH_SIP_DEF_ONE,
                CHCH_SIP_DEF_TWO, "m01-access"));

        regionToTargetNameMap = new HashMap<String, String>();
        regionToTargetNameMap.put("auc", "dev1-TSPN_dev2-TSPN");
        regionToTargetNameMap.put("chch", "dev5-TSTC_dev6-TSTC");
        regionToTargetNameMap.put("wlgn", "dev3-WGTN_dev4-WGTN");
        regionToTargetNameMap.put("lab", "dev-LAB");

        targetNameToRegionMap = new HashMap<String, String>();
        targetNameToRegionMap.put("dev1-TSPN_dev2-TSPN", "auc");
        targetNameToRegionMap.put("dev5-TSTC_dev6-TSTC", "chch");
        targetNameToRegionMap.put("dev3-WGTN_dev4-WGTN", "wlgn");
        targetNameToRegionMap.put("dev-LAB", "lab");
    }

    @Override
    public void populateSelectionOptions(CircuitForm circuitForm) {
        circuitForm.setCircuitTypeOptions(getCircuitTypeList());
        circuitForm.setRegionOptions(getRegionList());
    }

    private Map<String, String> getCircuitTypeList() {
        return circuitTypes;
    }

    private Map<String, String> getRegionList() {

        return regionTypes;
    }

    @Override
    public String getNetworkInterfaceDescription() {
        return "Connection to Access Network for customer %s";
    }

    @Override
    public String getPrimaryAccessRealmDescription() {
        return "Access Realm for customer %s";
    }

    @Override
    public String getSecondaryAccessRealmDescription() {
        return "NAT Access Realm for customer %s";
    }

    @Override
    public String getPrimarySipInterfaceDescription() {
        return "Access SIP-Interface for customer %s";
    }

    @Override
    public String getSecondarySipInterfaceDescription() {
        return "NAT Access SIP-Interface for customer %s";
    }

    @Override
    public String getPrimaryAccessRealmParentRealm() {
        return "CS2K-pabx.acc";
    }

    @Override
    public String getPrimaryAccessRealmRegex() {
        return "CS2K-([a-zA-Z0-9]*)-(\\d+).acc";
    }

    @Override
    public String getSecondaryAccessRealmRegex() {
        return "CS2K-([a-zA-Z0-9]*)-(\\d+)n.acc";
    }

    @Override
    public String getPrimaryAccessRealmId() {
        return "CS2K-%s-%s.acc";
    }

    @Override
    public String getSecondaryAccessRealmId() {
        return "CS2K-%s-%sn.acc";
    }

    @Override
    public Map<Order, String> getOrderRealmSuffixMap() {
        return regionRealmSuffix;
    }

    @Override
    public Map<String, RegionConfig> getRegionConfigMap() {
        return regionConfigMap;
    }

    @Override
    public Map<String, RegionConfig> getRegionConfigMappedBySipDefinitionTwo() {
        return regionConfigMapBySipDefinitionTwo;
    }

    @Override
    public Map<String, String> getRegionToTargetNameMap() {
        return regionToTargetNameMap;
    }

    @Override
    public Map<String, String> getTargetNameToRegionMap() {
        return targetNameToRegionMap;
    }
}
