package nz.co.example.dev.domain.logic;

import java.util.ArrayList;
import java.util.List;

import nz.co.example.dev.domain.exception.UnknownCircuitTypeException;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.integration.SessionBorderControllerServices;
import nz.co.example.dev.mvc.CircuitForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Provides domain logic for the sip models.
 * 
 * 
 * @author nivanov
 * 
 */
@Service
public class SipDomainLogicImpl implements SipDomainLogic {

    private static final Logger logger = LoggerFactory.getLogger(SipDomainLogicImpl.class);

    @Autowired
    private List<CircuitFactory<Circuit>> circuitFactories;

    @Autowired
    private List<CircuitFormFactory<Circuit>> circuitFormFactories;

    @Autowired
    private CircuitConfigData circuitConfigData;

    @Autowired
    private CircuitCache circuitCache;

    @Autowired
    @Qualifier("real")
    private SessionBorderControllerServices sessionBorderControllerServices;

    @Autowired
    private CircuitSubComponentSorter circuitSubComponentSorter;

    /*
     * (non-Javadoc)
     * 
     * @see nz.co.example.dev.domain.logic.SipDomainLogic#getAllCircuits()
     */
    @Override
    public List<Circuit> getAllCircuits() {
        List<Circuit> circuitList = new ArrayList<Circuit>();
        try {
            if (circuitCache.isEmpty()) {
                logger.debug("Circuit Cache is empty - repopulating");
                CircuitSubComponents circuitSubComponents = sessionBorderControllerServices
                        .getAllCircuitSubComponents();
                SortedCircuitResult<Circuit> sortedCircuits = circuitSubComponentSorter
                        .getSortedCircuits(circuitSubComponents);
                circuitList = sortedCircuits.getSortedCircuits();
                circuitCache.addCircuits(circuitList);
                circuitList = circuitCache.getCircuits();
            } else {
                logger.debug("Returning Circuits from the cache.");
                circuitList = circuitCache.getCircuits();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return circuitList;
    }

    @Override
    public void addCircuit(Circuit circuit) {
        sessionBorderControllerServices.addNewCircuit(circuit);
        circuitCache.addCircuit(circuit);
    }

    @Override
    public void modifyCircuit(Circuit oldCircuit, Circuit newCircuit) {
        sessionBorderControllerServices.modifyCircuit(oldCircuit, newCircuit);
        circuitCache.modifyCircuit(oldCircuit, newCircuit);
    }

    @Override
    public void removeCircuit(Circuit circuit) {
        sessionBorderControllerServices.relinquishCircuit(circuit);
        circuitCache.removeCircuit(circuit);
    }

    @Override
    public CircuitForm createCircuitForm(Circuit circuit) {
        Circuit circuitFromCache = circuitCache.getCircuit(circuit.getKey());

        for (CircuitFormFactory<Circuit> circuitFormFactory : circuitFormFactories) {
            if (circuitFormFactory.createsFromType(circuitFromCache)) {
                return circuitFormFactory.create(circuitFromCache, circuitConfigData);
            }
        }

        throw new UnknownCircuitTypeException(
                String.format(
                        "We do not have a form factory able to handle circuits of type - %s. This realy shouldn't ever happen and represents a coding error.",
                        circuit.getCircuitType()));
    }

    @Override
    public boolean circuitWithKeyExists(String circuitKey) {
        return circuitCache.containsCircuit(circuitKey);
    }

    public void setCircuitFormFactories(List<CircuitFormFactory<Circuit>> circuitFormFactories) {
        this.circuitFormFactories = circuitFormFactories;
    }

    public void setCircuitFactories(List<CircuitFactory<Circuit>> circuitFactories) {
        this.circuitFactories = circuitFactories;
    }

    public Circuit createCircuit(CircuitForm circuitForm) {
        for (CircuitFactory<Circuit> circuitFactory : circuitFactories) {
            if (circuitFactory.createsType(circuitForm)) {
                return circuitFactory.create(circuitForm, circuitConfigData);
            }
        }
        throw new UnknownCircuitTypeException(
                String.format(
                        "We do not have a factory able to handle circuits of type - %s. This realy shouldn't ever happen and represents a coding error.",
                        circuitForm.getCircuitType()));
    }
}
