package nz.co.example.dev.integration;

import static org.junit.Assert.assertTrue;
import nz.co.example.dev.domain.model.WSipLayerTwoCircuit;
import nz.co.example.dev.integration.operation.AddWSipLayerTwoCircuit;
import nz.co.example.dev.integration.operation.Operation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LogEntryTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testFromString() throws Exception {
        String encodedString;
        LogEntry originaLogEntry = new LogEntry();
        WSipLayerTwoCircuit circuit = null;
        Operation operation = new AddWSipLayerTwoCircuit(circuit);
        originaLogEntry.setOperation(operation);
        encodedString = LogEntry.toString(originaLogEntry);
        LogEntry logEntry = (LogEntry) LogEntry.fromString(encodedString);
        assertTrue(logEntry.equals(originaLogEntry));
        // assertEquals(originaLogEntry, logEntry);
    }

    /**
     * Same test as testFromString.
     * 
     * @throws Exception
     */
    @Test
    public final void testToStringSerializable() throws Exception {
        testFromString();
    }

    @Test
    public void testParseLogLine() throws Exception {
        LogEntry logEntry = LogEntry
                .parseLogLine("2013-10-08 16:15:06 5255  INFO  operations - {\"@type\":\"nz.co.example.dev.integration.LogEntry\",\"operation\":{\"@type\":\"nz.co.example.dev.integration.operation.AddWSipLayerTwoCircuit\",\"operationType\":{\"name\":\"ADD\",\"ordinal\":0},\"operands\":{\"@type\":\"java.util.ArrayList\",\"@items\":[{\"@type\":\"nz.co.example.dev.domain.model.WSipLayerTwoCircuit\",\"networkInterface\":{\"name\":\"m01-access\",\"subport\":2242,\"description\":\"Connection to Access Network for customer Test01\",\"ipAddress\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-1062731519,\"family\":1},\"primaryUtilityAddress\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-1062731518,\"family\":1},\"secondaryUtilityAddress\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-1062731517,\"family\":1},\"netMask\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-256,\"family\":1},\"gateway\":null,\"heartbeat\":{\"state\":{\"@id\":62,\"displayValue\":\"disabled\",\"name\":\"DISABLED\",\"ordinal\":1}}},\"primaryAccessRealm\":{\"identifier\":\"CS2K-TEST01-01.acc\",\"description\":\"Access Realm for customer Test01\",\"addressPrefix\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":0,\"family\":1},\"networkInterfaces\":\"m01-access:2242\",\"mmInRealm\":{\"@id\":16,\"displayValue\":\"enabled\",\"name\":\"ENABLED\",\"ordinal\":0},\"mmInNetwork\":{\"@ref\":16},\"mmInSameIp\":{\"@ref\":62},\"mmInSystem\":{\"@ref\":16},\"bwCacNonMm\":{\"@ref\":62},\"msmRelease\":{\"@ref\":62},\"accessControlTrustLevel\":{\"@id\":59,\"displayValue\":\"medium\",\"name\":\"MEDIUM\",\"ordinal\":2},\"invalidSignalThreshold\":1,\"averageRateLimit\":0,\"maximumSignalThreshold\":4000,\"untrustedSignalThreshold\":0,\"options\":{\"options\":{\"@type\":\"java.util.Arrays$ArrayList\",\"@items\":[\"+sip-connect-pbx-reg=rewrite-new\"]}},\"parentRealm\":\"CS2K-pabx.acc\",\"symmetricLatching\":null,\"order\":{\"@id\":53,\"name\":\"PRIMARY\",\"ordinal\":0},\"regionCode\":\"auc\"},\"secondaryAccessRealm\":{\"identifier\":\"CS2K-TEST01-01n.acc\",\"description\":\"NAT Access Realm for customer Test01\",\"addressPrefix\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":0,\"family\":1},\"networkInterfaces\":\"m01-access:2242\",\"mmInRealm\":{\"@ref\":16},\"mmInNetwork\":{\"@ref\":16},\"mmInSameIp\":{\"@ref\":62},\"mmInSystem\":{\"@ref\":16},\"bwCacNonMm\":{\"@ref\":62},\"msmRelease\":{\"@ref\":62},\"accessControlTrustLevel\":{\"@ref\":59},\"invalidSignalThreshold\":1,\"averageRateLimit\":0,\"maximumSignalThreshold\":4000,\"untrustedSignalThreshold\":0,\"options\":{\"options\":{\"@type\":\"java.util.Arrays$ArrayList\",\"@items\":[\"+sip-connect-pbx-reg=rewrite-new\"]}},\"parentRealm\":\"CS2K-TEST01-01.acc\",\"symmetricLatching\":{\"@ref\":16},\"order\":{\"@id\":38,\"name\":\"SECONDARY\",\"ordinal\":1},\"regionCode\":\"auc\"},\"primarySipInterface\":{\"order\":{\"@ref\":53},\"realmId\":\"CS2K-TEST01-01.acc\",\"state\":{\"@ref\":16},\"description\":\"Access SIP-Interface for customer Test01\",\"udpSipPort\":{\"address\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-1062731519,\"family\":1},\"port\":5060,\"anonMode\":{\"@id\":34,\"displayValue\":\"registered\",\"name\":\"REGISTERED\",\"ordinal\":3},\"transportProtocol\":{\"@id\":32,\"name\":\"UDP\",\"ordinal\":0}},\"tcpSipPort\":{\"address\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-630912504,\"family\":1},\"port\":5060,\"anonMode\":{\"@ref\":34},\"transportProtocol\":{\"name\":\"TCP\",\"ordinal\":1}},\"redirectAction\":{\"@id\":28,\"displayValue\":\"proxy\",\"name\":\"PROXY\",\"ordinal\":1},\"contactMode\":{\"@id\":25,\"displayValue\":\"none\",\"name\":\"NONE\",\"ordinal\":0},\"natTraversal\":{\"displayValue\":\"none\",\"name\":\"NONE\",\"ordinal\":0},\"registrationCaching\":{\"@ref\":16},\"natInterval\":0,\"minRegExpire\":3600,\"registrationInterval\":3600,\"routeToRegistrar\":{\"@ref\":16},\"options\":{\"options\":{\"@type\":\"java.util.Arrays$ArrayList\",\"@items\":[\"+reg-via-key\",\"+reg-no-port-match\"]}},\"sipImsFeature\":{\"@ref\":16},\"trustMode\":{\"@id\":13,\"displayValue\":\"registered\",\"name\":\"REGISTERED\",\"ordinal\":3},\"stopRecurse\":[401,407]},\"secondarySipInterface\":{\"order\":{\"@ref\":38},\"realmId\":\"CS2K-TEST01-01n.acc\",\"state\":{\"@ref\":16},\"description\":\"NAT Access SIP-Interface for customer Test01\",\"udpSipPort\":{\"address\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-1062731519,\"family\":1},\"port\":5080,\"anonMode\":{\"@ref\":34},\"transportProtocol\":{\"@ref\":32}},\"tcpSipPort\":null,\"redirectAction\":{\"@ref\":28},\"contactMode\":{\"@ref\":25},\"natTraversal\":{\"displayValue\":\"always\",\"name\":\"ALWAYS\",\"ordinal\":1},\"registrationCaching\":{\"@ref\":16},\"natInterval\":30,\"minRegExpire\":3600,\"registrationInterval\":3600,\"routeToRegistrar\":{\"@ref\":16},\"options\":{\"options\":{\"@type\":\"java.util.Arrays$ArrayList\",\"@items\":[\"+reg-via-key\",\"+reg-no-port-match\"]}},\"sipImsFeature\":{\"@ref\":16},\"trustMode\":{\"@ref\":13},\"stopRecurse\":[401,407]},\"steeringPool\":{\"realmId\":\"CS2K-TEST01-01.acc\",\"ipAddress\":{\"@type\":\"java.net.Inet4Address\",\"hostName\":null,\"address\":-1062731519,\"family\":1},\"startPort\":16384,\"endPort\":17384,\"networkInterface\":\":0\"},\"region\":\"auc\"}]}},\"when\":null}");
        System.out.println(logEntry.getWhen());
        assertTrue(null != logEntry.getWhen());
    }
}
