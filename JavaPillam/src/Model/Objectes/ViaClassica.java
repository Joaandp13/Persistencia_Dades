// Defineix el paquet on s'ubica el POJO (Plain Old Java Object) que representa una Via Clàssica.
package Model.Objectes;

// Importa la classe LocalDate per gestionar les dates de restriccions i estats.
import java.time.LocalDate;

// Classe que modela una via d'escalada clàssica (habitualment de diversos llargs i autoprotecció).
public class ViaClassica {
    // Atributs privats que reflecteixen l'estructura de dades a la persistència.
    private int idVia;
    private String nom;
    private int idSector;
    private Integer llargadaTotal; // nullable, ja que sovint es calcula sumant els metres de cada tram/llarg.
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

    // Constructor buit per defecte.
    public ViaClassica() {}

    // Constructor complet amb tots els paràmetres, incloent l'ID d'objecte.
    public ViaClassica(int idVia, String nom, int idSector, Integer llargadaTotal, String grau,
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

    // Constructor sense ID ni llargada total (útil quan la llargada es deriva de la suma de trams).
    public ViaClassica(String nom, int idSector, String grau, String orientacio, String estat,
                       LocalDate dataIniciNoApte, LocalDate dataFiNoApte, String ancoratge,
                       String tipusRoca, Integer idCreador, String restriccions) {
        setNom(nom);
        setIdSector(idSector);
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

    // ── Getters (Mètodes d'accés) ──
    public int getIdVia()                 { return idVia; }
    public String getNom()                { return nom; }
    public int getIdSector()              { return idSector; }
    public Integer getLlargadaTotal()     { return llargadaTotal; }
    public String getGrau()               { return grau; }
    public String getOrientacio()         { return orientacio; }
    public String getEstat()              { return estat; }
    public LocalDate getDataIniciNoApte() { return dataIniciNoApte; }
    public LocalDate getDataFiNoApte()    { return dataFiNoApte; }
    public String getAncoratge()          { return ancoratge; }
    public String getTipusRoca()          { return tipusRoca; }
    public Integer getIdCreador()         { return idCreador; }
    public String getRestriccions()       { return restriccions; }

    // ── Setters (Mètodes de modificació amb validacions de domini) ──

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

    public void setLlargadaTotal(Integer llargadaTotal) { this.llargadaTotal = llargadaTotal; }

    public void setGrau(String grau)                    { this.grau          = grau; }

    public void setOrientacio(String orientacio) {
        // Valida que l'orientació segueixi el format de punts cardinals (N, S, SE, etc.).
        if (orientacio != null && !orientacio.matches("^(N|NE|NO|E|O|SE|SO|S)$"))
            throw new IllegalArgumentException("Orientació invàlida: " + orientacio);
        this.orientacio = orientacio;
    }

    public void setDataIniciNoApte(LocalDate d) { this.dataIniciNoApte = d; }

    public void setDataFiNoApte(LocalDate d)    { this.dataFiNoApte    = d; }

    public void setEstat(String estat) {
        // Restricció de valors per l'estat de la via.
        if (estat != null && !estat.equals("apte") && !estat.equals("construccio") && !estat.equals("tancada"))
            throw new IllegalArgumentException("Estat invàlid: " + estat);
        // Si l'estat indica que no es pot escalar, cal una data de previsió de reobertura (dataFiNoApte).
        if ((estat != null && !estat.equals("apte")) && this.dataFiNoApte == null)
            throw new IllegalArgumentException("Cal definir data_fi_no_apte si l'estat no és apte");
        this.estat = estat;
    }

    public void setAncoratge(String ancoratge) {
        // L'escalada clàssica és híbrida: admet tant material d'autoprotecció (friends, tascons) com fixos (spits, parabolts).
        if (ancoratge != null &&
            !ancoratge.equals("friends") && !ancoratge.equals("tascons") &&
            !ancoratge.equals("bagues")  && !ancoratge.equals("pitons") &&
            !ancoratge.equals("tricams") && !ancoratge.equals("bigbros") &&
            !ancoratge.equals("spits")   && !ancoratge.equals("parabolts") &&
            !ancoratge.equals("quimics"))
            throw new IllegalArgumentException("Ancoratge invàlid per via clàssica: " + ancoratge);
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

    public void setIdCreador(Integer idCreador)      { this.idCreador    = idCreador; }

    public void setRestriccions(String restriccions) { this.restriccions = restriccions; }

    // Representació visual en format String de les dades de la via.
    @Override
    public String toString() {
        return "ViaClassica {" +
               "\n  id         = " + idVia +
               "\n  nom        = " + nom +
               "\n  sector     = " + idSector +
               "\n  llargada   = " + (llargadaTotal != null ? llargadaTotal + "m" : "pendent") +
               "\n  grau       = " + grau +
               "\n  orientacio = " + orientacio +
               "\n  estat      = " + estat +
               "\n  ancoratge  = " + ancoratge +
               "\n  roca       = " + tipusRoca +
               "\n}";
    }
}
