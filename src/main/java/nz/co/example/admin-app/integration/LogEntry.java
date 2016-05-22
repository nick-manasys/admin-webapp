package nz.co.example.dev.integration;

import java.io.IOException;
import java.io.Serializable;

import nz.co.example.dev.integration.operation.Operation;

import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.google.common.base.Objects;

/**
 * 
 */
public class LogEntry implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(LogEntry.class);

    private static final String LOGLINE_TAG = "INFO  operations - ";

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");

    /**
     * 
     */
    private static final long serialVersionUID = 4096096930183900645L;

    private Operation operation;

    private Instant when;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LogEntry logEntry = new LogEntry();
        System.out.println("\n\nOriginal object");
        System.out.println(logEntry);
        String string = toString(logEntry);
        System.out.println("\n\nEncoded serialized version ");
        System.out.println(string);
        LogEntry some = (LogEntry) fromString(string);
        System.out.println("\n\nReconstituted object");
        System.out.println(some);

    }

    /**
     * @param logLine
     * @return
     */
    public static LogEntry parseLogLine(String logLine) {
        LogEntry logEntry;
        logger.info("logLine='" + logLine + "'");
        int index = logLine.indexOf(LOGLINE_TAG);

        if (-1 != index) {
            String json = logLine.substring(index + LOGLINE_TAG.length(), logLine.length());
            logger.info("json='" + json + "'");
            logEntry = fromString(json);
        } else {
            logEntry = new LogEntry();
        }

        String dateString = logLine.substring(0, index);
        logger.info("dateString=" + dateString);
        dateString = dateString.substring(0, 19);
        Instant when = Instant.parse(dateString, dateTimeFormatter);
        logEntry.setWhen(when);
        return logEntry;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * Read the object from Base64 string.
     */
    public static LogEntry fromString(String s) {
        // byte[] data = new BASE64Decoder().decodeBuffer(s);
        // ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        // Object o = ois.readObject();
        // ois.close();
        LogEntry o = null;
        try {
            o = (LogEntry) JsonReader.jsonToJava(s);
            logger.info(o.getOperation().toString());
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    public static String toString(LogEntry o) throws IOException {
        String json = JsonWriter.objectToJson(o);
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // ObjectOutputStream oos = new ObjectOutputStream(baos);
        // oos.writeObject(o);
        // oos.close();
        // return new String(new BASE64Encoder().encode(baos.toByteArray()));
        return json;
    }

    @Override
    public String toString() {
        return "LogEntry(" + this.getOperation() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getOperation().hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof LogEntry) {
            final LogEntry other = (LogEntry) obj;
            return Objects.equal(this.getOperation(), other.getOperation()); // && Objects.equal(children,
                                                                             // other.children);
        } else {
            return false;
        }
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }
}
