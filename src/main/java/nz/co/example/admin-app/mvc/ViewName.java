/**
 * 
 */
package nz.co.example.dev.mvc;

/**
 * Enumeration to hold the view names
 * 
 * @author nivanov
 * 
 */
public enum ViewName {

    HOME("home"), 
    ERROR("error"), 
    LIST_CIRCUITS("listCircuits"), 
    REDIRECT_NEW_CIRCUIT("redirect:/newCircuit"), 
    NEW_CIRCUIT("newCircuit"), 
    REDIRECT_LIST_CIRCUITS("redirect:/listCircuits"), 
    EDIT_CIRCUIT("editCircuit"),
    LOG("log");

    private String viewName;

    private ViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String toString() {
        return viewName;
    }

}
