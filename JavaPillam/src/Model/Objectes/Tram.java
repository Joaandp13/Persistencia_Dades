// Defineix el paquet on s'ubica el POJO que representa un Tram (o llarg) d'una via d'escalada.
package Model.Objectes;

// Classe que modela un tram individual. Una via pot estar composta per un o diversos trams.
public class Tram {

    // Atributs privats que mapegen les columnes de la taula 'Tram' a la base de dades.
    private int idTram;                    // Clau Primària (PK) - Identificador únic del tram.
    private Integer idViaClasica;          // Clau Forana (FK) a via clàssica (opcional si és d'altre tipus).
    private Integer idViaEsportiva;        // Clau Forana (FK) a via esportiva (opcional).
    private Integer idViaGel;              // Clau Forana (FK) a via de gel (opcional).
    private int numTram;                   // Ordre del tram dins la via (ex: 1 per al primer llarg L1).
    private int llargada;                  // Metres del tram (validació segons el tipus de via).
    private String grau;                   // Dificultat tècnica específica d'aquest tram.

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor complet amb ID. S'utilitza normalment quan recuperem dades existents de la BDD.
     */
    public Tram(int idTram, Integer idViaClasica, Integer idViaEsportiva, Integer idViaGel,
                int numTram, int llargada, String grau) {
        setIdTram(idTram);
        setIdViaClasica(idViaClasica);
        setIdViaEsportiva(idViaEsportiva);
        setIdViaGel(idViaGel);
        setNumTram(numTram);
        setLlargada(llargada); // Important: cridar després de set els IDs de via per a la validació de metres.
        setGrau(grau);
    }

    /**
     * Constructor sense ID. S'utilitza per crear nous trams que encara no s'han inserit a la BDD (ID autoincremental).
     */
    public Tram(Integer idViaClasica, Integer idViaEsportiva, Integer idViaGel,
                int numTram, int llargada, String grau) {
        setIdViaClasica(idViaClasica);
        setIdViaEsportiva(idViaEsportiva);
        setIdViaGel(idViaGel);
        setNumTram(numTram);
        setLlargada(llargada);
        setGrau(grau);
    }

    // ==================== GETTERS ====================

    public int getIdTram()            { return idTram; }
    public Integer getIdViaClasica()  { return idViaClasica; }
    public Integer getIdViaEsportiva(){ return idViaEsportiva; }
    public Integer getIdViaGel()      { return idViaGel; }
    public int getNumTram()           { return numTram; }
    public int getLlargada()          { return llargada; }
    public String getGrau()           { return grau; }

    // ==================== SETTERS AMB VALIDACIÓ ====================

    public void setIdTram(int idTram) {
        if (idTram < 0) {
            throw new IllegalArgumentException("L'id del tram no pot ser negatiu");
        }
        this.idTram = idTram;
    }

    // Cada cop que assignem una via, comprovem que no n'hi hagi cap altra d'assignada (exclusivitat).
    public void setIdViaClasica(Integer idViaClasica) {
        this.idViaClasica = idViaClasica;
        validarUnicTipusVia();
    }

    public void setIdViaEsportiva(Integer idViaEsportiva) {
        this.idViaEsportiva = idViaEsportiva;
        validarUnicTipusVia();
    }

    public void setIdViaGel(Integer idViaGel) {
        this.idViaGel = idViaGel;
        validarUnicTipusVia();
    }

    public void setNumTram(int numTram) {
        if (numTram < 1) {
            throw new IllegalArgumentException("El número de tram ha de ser com a mínim 1 (L1)");
        }
        this.numTram = numTram;
    }

    public void setLlargada(int llargada) {
        // Lògica de negoci: La llargada permesa varia segons la disciplina d'escalada.
        if (idViaEsportiva != null) {
            // Per a vies esportives (normalment d'un sol llarg o trams curts), entre 5 i 30m.
            if (llargada < 5 || llargada > 30)
                throw new IllegalArgumentException("La llargada del tram esportiu ha d'estar entre 5 i 30 metres");
        } else {
            // Per a Clàssica o Gel, els trams solen ser més llargs, d'entre 15 i 30m.
            if (llargada < 15 || llargada > 30)
                throw new IllegalArgumentException("La llargada del tram ha d'estar entre 15 i 30 metres");
        }
        this.llargada = llargada;
    }

    public void setGrau(String grau) {
        if (grau == null || grau.isBlank()) {
            throw new IllegalArgumentException("El grau del tram no pot estar buit");
        }
        // Normalitza el text a majúscules i treu espais sobrants.
        this.grau = grau.trim().toUpperCase();
    }

    // ==================== VALIDACIÓ INTERNA ====================

    /**
     * Mètode privat de suport per garantir la integritat del model:
     * Un objecte Tram és "exclusiu", és a dir, només pot estar lligat a un dels tres tipus de via.
     */
    private void validarUnicTipusVia() {
        int count = 0;
        if (idViaClasica != null)  count++;
        if (idViaEsportiva != null) count++;
        if (idViaGel != null)      count++;

        // Si es detecta que té més d'una ID, llança una excepció per evitar dades inconsistents.
        if (count > 1) {
            throw new IllegalArgumentException(
                    "Un tram només pot pertànyer a UNA via (clàssica, esportiva o gel). " +
                    "No es poden assignar diverses IDs alhora.");
        }
    }
}
