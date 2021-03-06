package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.util.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Classe che gestisce la matrice di vasche
 * @author Mattia Broch
 * @version 1.0
 */
public class PoolList {

    /** @brief Bordo da uitilizzare tra due vasche adiacenti */
    private static final int POOLS_BORDER = 10;

    /** @brief La lista che verrà gestita come una matrice di vasche */
    private List<PoolEntity> list;

    /** @brief L'indice X della matrice di vasche */
    private final int columns;
    /** @brief L'indice Y della matrice di vasche */
    private final int rows;

    /** @brief La coordinata X dell'ultima vasca disegnata, usata per calcolare dove disegnare la prossima vasca */
    private float lastX;
    /** @brief La coordinata Y dell'ultima vasca disegnata, usata per calcolare dove disegnare la prossima vasca */
    private float lastY;
    /** @brief La lunghezza di una vasca sull'asse X */
    private float lenghtX;
    /** @brief La lunghezza di una vasca sull'asse Y */
    private float lenghtY;
    /** @brief Il margine a sinistra, usato per disegnare le vasche */
    private final int border = 100;

    /**
     * @brief Costruttore che inizializza la classe e richiama i metodi per trovare le dimensioni di una vasca
     *         in base alla dimensione dello schermo e l'aggiunta delle vasche all lista con le giuste coordinate e
     *         dimensioni
     */
    public PoolList(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        lastX = border;
        lastY = border;
        this.list = new ArrayList<>();

        setLenght();
        addAll();
    }

    /**
     * @brief Metodo che ricava le dimensioni di una vasca con le attuali dimensioni dello schermo e il numero
     *         delle vasche
     */
    public void setLenght() {
        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        lenghtX = (res.getScaledWidth() - (border * 2)) / columns;
        lenghtY = (res.getScaledHeight() - (border * 2)) / rows;
    }

    /**
     * @brief Metodo che aggiunge tutte le vasche alla lista list
     */
    public void addAll() {

        for (int i = 0; i < rows; i++) {

            float topBorder = i != 0 ? POOLS_BORDER : Float.MAX_VALUE; // If it's the first row, upper border is infinite
            float bottomBorder = i + 1 != rows ? POOLS_BORDER : Float.MAX_VALUE; // If it's the last row, bottom border is infinite

            for (int j = 0; j < columns; j++) {

                float leftBorder = j != 0 ? POOLS_BORDER : Float.MAX_VALUE; // If it's the first column, left border is infinite
                float rightBorder = j + 1 != columns ? POOLS_BORDER : Float.MAX_VALUE; // If it's the last column, right border is infinite

                addPool(new PoolEntity(
                        lastX, lastY,
                        lenghtX, lenghtY,
                        topBorder, bottomBorder,
                        leftBorder, rightBorder));
                lastX += lenghtX + 2;
            }
            lastX = border;
            lastY += lenghtY + 2;
        }

        // For each pools set the nearby pools

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final PoolEntity currPool = getAt(i, j);

                if(i != 0) // If it's not the first row
                    currPool.setTopPool(getAt(i - 1, j));
                if(i + 1 != rows)  // If it's not the last row
                    currPool.setBottomPool(getAt(i + 1, j));
                if(j != 0) // If it's not the first column
                    currPool.setLeftPool(getAt(i, j - 1));
                if(j + 1 != columns) // If it's not the last column
                    currPool.setRightPool(getAt(i, j + 1));
            }
        }
    }

    /**
     * @brief Metodo che aggiunge una vasca alla lista list
     *
     * @param pool la vasca da aggiungere alla lista list
     */
    private void addPool(PoolEntity pool) {
        if (list.size() < columns * rows) {
            list.add(pool);
        }
    }

    /**
     * @brief Restituisce la vasca alla posizione corrente, trattando la lista come matrice
     *
     * @param row    riga della matrice
     * @param column colonna della matrice
     * @return la vasca alla posizione richiesta
     */
    private PoolEntity getAt(int row, int column) {
        return list.get(row * columns + column);
    }

    /** @brief Metodo get che restituisce columns */
    public int getColumns() {
        return columns;
    }

    /** @brief Metodo get che restituisce rows */
    public int getRows() {
        return rows;
    }

    /** @brief Metodo get che restituisce la lista list */
    public List<PoolEntity> getList() {
        return list;
    }
}

