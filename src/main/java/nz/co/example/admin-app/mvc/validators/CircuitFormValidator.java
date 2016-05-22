/**
 * 
 */
package nz.co.example.dev.mvc.validators;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.mvc.CircuitForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Encapsulates the validation required for adding a new circuit or modifying an
 * existing circuit.
 * 
 * @author nivanov
 * 
 */
@Component
public class CircuitFormValidator implements Validator {

    @Autowired
    private SipDomainLogic sipDomainLogic;

    private static final String IP_V4_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final Pattern ipV4Pattern = Pattern.compile(IP_V4_PATTERN);

    private static final Logger logger = LoggerFactory.getLogger(CircuitFormValidator.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> arg0) {
        return CircuitForm.class.isAssignableFrom(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object arg0, Errors errors) {
        CircuitForm circuitForm = (CircuitForm) arg0;
        logger.debug("validating circuit : " + circuitForm.getKey());
        rejectIfEmptyOrContains(errors, circuitForm.getCircuitType(), "circuitType.empty", "NONE");
        rejectIfEmpty(errors, circuitForm.getCarrierShortCode(), "carrierShortCode.empty");
        rejectIfEmpty(errors, circuitForm.getCarrierName(), "carrierName.empty");
        rejectIfEmptyOrContains(errors, circuitForm.getRegion(), "region.empty", "NONE");
        if (!rejectIfEmpty(errors, circuitForm.getTrunkNumber(), "trunkNumber.empty")) {
            rejectIfNotNumericBetween(errors, circuitForm.getTrunkNumber(), "trunkNumber.nonNumeric", 1, 99);
        }
        if (!rejectIfEmpty(errors, circuitForm.getIpAddress(), "ipAddress.empty")) {
            rejectIfNotValidIp(errors, circuitForm.getIpAddress(), "ipAddress.notValidIp");
        }
        if (!rejectIfEmpty(errors, circuitForm.getPrimaryUtilityIpAddress(), "primaryUtilityIpAddress.empty")) {
            rejectIfNotValidIp(errors, circuitForm.getPrimaryUtilityIpAddress(), "primaryUtilityIpAddress.notValidIp");
        }
        if (!rejectIfEmpty(errors, circuitForm.getSecondaryUtilityIpAddress(), "secondaryUtilityIpAddress.empty")) {
            rejectIfNotValidIp(errors, circuitForm.getSecondaryUtilityIpAddress(),
                    "secondaryUtilityIpAddress.notValidIp");
        }
        if (!rejectIfEmpty(errors, circuitForm.getNetworkMask(), "networkMask.empty")) {
            rejectIfNotValidIp(errors, circuitForm.getNetworkMask(), "networkMask.notValidIp");
        }
        if (!rejectIfEmpty(errors, circuitForm.getAccessVLan(), "accessVLan.empty")) {
            if (!rejectIfNotNumericBetween(errors, circuitForm.getAccessVLan(), "accessVLan.nonNumeric", 2, 4096)) {
                if (circuitForm.getAllowedDuplicateAccessVLan() == null) {
                    rejectIfNotUniqueVLANForRegion(errors, circuitForm, "accessVLan.nonUnique");
                } else if (!circuitForm.getAccessVLan().equals(circuitForm.getAllowedDuplicateAccessVLan())) {
                    rejectIfNotUniqueVLANForRegion(errors, circuitForm, "accessVLan.nonUnique");
                }
            }
        }
        if (StringUtils.hasLength(circuitForm.getDefaultGatewayIpAddress())) {
            rejectIfNotValidIp(errors, circuitForm.getDefaultGatewayIpAddress(), "defaultGatewayIpAddress.notValidIp");
        }
    }

    private boolean rejectIfNotUniqueVLANForRegion(Errors errors, CircuitForm circuitForm, String errorCode) {
        String fieldName = errorCode.substring(0, errorCode.indexOf("."));
        if (sipDomainLogic.circuitWithKeyExists(circuitForm.getKey())) {
            errors.rejectValue(fieldName, errorCode);
        }
        return false;
    }

    private boolean rejectIfNotValidIp(Errors errors, String fieldValue, String errorCode) {
        String fieldName = errorCode.substring(0, errorCode.indexOf("."));
        Matcher matcher = ipV4Pattern.matcher(fieldValue);
        if (!matcher.matches()) {
            errors.rejectValue(fieldName, errorCode);
            return true;
        }

        try {
            InetAddress.getByName(fieldValue);
        } catch (UnknownHostException uhe) {
            errors.rejectValue(fieldName, errorCode);
            return true;
        }
        return false;
    }

    private boolean rejectIfNotNumericBetween(Errors errors, String fieldValue, String errorCode, int startValue,
            int endValue) {
        String fieldName = errorCode.substring(0, errorCode.indexOf("."));
        try {
            int formValue = Integer.parseInt(fieldValue);
            if (formValue < startValue || formValue > endValue) {
                errors.rejectValue(fieldName, errorCode, new Object[] { startValue, endValue },
                        String.format("Invalid %s", fieldName));
                return true;
            }

        } catch (NumberFormatException nfe) {
            errors.rejectValue(fieldName, errorCode, new Object[] { startValue, endValue },
                    String.format("Invalid %s", fieldName));
            return true;
        }
        return false;
    }

    private boolean rejectIfEmpty(Errors errors, String fieldValue, String errorCode) {
        if (!StringUtils.hasLength(fieldValue)) {
            errors.rejectValue(errorCode.substring(0, errorCode.indexOf(".")), errorCode);
            return true;
        }
        return false;
    }

    private boolean rejectIfEmptyOrContains(Errors errors, String fieldValue, String errorCode, String contains) {
        if (!StringUtils.hasLength(fieldValue) || fieldValue.contains(contains)) {
            errors.rejectValue(errorCode.substring(0, errorCode.indexOf(".")), errorCode);
            return true;
        }
        return false;
    }

}
