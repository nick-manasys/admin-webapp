package nz.co.example.dev.domain.model;

import java.util.Objects;

/**
 * A BaseCircuit implementation of a {@link Circuit}.
 * 
 * Encapsulates the basic circuit information that is common to all circuits.
 * 
 * @author nivanov
 * 
 */
public class BaseCircuit implements Circuit {

    private String carrierId;
    private String ipAddress;
    private String VLAN;
    private String region;
    private CircuitType circuitType;

    private String carrierShortCode;
    private String carrierName;
    private String trunkNumber;
    private String primaryUtilityIpAddress;
    private String secondaryUtilityIpAddress;
    private String networkMask;
    private String accessVLan;
    private String defaultGatewayIpAddress;

    public BaseCircuit() {
    }

    public BaseCircuit(String carrierId, String ipAddress, String VLAN, String region, CircuitType circuitType) {
        super();
        this.carrierId = carrierId;
        this.ipAddress = ipAddress;
        this.VLAN = VLAN;
        this.region = region;
        this.circuitType = circuitType;
    }

    public String getHint() {
        return "Base Circuit";
    }

    @Override
    public final int hashCode() {
        return Objects.hash(VLAN, carrierId, ipAddress, region);
    }

    @Override
    public final boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseCircuit)) {
            return false;
        }
        BaseCircuit other = (BaseCircuit) obj;
        return Objects.equals(VLAN, other.getVLAN()) && Objects.equals(carrierId, other.getCarrierId())
                && Objects.equals(ipAddress, other.getIpAddress()) && Objects.equals(region, other.getRegion());

    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getVLAN() {
        return VLAN;
    }

    public void setVLAN(String VLAN) {
        this.VLAN = VLAN;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public CircuitType getCircuitType() {
        return circuitType;
    }

    public void setCircuitType(CircuitType circuitType) {
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

    public String getTrunkNumber() {
        return trunkNumber;
    }

    public void setTrunkNumber(String trunkNumber) {
        this.trunkNumber = trunkNumber;
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

    public String getDefaultGatewayIpAddress() {
        return defaultGatewayIpAddress;
    }

    public void setDefaultGatewayIpAddress(String defaultGatewayIpAddress) {
        this.defaultGatewayIpAddress = defaultGatewayIpAddress;
    }

    public String getAccessVLan() {
        return accessVLan;
    }

    public void setAccessVLan(String accessVLan) {
        this.accessVLan = accessVLan;
    }

    public String getNetworkMask() {
        return networkMask;
    }

    public void setNetworkMask(String networkMask) {
        this.networkMask = networkMask;
    }

    @Override
    public String getKey() {
        return this.getRegion() + "-" + this.getVLAN();
    }
}
