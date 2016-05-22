/**
 * 
 */
package nz.co.example.dev.domain.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nz.co.example.dev.domain.model.AccessRealm;
import nz.co.example.dev.domain.model.NetworkInterface;
import nz.co.example.dev.domain.model.SipInterface;
import nz.co.example.dev.domain.model.SteeringPool;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * A collection of the various circuit subcomponents.
 * 
 * The lists returned are exactly all of the various subcomponents that were given at construction time.
 * 
 * The various maps however are a rationalised set of the subcomponents with any duplicates removed.
 * The duplicates sre determined by considering the natural key of the subcomponent i.e:
 * 
 * networkInterface - key=name:sup-port
 * sipInterface - key=realmId
 * accessRealm - key=networkInterfaceId-order (where order is PRIMARY or SECONDARY)
 * steeringPool - key=realmId
 * 
 * Any duplicate subcomponents that are found during creation are stored in the duplicate objects multimap.
 * 
 * @author nivanov
 * 
 */
public class CircuitSubComponents {

    public static final String ORDER_SEPARATOR = "-";
    private List<NetworkInterface> networkInterfaces;
    private List<AccessRealm> accessRealms;
    private List<SipInterface> sipInterfaces;
    private List<SteeringPool> steeringPools;

    private Map<String, NetworkInterface> networkInterfacesMappedById;
    private Map<String, AccessRealm> accessRealmsByNetworkInterfaceId;
    private Map<String, SipInterface> sipInterfacesMappedByRealmId;
    private Map<String, SteeringPool> steeringPoolsMappedByRealmId;

    private Multimap<String, Object> duplicateObjects;

    public CircuitSubComponents(List<NetworkInterface> networkInterfaces, List<AccessRealm> accessRealms,
            List<SipInterface> sipInterfaces, List<SteeringPool> steeringPools) {
        super();
        this.networkInterfaces = networkInterfaces;
        this.accessRealms = accessRealms;
        this.sipInterfaces = sipInterfaces;
        this.steeringPools = steeringPools;
        this.duplicateObjects = ArrayListMultimap.create();
        createNetworkInterfacesMappedById();
        createAccessRealmsByNetworkInterfaceId();
        creatdevInterfacesMappedByRealmId();
        createSteeringPoolsMappedByRealmId();
    }

    public CircuitSubComponents(Map<String, NetworkInterface> networkInterfacesMap,
            Map<String, AccessRealm> accessRealmsMap, Map<String, SipInterface> sipInterfacesMap,
            Map<String, SteeringPool> steeringPoolsMap) {
        this.networkInterfacesMappedById = networkInterfacesMap;
        this.accessRealmsByNetworkInterfaceId = accessRealmsMap;
        this.sipInterfacesMappedByRealmId = sipInterfacesMap;
        this.steeringPoolsMappedByRealmId = steeringPoolsMap;
        this.networkInterfaces = new ArrayList<NetworkInterface>(networkInterfacesMap.values());
        this.accessRealms = new ArrayList<AccessRealm>(accessRealmsMap.values());
        this.sipInterfaces = new ArrayList<SipInterface>(sipInterfacesMap.values());
        this.steeringPools = new ArrayList<SteeringPool>(steeringPoolsMap.values());
        this.duplicateObjects = ArrayListMultimap.create();
    }

    public Multimap<String, Object> getDuplicateObjects() {
        return duplicateObjects;
    }

    public List<NetworkInterface> getNetworkInterfaces() {
        return Collections.unmodifiableList(networkInterfaces);
    }

    public List<AccessRealm> getAccessRealm() {
        return Collections.unmodifiableList(accessRealms);
    }

    public List<SipInterface> getSipInterfaces() {
        return Collections.unmodifiableList(sipInterfaces);
    }

    public List<SteeringPool> getSteeringPools() {
        return Collections.unmodifiableList(steeringPools);
    }

    public Map<String, NetworkInterface> getNetworkInterfacesMappedById() {
        return networkInterfacesMappedById;
    }

    public Map<String, AccessRealm> getAccessRealmsMappedByNetworkInterfaceIdAndOrder() {
        return accessRealmsByNetworkInterfaceId;
    }

    public boolean isEmpty() {
        return networkInterfaces.isEmpty() && accessRealms.isEmpty() && sipInterfaces.isEmpty()
                && steeringPools.isEmpty();
    }

    public Map<String, SipInterface> getSipInterfacesMappedByRealmId() {
        return sipInterfacesMappedByRealmId;
    }

    public Map<String, SteeringPool> getSteeringPoolsMappedByRealmId() {
        return steeringPoolsMappedByRealmId;
    }

    private void createSteeringPoolsMappedByRealmId() {
        steeringPoolsMappedByRealmId = new HashMap<String, SteeringPool>();

        for (SteeringPool steeringPool : steeringPools) {
            String key = steeringPool.getRealmId();
            if (steeringPoolsMappedByRealmId.containsKey(key)) {
                duplicateObjects.put(key, steeringPool);
                duplicateObjects.put(key, steeringPoolsMappedByRealmId.get(key));
                steeringPoolsMappedByRealmId.remove(key);
            } else {
                steeringPoolsMappedByRealmId.put(key, steeringPool);
            }
        }
    }

    private void createNetworkInterfacesMappedById() {
        networkInterfacesMappedById = new HashMap<String, NetworkInterface>();
        for (NetworkInterface networkInterface : networkInterfaces) {
            String key = networkInterface.getId();
            if (networkInterfacesMappedById.containsKey(key)) {
                duplicateObjects.put(key, networkInterface);
                duplicateObjects.put(key, networkInterfacesMappedById.get(key));
                networkInterfacesMappedById.remove(key);
            } else {
                networkInterfacesMappedById.put(key, networkInterface);
            }
        }
    }

    private void createAccessRealmsByNetworkInterfaceId() {
        accessRealmsByNetworkInterfaceId = new TreeMap<String, AccessRealm>();

        for (AccessRealm accessRealm : accessRealms) {
            String key = accessRealm.getNetworkInterfaces() + ORDER_SEPARATOR + accessRealm.getOrder();
            if (accessRealmsByNetworkInterfaceId.containsKey(key)) {
                duplicateObjects.put(key, accessRealm);
                duplicateObjects.put(key, accessRealmsByNetworkInterfaceId.get(key));
                accessRealmsByNetworkInterfaceId.remove(key);
            } else {
                accessRealmsByNetworkInterfaceId.put(key, accessRealm);
            }
        }
    }

    private void creatdevInterfacesMappedByRealmId() {
        sipInterfacesMappedByRealmId = new HashMap<String, SipInterface>();
        for (SipInterface sipInterface : sipInterfaces) {
            String key = sipInterface.getRealmId();
            if (sipInterfacesMappedByRealmId.containsKey(key)) {
                duplicateObjects.put(key, sipInterface);
                duplicateObjects.put(key, sipInterfacesMappedByRealmId.get(key));
                sipInterfacesMappedByRealmId.remove(key);
            } else {
                sipInterfacesMappedByRealmId.put(key, sipInterface);
            }
        }
    }
}
