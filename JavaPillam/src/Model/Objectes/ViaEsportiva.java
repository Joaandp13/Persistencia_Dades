// Defineix el paquet on s'ubica el POJO (Plain Old Java Object) que representa una Via Esportiva.
package Model.Objectes;

// Importa la classe LocalDate per gestionar les dates relacionades amb la disponibilitat de la via.
import java.time.LocalDate;

// Classe que modela una via d'escalada esportiva amb els seus atributs i lògica de validació.
public class ViaEsportiva {
    // Atributs privats que mapegen l'estructura de la taula a la base de dades.
    private int idVia;
    private String nom;
    private int idSector;
    private int llargadaTotal;
    private String grau;
    private String orientacio;
    private String estat;
    private LocalDate dataIniciNoApte;
    private LocalDate dataFiNoApte;
    private String ancoratge;
    private String tipusRoca;
    private Integer idCreador;
    private String restriccions;

    // ── Constructors ──

    // Constructor buit (per defecte).
    public ViaEsportiva() {}

    // Constructor complet amb tots els paràmetres, incloent l'ID.
    public ViaEsportiva(int idVia, String nom, int idSector, int llargadaTotal, String grau,
                        String orientacio, String estat, LocalDate dataIniciNoApte,
                        LocalDate dataFiNoApte, String ancoratge, String tipusRoca,
                        Integer idCreador, String restriccions) {
        setIdVia(idVia);
        setNom(nom);
        setIdSector(idSector);
        setLlargadaTotal(llargadaTotal);
        setGrau(grau);
        setOrientacio(orientacio);
        setDataIniciNoApte(dataIniciNoApte);
        setDataFiNoApte(dataFiNoApte);
        setEstat(estat);
        setAncoratge(ancoratge);
        setTipusRoca(tipusRoca);
        setIdCreador(idCreador);
        setRestriccions(restriccions);
    }

    // Constructor sense l'ID (útil per a noves insercions on l'ID és autoincrementat).
    public ViaEsportiva(String nom, int idSector, int llargadaTotal, String grau,
                        String orientacio, String estat, LocalDate dataIniciNoApte,
                        LocalDate dataFiNoApte, String ancoratge, String tipusRoca,
                        Integer idCreador, String restriccions) {
        setNom(nom);
        setIdSector(idSector);
        setLlargadaTotal(llargadaTotal);
        setGrau(grau);
        setOrientacio(orientacio);
        setDataIniciNoApte(dataIniciNoApte);
        setDataFiNoApte(dataFiNoApte);
        setEstat(estat);
        setAncoratge(ancoratge);
        setTipusRoca(tipusRoca);
        setIdCreador(idCreador);
        setRestriccions(restriccions);
    }

    // ── Getters (Mètodes de consulta) ──
    public int getIdVia()                 { return idVia; }
    public String getNom()                { return nom; }
    public int getIdSector()              { return idSector; }
    public int getLlargadaTotal()         { return llargadaTotal; }
    public String getGrau()               { return grau; }
    public String getOrientacio()         { return orientacio; }
    public String getEstat()              { return estat; }
    public LocalDate getDataIniciNoApte() { return dataIniciNoApte; }
    public LocalDate getDataFiNoApte()    { return dataFiNoApte; }
    public String getAncoratge()          { return ancoratge; }
    public String getTipusRoca()          { return tipusRoca; }
    public Integer getIdCreador()         { return idCreador; }
    public String getRestriccions()       { return restriccions; }

    // ── Setters (Mètodes de modificació amb validacions de negoci) ──

    public void setIdVia(int idVia) {
        if (idVia < 0) throw new IllegalArgumentException("L'id no pot ser negatiu");
        this.idVia = idVia;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom;
    }

    public void setIdSector(int idSector) {
        if (idSector <= 0) throw new IllegalArgumentException("El sector no és vàlid");
        this.idSector = idSector;
    }

    public void setLlargadaTotal(int llargadaTotal) {
        // Validació: una via esportiva sol tenir una llargada limitada pel cordam (un llarg).
        if (llargadaTotal < 5 || llargadaTotal > 30)
            throw new IllegalArgumentException("La llargada ha de ser entre 5 i 30m");
        this.llargadaTotal = llargadaTotal;
    }

    public void setGrau(String grau) {
        // Expressió regular per validar el format de grau francès (ex: 6a, 7b+, 5).
        if (grau != null && !grau.matches("^[4-9][abc]?[+]?$"))
            throw new IllegalArgumentException("Grau invàlid: " + grau);
        this.grau = grau;
    }

    public void setOrientacio(String orientacio) {
        if (orientacio != null && !orientacio.matches("^(N|NE|NO|E|O|SE|SO|S)$"))
            throw new IllegalArgumentException("Orientació invàlida: " + orientacio);
        this.orientacio = orientacio;
    }

    public void setDataIniciNoApte(LocalDate d) { this.dataIniciNoApte = d; }

    public void setDataFiNoApte(LocalDate d)    { this.dataFiNoApte    = d; }

    public void setEstat(String estat) {
        if (estat != null && !estat.equals("apte") && !estat.equals("construccio") && !estat.equals("tancada"))
            throw new IllegalArgumentException("Estat invàlid: " + estat);
        // Regla: si no és "apte", cal saber fins quan (per evitar bloquejos infinits sense data).
        if ((estat != null && !estat.equals("apte")) && this.dataFiNoApte == null)
            throw new IllegalArgumentException("Cal definir data_fi_no_apte si l'estat no és apte");
        this.estat = estat;
    }

    public void setAncoratge(String ancoratge) {
        // L'escalada esportiva es caracteritza per ancoratges fixos i mecànics/químics.
        if (ancoratge != null &&
                !ancoratge.equals("spits") && !ancoratge.equals("parabolts") && !ancoratge.equals("quimics"))
            throw new IllegalArgumentException("Ancoratge invàlid per via esportiva: " + ancoratge);
        this.ancoratge = ancoratge;
    }

    public void setTipusRoca(String tipusRoca) {
        if (tipusRoca != null &&
                !tipusRoca.equals("conglomerat") && !tipusRoca.equals("granit") &&
                !tipusRoca.equals("calcaria")    && !tipusRoca.equals("arenisca") &&
                !tipusRoca.equals("altres"))
            throw new IllegalArgumentException("Tipus de roca invàlid: " + tipusRoca);
        this.tipusRoca = tipusRoca;
    }

    public void setIdCreador(Integer idCreador)      { this.idCreador   = idCreador; }

    public void setRestriccions(String restriccions) { this.restriccions = restriccions; }

    // Representació en text de l'objecte.
    @Override
    public String toString() {
        return "ViaEsportiva {" +
                "\n  id         = " + idVia +
                "\n  nom        = " + nom +
                "\n  sector     = " + idSector +
                "\n  llargada   = " + llargadaTotal + "m" +
                "\n  grau       = " + grau +
                "\n  orientacio = " + orientacio +
                "\n  estat      = " + estat +
                "\n  ancoratge  = " + ancoratge +
                "\n  roca       = " + tipusRoca +
                "\n}";
    }
}
