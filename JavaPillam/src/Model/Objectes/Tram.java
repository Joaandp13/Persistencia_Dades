package Model.Objectes;

public class Tram {

    private int idTram;                    // PK - obligatori (no pot ser null)
    private Integer idViaClasica;          // FK a via_clasica (pot ser null)
    private Integer idViaEsportiva;        // FK a via_esportiva (pot ser null)
    private Integer idViaGel;              // FK a via_gel (pot ser null)
    private int numTram;                   // Número de tram (1 = L1, 2 = L2, ...)
    private int llargada;                  // Longitud del tram en metres (15-30)
    private String grau;                   // Grau de dificultat del tram

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor complet (amb ID) - per consultes de la base de dades.
     * Només es pot assignar UNA de les tres IDs de via (clàssica, esportiva o gel).
     */
    public Tram(int idTram, Integer idViaClasica, Integer idViaEsportiva, Integer idViaGel,
                int numTram, int llargada, String grau) {
        setIdTram(idTram);
        setIdViaClasica(idViaClasica);
        setIdViaEsportiva(idViaEsportiva);
        setIdViaGel(idViaGel);
        setNumTram(numTram);
        setLlargada(llargada);
        setGrau(grau);
    }

    /**
     * Constructor sense ID - per a insercions noves.
     * Només es pot assignar UNA de les tres IDs de via.
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
        if (llargada < 15 || llargada > 30) {
            throw new IllegalArgumentException("La llargada del tram ha d'estar entre 15 i 30 metres");
        }
        this.llargada = llargada;
    }

    public void setGrau(String grau) {
        if (grau == null || grau.isBlank()) {
            throw new IllegalArgumentException("El grau del tram no pot estar buit");
        }
        this.grau = grau.trim().toUpperCase();
    }

    // ==================== VALIDACIÓ ====================

    /**
     * Assegura que només UNA de les tres FK estigui informada (no null).
     * Les altres dues han de ser obligatòriament null.
     */
    private void validarUnicTipusVia() {
        int count = 0;
        if (idViaClasica != null)  count++;
        if (idViaEsportiva != null) count++;
        if (idViaGel != null)      count++;

        if (count > 1) {
            throw new IllegalArgumentException(
                    "Un tram només pot pertànyer a UNA via (clàssica, esportiva o gel). " +
                            "No es poden assignar diverses IDs alhora.");
        }
    }
}