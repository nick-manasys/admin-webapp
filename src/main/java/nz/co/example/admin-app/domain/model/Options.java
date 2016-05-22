/**
 * 
 */
package nz.co.example.dev.domain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Encapsulates the options that can be set.
 * 
 * @author nivanov
 */
public class Options implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8736350369996259062L;

    private List<String> options;

    /**
     * Required for JSON
     */
    public Options() {
        // EMPTY
    }

    public Options(String... options) {
        this.options = Arrays.asList(options);
    }

    public void addOptions(String... options) {
        this.options.addAll(Arrays.asList(options));
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        StringBuilder optionsBuilder = new StringBuilder();
        for (String option : options) {
            optionsBuilder.append(String.format("options %s%n", option));
        }
        return optionsBuilder.toString();
    }

}
