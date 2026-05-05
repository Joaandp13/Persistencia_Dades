package Model.Objectes;

public class Escalador {
    private int    idEscalador;
    private String nom;
    private String alias;
    private int    edat;
    private String nivell;
    private String viaMaxNivell;
    private String estilPreferit;
    private String historial;


    public Escalador(int idEscalador, String nom, String alias, int edat,
                     String nivell, String viaMaxNivell, String estilPreferit, String historial) {
        setIdEscalador(idEscalador);
        setNom(nom);
        setAlias(alias);
        setEdat(edat);
        setNivell(nivell);
        setViaMaxNivell(viaMaxNivell);
        setEstilPreferit(estilPreferit);
        setHistorial(historial);
    }

    public Escalador(String nom, String alias, int edat,
                     String nivell, String viaMaxNivell, String estilPreferit, String historial) {
        setNom(nom);
        setAlias(alias);
        setEdat(edat);
        setNivell(nivell);
        setViaMaxNivell(viaMaxNivell);
        setEstilPreferit(estilPreferit);
        setHistorial(historial);
    }

    // ── Getters ──
    public int    getIdEscalador()  { return idEscalador; }
    public String getNom()          { return nom; }
    public String getAlias()        { return alias; }
    public int    getEdat()         { return edat; }
    public String getNivell()       { return nivell; }
    public String getViaMaxNivell() { return viaMaxNivell; }
    public String getEstilPreferit(){ return estilPreferit; }
    public String getHistorial()    { return historial; }

    // ── Setters ──
    public void setIdEscalador(int idEscalador) {
        if (idEscalador < 0)
            throw new IllegalArgumentException("L'id no pot ser negativa");
        this.idEscalador = idEscalador;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank())
            throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom.trim();
    }

    public void setAlias(String alias) {
        // alias es opcional, puede ser null
        this.alias = (alias != null) ? alias.trim() : null;
    }

    public void setEdat(int edat) {
        if (edat < 10)
            throw new IllegalArgumentException("L'edat mínima és 10 anys");
        this.edat = edat;
    }

    public void setNivell(String nivell) {
        // Cubre: 4, 4+, 5, 6a, 6a+, 7b+, 8c, 9c+
        if (nivell != null && !nivell.matches("^([4-9][abc]?[+]?)$"))
            throw new IllegalArgumentException("Nivell invàlid: " + nivell);
        this.nivell = nivell;
    }

    public void setViaMaxNivell(String viaMaxNivell) {
        // opcional, puede ser null
        this.viaMaxNivell = viaMaxNivell;
    }

    public void setEstilPreferit(String estilPreferit) {
        if (estilPreferit != null &&
                !estilPreferit.equals("esportiva") &&
                !estilPreferit.equals("classica")  &&
                !estilPreferit.equals("gel"))
            throw new IllegalArgumentException("Estil invàlid: " + estilPreferit);
        this.estilPreferit = estilPreferit;
    }

    public void setHistorial(String historial) {
        // PENDENT segons enunciat, sense validació de moment
        this.historial = historial;
    }

    // ── toString ──
    @Override
    public String toString() {
        return  "Escalador {" +
                "\n  id       = " + idEscalador +
                "\n  nom      = " + nom +
                "\n  alias    = " + alias +
                "\n  edat     = " + edat +
                "\n  nivell   = " + nivell +
                "\n  via màx  = " + viaMaxNivell +
                "\n  estil    = " + estilPreferit +
                "\n}";
    }
}