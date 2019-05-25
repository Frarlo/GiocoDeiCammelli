package me.palla.input;

/** @author Davide Borzì
 * @brief Classe singleton in caso non ci sia input
 */
public final class NoInput implements InputData {

    /**L'istanza della classe*/private static final NoInput INSTANCE = new NoInput();

    /** @brief Costruttore
     * Csotruttore vuoto, senza parametri e privato per singleton
     */
    private NoInput() {
    }

    /** @brief Metodo per prender l'istanza della classe
     * @return Ritorna l'istanza della classe
     */
    public static NoInput instance() {
        return INSTANCE;
    }
}
