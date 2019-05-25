package me.palla.input;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Christian Ferrareis
 * @version 1.0
 * @brief Classe rappresentate la sottoscrizione
 */
public class InputSubscription {

    /**L'ultimo dato ricevuto*/
    private InputData lastData;
    /**Semaforo per sincronizzazione*/
    private Semaphore sem;
    
    /** @brief Costrutto senza parametri
     * Inizializza solamente il semaforo a 0
     */
    protected InputSubscription() {
        sem = new Semaphore(0);
    }

    /** @brief Serve per prendere l'ultimo valore letto
     * Metodo sincronizzato da un semaforo, che segnala la presenza di un dato
     *  o meno, per ricavare l'ultimo valore letto in input.
     * @retun Ritorna l'ultimo valore letto
     */
    public InputData poll() {
        try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(InputSubscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastData;
    }

    /** @brief "Carica" un nuovo dato
     * Metodo per registrare un nuovo dato ricevuto come parametro
     * @param letto Il nuovo valore in input da registrare
     */
    void post(InputData letto) {
        lastData = letto;
        sem.release();
    }
}
