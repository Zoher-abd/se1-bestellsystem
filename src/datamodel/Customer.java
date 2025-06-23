package datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Repräsentiert Kund*innen mit ID, Namen und Kontaktliste.
 */
public class Customer {

    /** Datenfelder (Standardwerte gemäss UML) */
    private long id = -1;
    private String lastName  = "";
    private String firstName = "";
    private final List<String> contacts = new ArrayList<>();

    /** Parameterloser Standard-Konstruktor. */
    public Customer() { }

    /**
     * Konstruktor mit Einzel-String-Name.
     * @param name Vollständiger Name, der aufgesplittet wird
     */
    public Customer(String name) {
        setName(name);
    }

    // ---------------------------------------------------------------------
    // ID
    // ---------------------------------------------------------------------

    /** @return interne ID */
    public long getId() {
        return id;
    }

    /**
     * Setzt eine neue ID.
     * @param id neue ID
     * @return dieses Objekt (Fluent API)
     */
    public Customer setId(long id) {
        this.id = id;
        return this;
    }

    // ---------------------------------------------------------------------
    // Namens­teile
    // ---------------------------------------------------------------------

    /** @return Nachname */
    public String getLastName() {
        return lastName;
    }

    /** @return Vorname(n) */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setzt Vor- und Nachname direkt.
     * @param first Vorname(n)
     * @param last  Nachname
     * @return dieses Objekt (Fluent API)
     */
    public Customer setName(String first, String last) {
        this.firstName = trim(Objects.requireNonNull(first));
        this.lastName  = trim(Objects.requireNonNull(last));
        return this;
    }

    /**
     * Setzt Namen aus Einzel-String.
     * @param name Vollständiger Name
     * @return dieses Objekt (Fluent API)
     */
    public Customer setName(String name) {
        splitName(name);
        return this;
    }

    // ---------------------------------------------------------------------
    // Kontakte
    // ---------------------------------------------------------------------

    /** @return Anzahl der Kontakte */
    public int contactsCount() {
        return contacts.size();
    }

    /** @return unveränderbare Sicht auf alle Kontakte */
    public Iterable<String> getContacts() {
        return Collections.unmodifiableList(contacts);
    }

    /**
     * Fügt einen Kontakt hinzu.
     * @param contact Kontakt-String
     * @return dieses Objekt (Fluent API)
     */
    public Customer addContact(String contact) {
        contacts.add(trim(Objects.requireNonNull(contact)));
        return this;
    }

    /**
     * Löscht Kontakt an gegebener Position.
     * @param i Index
     * @return 1 (gelöschte Elemente)
     * @throws IndexOutOfBoundsException falls Index ungültig
     */
    public int deleteContact(int i) {
        contacts.remove(i);
        return 1;
    }

    /** Löscht alle Kontakte. */
    public void deleteAllContacts() {
        contacts.clear();
    }

    // ---------------------------------------------------------------------
    // Hilfsmethoden
    // ---------------------------------------------------------------------

    /**
     * Splittet Einzel-String-Name in Vor- und Nachnamen.
     * Siehe README/JavaDoc für Regeln und Beispiele.
     * @param name Einzel-String-Name
     * @throws IllegalArgumentException falls {@code name} null/leer ist
     */
    public void splitName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name may not be null/empty");
        }
        String n = trim(name);
        String[] parts;
        if (n.contains(",") || n.contains(";")) {
            parts = n.split("[,;]", 2);
            lastName  = trim(parts[0]);
            firstName = trim(parts[1]);
        } else {
            parts = n.split("\\s+");
            if (parts.length == 1) {
                firstName = "";
                lastName  = parts[0];
            } else {
                lastName  = parts[parts.length - 1];
                firstName = String.join(" ", java.util.Arrays.copyOf(parts, parts.length - 1));
            }
        }
    }

    /**
     * Entfernt führende/­nachgestellte Leerzeichen, Kommata, Semikola
     * sowie einfache/doppelte Anführungs­zeichen.
     * @param s zu trimmender String
     * @return getrimmter String
     */
    private String trim(String s) {
        s = s.replaceAll("^[\\s\"',;]+", "");
        s = s.replaceAll("[\\s\"',;]+$", "");
        return s.trim().replaceAll("\\s{2,}", " ");
    }
}