/**
 * 
 */
package nz.co.example.dev.mvc;

import java.io.Serializable;
import java.util.Map;

/**
 * Encapsulates the form data to be added/modified when adding/modifying a Circuit.
 * 
 * @author nivanov
 * 
 */
public class CircuitForm implements Serializable {
    private static final long serialVersionUID = -8656914415018129297L;

    private static final String IP_ADDRESS = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    private String circuitType;

    private String carrierShortCode;
    private String carrierName;
    private String region;
    private String trunkNumber;

    // @Pattern(regexp = IP_ADDRESS)
    private String ipAddress;

    // @Pattern(regexp = IP_ADDRESS)
    private String primaryUtilityIpAddress;

    // @Pattern(regexp = IP_ADDRESS)
    private String secondaryUtilityIpAddress;

    // @Pattern(regexp = IP_ADDRESS)
    private String networkMask;

    private String accessVLan;

    // @Pattern(regexp = IP_ADDRESS)
    private String defaultGatewayIpAddress;

    private boolean validatedReadyForSave = false;

    private String allowedDuplicateAccessVLan;

    private Map<String, String> circuitTypeOptions;
    private Map<String, String> regionOptions;

    public static CircuitForm newInstance(CircuitForm circuitForm) {
        CircuitForm circuitFormCopy = new CircuitForm();
        circuitFormCopy.setAccessVLan(circuitForm.getAccessVLan());
        circuitFormCopy.setCarrierName(circuitForm.getCarrierName());
        circuitFormCopy.setCarrierShortCode(circuitForm.getCarrierShortCode());
        circuitFormCopy.setCircuitType(circuitForm.getCircuitType());
        circuitFormCopy.setCircuitTypeOptions(circuitForm.getCircuitTypeOptions());
        circuitFormCopy.setDefaultGatewayIpAddress(circuitForm.getDefaultGatewayIpAddress());
        circuitFormCopy.setIpAddress(circuitForm.getIpAddress());
        circuitFormCopy.setNetworkMask(circuitForm.getNetworkMask());
        circuitFormCopy.setPrimaryUtilityIpAddress(circuitForm.getPrimaryUtilityIpAddress());
        circuitFormCopy.setRegion(circuitForm.getRegion());
        circuitFormCopy.setRegionOptions(circuitForm.getRegionOptions());
        circuitFormCopy.setSecondaryUtilityIpAddress(circuitForm.getSecondaryUtilityIpAddress());
        circuitFormCopy.setTrunkNumber(circuitForm.getTrunkNumber());
        circuitFormCopy.setValidatedReadyForSave(circuitForm.isValidatedReadyForSave());
        return circuitFormCopy;
    }

    public String getCircuitType() {
        return circuitType;
    }

    public void setCircuitType(String circuitType) {
        this.circuitType = circuitType;
    }

    public String getCarrierShortCode() {
        return carrierShortCode;
    }

    public void setCarrierShortCode(String carrierShortCode) {
        this.carrierShortCode = carrierShortCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTrunkNumber() {
        return trunkNumber;
    }

    public void setTrunkNumber(String trunkNumber) {
        this.trunkNumber = trunkNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPrimaryUtilityIpAddress() {
        return primaryUtilityIpAddress;
    }

    public void setPrimaryUtilityIpAddress(String primaryUtilityIpAddress) {
        this.primaryUtilityIpAddress = primaryUtilityIpAddress;
    }

    public String getSecondaryUtilityIpAddress() {
        return secondaryUtilityIpAddress;
    }

    public void setSecondaryUtilityIpAddress(String secondaryUtilityIpAddress) {
        this.secondaryUtilityIpAddress = secondaryUtilityIpAddress;
    }

    public String getNetworkMask() {
        return networkMask;
    }

    public void setNetworkMask(String networkMask) {
        this.networkMask = networkMask;
    }

    public String getAccessVLan() {
        return accessVLan;
    }

    public void setAccessVLan(String accessVLan) {
        this.accessVLan = accessVLan;
    }

    public String getDefaultGatewayIpAddress() {
        return defaultGatewayIpAddress;
    }

    public void setDefaultGatewayIpAddress(String defaultGatewayIpAddress) {
        this.defaultGatewayIpAddress = defaultGatewayIpAddress;
    }

    public Map<String, String> getCircuitTypeOptions() {
        return circuitTypeOptions;
    }

    public void setCircuitTypeOptions(Map<String, String> circuitTypeOptions) {
        this.circuitTypeOptions = circuitTypeOptions;
    }

    public Map<String, String> getRegionOptions() {
        return regionOptions;
    }

    public void setRegionOptions(Map<String, String> regionOptions) {
        this.regionOptions = regionOptions;
    }

    public boolean isValidatedReadyForSave() {
        return validatedReadyForSave;
    }

    public void setValidatedReadyForSave(boolean validatedReadyForSave) {
        this.validatedReadyForSave = validatedReadyForSave;
    }

    public String getAllowedDuplicateAccessVLan() {
        return allowedDuplicateAccessVLan;
    }

    public void setAllowedDuplicateAccessVLan(String allowedDuplicateAccessVLan) {
        this.allowedDuplicateAccessVLan = allowedDuplicateAccessVLan;
    }

    public String getKey() {
        return this.getRegion() + "-" + this.getAccessVLan();
    }
}
