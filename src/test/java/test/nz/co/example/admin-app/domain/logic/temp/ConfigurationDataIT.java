package test.nz.co.example.dev.domain.logic.temp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.example.dev.domain.logic.temp.ConfigurationData;
import nz.co.example.dev.domain.model.Order;
import nz.co.example.dev.domain.model.RegionConfig;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Nick
 * 
 */
public class ConfigurationDataIT {
    private ConfigurationData configurationData;

    @Before
    public void setUp() {
        configurationData = new ConfigurationData();
    }

    @Test
    public void testGetRegionConfigMap() throws Exception {
        Map<String, RegionConfig> regionConfigMap = configurationData.getRegionConfigMap();
        assertTrue(!regionConfigMap.keySet().isEmpty());
        assertTrue(0 != regionConfigMap.keySet().size());
    }

    @Test
    public void testGetRegionConfigMappedBySipDefinitionTwo() throws Exception {
        Map<String, RegionConfig> regionConfigMappedBySipDefinitionTwo = configurationData
                .getRegionConfigMappedBySipDefinitionTwo();
        assertTrue(!regionConfigMappedBySipDefinitionTwo.keySet().isEmpty());
        assertTrue(0 != regionConfigMappedBySipDefinitionTwo.keySet().size());
    }

    @Test
    public void testGetOrderRealmSuffixMap() throws Exception {
        Map<Order, String> orderRealmSuffixMap = configurationData.getOrderRealmSuffixMap();
        assertTrue(!orderRealmSuffixMap.keySet().isEmpty());
        assertTrue(0 != orderRealmSuffixMap.keySet().size());
    }

    @Test
    public void testGetNetworkInterfaceDescription() throws Exception {
        String networkInterfaceDescription = configurationData.getNetworkInterfaceDescription();
        assertNotNull(networkInterfaceDescription);
    }

    @Test
    public void testGetPrimaryAccessRealmDescription() throws Exception {
        String primaryAccessRealmDescription = configurationData.getPrimaryAccessRealmDescription();
        assertNotNull(primaryAccessRealmDescription);
    }

    @Test
    public void testGetSecondaryAccessRealmDescription() throws Exception {
        String secondaryAccessRealmDescription = configurationData.getPrimaryAccessRealmDescription();
        assertNotNull(secondaryAccessRealmDescription);
    }

    @Test
    public void testGetPrimaryAccessRealmParentRealm() throws Exception {
        String primaryAccessRealmParentRealm = configurationData.getPrimaryAccessRealmParentRealm();
        assertNotNull(primaryAccessRealmParentRealm);
    }

    @Test
    public void testGetPrimaryAccessRealmRegex() throws Exception {
        String primaryAccessRealmRegex = configurationData.getPrimaryAccessRealmRegex();
        String field1 = "unittest";
        String field2 = "02";
        String primaryAccessRealmString = "CS2K-" + field1 + "-" + field2 + ".acc";
        Pattern pattern = Pattern.compile(primaryAccessRealmRegex);
        Matcher matcher = pattern.matcher(primaryAccessRealmString);
        assertTrue(matcher.matches());
        if (matcher.matches()) {
            assertEquals(field1, matcher.group(1));
            assertEquals(field2, matcher.group(2));
        }
    }

    @Test
    public void testGetSecondaryAccessRealmRegex() throws Exception {
        String secondaryAccessRealmRegex = configurationData.getSecondaryAccessRealmRegex();
        String field1 = "unittest";
        String field2 = "02";
        String secondaryAccessRealmString = "CS2K-" + field1 + "-" + field2 + "n.acc";
        Pattern pattern = Pattern.compile(secondaryAccessRealmRegex);
        Matcher matcher = pattern.matcher(secondaryAccessRealmString);
        assertTrue(matcher.matches());
        if (matcher.matches()) {
            assertEquals(field1, matcher.group(1));
            assertEquals(field2, matcher.group(2));
        }
    }

    @Test
    public void testGetPrimaryAccessRealmId() throws Exception {
        String primaryAccessRealmId = configurationData.getPrimaryAccessRealmId();
        String field1 = "unittest";
        String field2 = "02";
        String primaryAccessRealmString = "CS2K-" + field1 + "-" + field2 + ".acc";
        assertNotNull(primaryAccessRealmId);
        assertEquals(primaryAccessRealmString, String.format(primaryAccessRealmId, new String[] { field1, field2 }));
    }

    @Test
    public void testGetSecondaryAccessRealmId() throws Exception {
        String secondaryAccessRealmId = configurationData.getSecondaryAccessRealmId();
        String field1 = "unittest";
        String field2 = "02";
        String secondaryAccessRealmString = "CS2K-" + field1 + "-" + field2 + "n.acc";
        assertNotNull(secondaryAccessRealmId);
        assertEquals(secondaryAccessRealmString, String.format(secondaryAccessRealmId, new String[] { field1, field2 }));
    }

    @Test
    public void testGetPrimarySipInterfaceDescription() throws Exception {
        String primarySipInterfaceDescription = configurationData.getPrimarySipInterfaceDescription();
        assertNotNull(primarySipInterfaceDescription);
    }

    @Test
    public void testGetSecondarySipInterfaceDescription() throws Exception {
        String secondarySipInterfaceDescription = configurationData.getPrimarySipInterfaceDescription();
        assertNotNull(secondarySipInterfaceDescription);
    }

    @Test
    public void testGetRegionToNameMap() throws Exception {
        Map<String, String> regionList = configurationData.getRegionToNameMap();
        assertNotNull(!regionList.keySet().isEmpty());
    }
}
