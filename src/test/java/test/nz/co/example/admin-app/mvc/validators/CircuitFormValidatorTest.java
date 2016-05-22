package test.nz.co.example.dev.mvc.validators;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import nz.co.example.dev.domain.logic.SipDomainLogic;
import nz.co.example.dev.domain.model.Circuit;
import nz.co.example.dev.mvc.CircuitForm;
import nz.co.example.dev.mvc.validators.CircuitFormValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import test.nz.co.example.dev.testsupport.Fixtures;

@RunWith(MockitoJUnitRunner.class)
public class CircuitFormValidatorTest {

    @Mock
    private Errors errors;

    @Mock
    private SipDomainLogic sipDomainLogic;

    @InjectMocks
    private CircuitFormValidator circuitFormValidator;

    @Test
    public void supportsWhenPassedCircuitFormShouldReturnTrue() {
        boolean supports = circuitFormValidator.supports(CircuitForm.class);
        assertThat(supports).isTrue();
    }

    @Test
    public void supportsWhenPassedCircuitShouldReturnFalse() {
        assertThat(circuitFormValidator.supports(Circuit.class)).isFalse();
    }

    @Test
    public void validateWhenCircuitTypeEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setCircuitType("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("circuitType", "circuitType.empty");
    }

    @Test
    public void validateWhenCircuitTypeNotSelectedShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setCircuitType("NONE");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("circuitType", "circuitType.empty");
    }

    @Test
    public void validateWhenCarrierShortCodeEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setCarrierShortCode("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("carrierShortCode", "carrierShortCode.empty");
    }

    @Test
    public void validateWhenCarrierNameEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setCarrierName("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("carrierName", "carrierName.empty");
    }

    @Test
    public void validateWhenCarrierEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setRegion("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("region", "region.empty");
    }

    @Test
    public void validateWhenRegionNotSelectedShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setRegion("NONE");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("region", "region.empty");
    }

    @Test
    public void validateWhenTrunkNumerEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setTrunkNumber("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("trunkNumber", "trunkNumber.empty");
    }

    @Test
    public void validateWhenTrunkNumerNotNumericShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setTrunkNumber("nope");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("trunkNumber", "trunkNumber.nonNumeric", new Object[] { 1, 99 },
                "Invalid trunkNumber");
    }

    @Test
    public void validateWhenTrunkNumerTooHighShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setTrunkNumber("100");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("trunkNumber", "trunkNumber.nonNumeric", new Object[] { 1, 99 },
                "Invalid trunkNumber");
    }

    @Test
    public void validateWhenTrunkNumerTooLowShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setTrunkNumber("0");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("trunkNumber", "trunkNumber.nonNumeric", new Object[] { 1, 99 },
                "Invalid trunkNumber");
    }

    @Test
    public void validateWhenIPAddressEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setIpAddress("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("ipAddress", "ipAddress.empty");
    }

    @Test
    public void validateWhenIPAddressNotValidShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setIpAddress("nope");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("ipAddress", "ipAddress.notValidIp");
    }

    @Test
    public void validateWhenPrimaryUtilityIpAddressEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setPrimaryUtilityIpAddress("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("primaryUtilityIpAddress", "primaryUtilityIpAddress.empty");
    }

    @Test
    public void validateWhenPrimaryUtilityIpAddressNotValidShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setPrimaryUtilityIpAddress("nope");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("primaryUtilityIpAddress", "primaryUtilityIpAddress.notValidIp");
    }

    @Test
    public void validateWhenSecondaryUtilityIpAddressEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setSecondaryUtilityIpAddress("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("secondaryUtilityIpAddress", "secondaryUtilityIpAddress.empty");
    }

    @Test
    public void validateWhenSecondaryUtilityIpAddressNotValidShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setSecondaryUtilityIpAddress("nope");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("secondaryUtilityIpAddress", "secondaryUtilityIpAddress.notValidIp");
    }

    @Test
    public void validateWhenNetworkMaskEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setNetworkMask("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("networkMask", "networkMask.empty");
    }

    @Test
    public void validateWhenNetworkMaskNotValidShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setNetworkMask("nope");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("networkMask", "networkMask.notValidIp");
    }

    @Test
    public void validateWhenAccessVLANEmptyShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setAccessVLan("");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("accessVLan", "accessVLan.empty");
    }

    @Test
    public void validateWhenAccessVLANNotUniqueShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setAccessVLan("11");
        given(sipDomainLogic.circuitWithKeyExists(Fixtures.REGION + "-11")).willReturn(true);

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("accessVLan", "accessVLan.nonUnique");
    }

    @Test
    public void validateWhenDefaultGatewayIpAddressNotValidShouldFail() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setDefaultGatewayIpAddress("nope");

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verify(errors).rejectValue("defaultGatewayIpAddress", "defaultGatewayIpAddress.notValidIp");
    }

    @Test
    public void validateWhenAllValidationsPassShouldPass() {
        // Given
        CircuitForm circuitForm = Fixtures.createCircuitForm();
        circuitForm.setAccessVLan("11");
        given(sipDomainLogic.circuitWithKeyExists(Fixtures.REGION + "-11")).willReturn(false);

        // When
        circuitFormValidator.validate(circuitForm, errors);

        // Then
        verifyZeroInteractions(errors);
    }

}
