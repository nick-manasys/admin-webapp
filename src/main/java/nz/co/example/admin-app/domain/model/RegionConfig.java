/**
 * 
 */
package nz.co.example.dev.domain.model;

/**
 * Contains the configuration data specific to a region.
 * 
 * TODO This needs to be extracted to the properties database.
 * 
 * @author nivanov
 */
public class RegionConfig {
    private String name;
    private String sipDefinitionOne;
    private String sipDefinitionTwo;
    private String accessNetworkInterfaceLayerTwo;

    public RegionConfig(String name, String sipDefinitionOne, String sipDefinitionTwo,
            String accessNetworkInterfaceLayerTwo) {
        super();
        this.name = name;
        this.sipDefinitionOne = sipDefinitionOne;
        this.sipDefinitionTwo = sipDefinitionTwo;
        this.accessNetworkInterfaceLayerTwo = accessNetworkInterfaceLayerTwo;
    }

    public String getSipDefinitionOne() {
        return sipDefinitionOne;
    }

    public String getSipDefinitionTwo() {
        return sipDefinitionTwo;
    }

    public String getName() {
        return name;
    }

    public String getAccessNetworkInterfaceLayerTwo() {
        return accessNetworkInterfaceLayerTwo;
    }

    @Override
    public String toString() {
        return name;
    }
}
