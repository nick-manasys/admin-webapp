/**
 * 
 */
package nz.co.example.dev.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the result of an integration call.
 * 
 * Each integration call must have a meaning full description and
 * can optionally have warnings or errors.
 * 
 * @author nivanov
 */
public class IntegrationCallResult {
    
    private String description;
    private List<String> warnings;
    private List<String> errors;
    
    public IntegrationCallResult(String description) {
        super();
        this.description = description;
        this.errors = new ArrayList<String>();
        this.warnings = new ArrayList<String>();
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void addWarnings(String... warnings) {
        this.warnings.addAll(Arrays.asList(warnings));
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addErrors(String errors) {
        this.errors.addAll(Arrays.asList(errors));
    }

    public String getDescription() {
        return description;
    }
    
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }
    
    public boolean hasWarnings()
    {
        return !warnings.isEmpty();
    }

}
