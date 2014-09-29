package jojoriot.references;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reference is the parent class of every Bibtex reference type. It has a set of
 * keys called valid fields in this project. This set depends on the reference
 * type which the extending class represents. A reference also contains a
 * key-value-mapping of the keys denoting field names and values. The methods
 * of reference object manipulate the state of this map.
 *
 * @author John Lång <jllang@cs.helsinki.fi>
 */
public abstract class Reference {

    /**
     * A set enumerating all the acceptable field identifiers (or keys). A
     * reference to a single instance of this set should be contained in the
     * extending class as a constant for avoiding unnecessary repeated object
     * creations during constructor invocations.
     */
    //private final Set<String> validFields;
    private final List<String> requiredFields;
    private final List<String> optionalFields;
    
    /**
     * A code that identifies each BibText references.
     * Entered by user.
     */
    private final String identifier;

    /**
     * A key-value-mapping containing the bibtex data of this reference object.
     */
    private final LinkedHashMap<String, String> data = new LinkedHashMap<>();

    /**
     * Creates a new Reference consisting of the valid fields specified as the
     * first argument and the keys and values of all the obligatory fields.
     *
     * @param requiredFields   A set containing all the required field keys.
     * @param optionalFields A set containing all the optional field keys
     * @param data          A map containing all the obligatory fields.
     */
    Reference(final String identifier, final List<String> requiredFields, final List<String> optionalFields) {
        this.requiredFields = requiredFields;
        this.optionalFields = optionalFields;
        this.identifier = identifier;
    }

    /**
     * Stores a key/value-pair into the reference object.
     *
     * @param key   The name of the bibtex field to be stored in this reference
     * object.
     * @param value The value of the bibtex field.
     * @throws IllegalArgumentException If the key-value-pair is invalid.
     */
    public void put(final String key, final String value)
            throws IllegalArgumentException {
        if (requiredFields.contains(key) || optionalFields.contains(key)) {
            checkvalue(key, value);
            data.put(key, value);
        } else {
            throw new IllegalArgumentException("Invalid field identifier \"" +
                    key + "\" for reference type \"" +
                    this.getClass().getSimpleName() + "\".");
        }
    }

    /**
     * Retrieves the value of the field specified by the given key.
     *
     * @param key   Identifies the name of the bibtex field.
     * @return      The value associated with the given key. <tt>null</tt> if
     * there is no such value.
     */
    public String get(final String key) {
        // Pitäisiköhän heittää exception jos käyttäjä yrittää pyytää
        // epäkelvollisen kentän arvoa?
        return data.get(key);
    }

    /**
     * Retrieves the whole data map.
     *
     * @return The data map
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * Removes a key-value-pair (or bibtex field) from the mapping.
     *
     * @param key A key specifying which key-value-pair is to be removed.
     */
    public void delete(final String key) {
        data.remove(key);
    }

    /**
     * The purpose of this hook method is to ensure that the value being added
     * is valid. If it's not, the method throws an exception.
     *
     * @param key   The key corresponding to the bibtex field.
     * @param value The value of this field.
     * @throws IllegalArgumentException If the value doesn't meet its
     * requirements.
     */
    abstract void checkvalue(final String key, final String value)
            throws IllegalArgumentException;

}
