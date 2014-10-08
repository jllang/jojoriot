package jojoriot.references;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Reference is the parent class of every BibTeX reference type. It has a set of
 * keys called valid fields in this project. This set depends on the reference
 * type which the extending class represents. A reference also contains a
 * key-value-mapping of the keys denoting field names and values. The methods
 * of reference object manipulate the state of this map.
 */
public abstract class Reference {

    /**
     * A set enumerating all the acceptable field identifiers (or keys). A
     * reference to a single instance of this set should be contained in the
     * extending class as a constant for avoiding unnecessary repeated object
     * creations during constructor invocations.
     */
    private final List<String> requiredFields;
    private final List<String> optionalFields;

    /**
     * A key-value-mapping containing the BibTeX data of this reference object.
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
    Reference(final List<String> requiredFields,
            final List<String> optionalFields,
            final LinkedHashMap<String, String> fields) {
        this.requiredFields = requiredFields;
        this.optionalFields = optionalFields;

        for (final Entry<String, String> entry : fields.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }

        for (final String field : this.requiredFields) {
            if (this.get(field) == null) {
                throw new IllegalArgumentException("Missing required field \""
                        + field + "\".");
            }
        }
    }

    public boolean isRequiredField(final String key) {
        return requiredFields.contains(key);
    }

    /**
     * Stores a key/value-pair into the reference object.
     *
     * @param key   The name of the BibTeX field to be stored in this reference
     * object.
     * @param value The value of the BibTeX field.
     * @throws IllegalArgumentException If the key-value-pair is invalid.
     */
    public final void put(final String key, final String value)
            throws IllegalArgumentException {
        if (requiredFields.contains(key) || optionalFields.contains(key)) {
            if (requiredFields.contains(key) && value.equals("")) {
                throw new IllegalArgumentException("Required field \"" + key +
                        "\" missing for reference type \"" +
                        this.getClass().getSimpleName() + "\".");
            }

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
     * @param key   Identifies the name of the BibTeX field.
     * @return      The value associated with the given key. <tt>null</tt> if
     * there is no such value.
     */
    public final String get(final String key) {
        // Pitäisiköhän heittää exception jos käyttäjä yrittää pyytää
        // epäkelvollisen kentän arvoa?
        return data.get(key);
    }

    /**
     * Retrieves the unique reference identifier
     * @return The identifier
     */
    public String getIdentifier() {
        return data.get("identifier");
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
     * Removes a key-value-pair (or BibTeX field) from the mapping.
     *
     * @param key A key specifying which key-value-pair is to be removed.
     */
    public void delete(final String key) throws IllegalArgumentException {
        if (requiredFields.contains(key)) {
            throw new IllegalArgumentException("Can't delete a required " +
                    "field \"" + key + "\" from reference type \"" +
                    this.getClass().getSimpleName() + "\".");
        }

        data.remove(key);
    }

    /**
     * Builds and returns a BibTex-formatted reference.
     *
     * @return
     */
    public final String toBibtexString(){
        final StringBuilder sb = new StringBuilder(32);

        sb.append("@");
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(getIdentifier());
        sb.append(",\n");

        for (final Map.Entry<String, String> entry : data.entrySet()) {
            if (!entry.getValue().equals("") &&
                    !entry.getKey().equals("identifier")) {
                sb.append("    ");
                sb.append(entry.getKey());
                sb.append(" = {");
                sb.append(entry.getValue());
                sb.append("},\n");
            }
        }

        sb.append("}");

        // Replace älphäbet
        String str = sb.toString();
        str = str.replace("ä", "\\\"{a}");
        str = str.replace("Ä", "\\\"{A}");
        str = str.replace("ö", "\\\"{o}");
        str = str.replace("Ö", "\\\"{O}");
        str = str.replace("å", "\\aa");
        str = str.replace("Å", "\\AA");


        return str;
    }

    /**
     * Returns a multiline string that represents the state of the object.
     *
     * @return A Human-readable string containing field names and values.
     */
    public String toPlaintextString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('\n');
        for(final Map.Entry<String, String> entry : data.entrySet()) {
            sb.append("    ");
            sb.append(entry.getKey());
            sb.append(':');
            sb.append(' ');
            sb.append(entry.getValue());
            sb.append('\n');
        }
        return sb.toString();
    }
}
