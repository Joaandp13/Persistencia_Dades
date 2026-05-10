// Defineix el paquet on s'ubica el POJO que representa un Escalador.
package Model.Objectes;

/**
 * Classe que modela un Escalador.
 * Conté la informació personal, tècnica i el perfil d'escalada de l'usuari.
 */
public class Escalador {
    // Atributs privats que mapegen les columnes de la taula 'Escalador' a la base de dades.
    private int    idEscalador;
    private String nom;
    private String alias;
    private int    edat;
    private String nivell;         // Grau d'escalada actual de l'escalador.
    private String viaMaxNivell;   // Nom de la via més difícil que ha encadenat.
    private String estilPreferit;  // esportiva, classica o gel.
    private String historial;      // Camp per a notes o historial d'activitat.

    // ── Constructors ──

    /**
     * Constructor complet (amb ID). 
     * S'utilitza per carregar escaladors ja existents a la base de dades.
     */
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

    /**
     * Constructor sense ID. 
     * Útil per registrar nous escaladors (l'ID serà generat per la base de dades).
     */
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

    // ── Getters (Mètodes d'accés) ──
    public int    getIdEscalador()  { return idEscalador; }
    public String getNom()          { return nom; }
    public String getAlias()        { return alias; }
    public int    getEdat()         { return edat; }
    public String getNivell()       { return nivell; }
    public String getViaMaxNivell() { return viaMaxNivell; }
    public String getEstilPreferit(){ return estilPreferit; }
    public String getHistorial()    { return historial; }

    // ── Setters (Mètodes de modificació amb validacions de domini) ──

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
        // L'àlies és opcional; si existeix, se li treuen els espais sobrants.
        this.alias = (alias != null) ? alias.trim() : null;
    }

    public void setEdat(int edat) {
        // Validació d'edat mínima segons regles de negoci (ex: 10 anys).
        if (edat < 10)
            throw new IllegalArgumentException("L'edat mínima és 10 anys");
        this.edat = edat;
    }

    public void setNivell(String nivell) {
        // Validació mitjançant expressió regular per a graus francesos (ex: 5, 6a, 7b+, 9c).
        if (nivell != null && !nivell.matches("^([4-9][abc]?[+]?)$"))
            throw new IllegalArgumentException("Nivell invàlid: " + nivell);
        this.nivell = nivell;
    }

    public void setViaMaxNivell(String viaMaxNivell) {
        // Camp opcional.
        this.viaMaxNivell = viaMaxNivell;
    }

    public void setEstilPreferit(String estilPreferit) {
        // Restricció als tres estats contemplats pel sistema.
        if (estilPreferit != null &&
                !estilPreferit.equals("esportiva") &&
                !estilPreferit.equals("classica")  &&
                !estilPreferit.equals("gel"))
            throw new IllegalArgumentException("Estil invàlid: " + estilPreferit);
        this.estilPreferit = estilPreferit;
    }

    public void setHistorial(String historial) {
        // Camp de text lliure per a informació addicional.
        this.historial = historial;
    }

    // Sobrecarrega el mètode toString per imprimir una fitxa resum de l'escalador.
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
