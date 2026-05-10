// Defineix el paquet on s'ubica el POJO (Plain Old Java Object) que representa una Via de Gel.
package Model.Objectes;

// Importa la classe LocalDate per gestionar les dates d'estat i restriccions.
import java.time.LocalDate;

// Classe que modela una via d'escalada en gel amb els seus atributs i validacions.
public class ViaGel {
    // Atributs privats de la classe que mapegen les columnes de la base de dades.
    private int idVia;
    private String nom;
    private int idSector;
    private Integer llargadaTotal; // Utilitza Integer per permetre valors nuls (null).
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

    // Constructor buit necessari per a frameworks de persistència o inicialitzacions genèriques.
    public ViaGel() {}

    // Constructor complet amb tots els paràmetres, incloent l'ID i la llargada.
    public ViaGel(int idVia, String nom, int idSector, Integer llargadaTotal, String grau,
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

    // Constructor sense ID (per a noves insercions on l'ID és autoincremental) ni llargada total.
    public ViaGel(String nom, int idSector, String grau, String orientacio, String estat,
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
        // Valida que l'identificador no sigui un número negatiu.
        if (idVia < 0) throw new IllegalArgumentException("L'id no pot ser negatiu");
        this.idVia = idVia;
    }

    public void setNom(String nom) {
        // Valida que el nom no sigui nul ni contingui només espais en blanc.
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom;
    }

    public void setIdSector(int idSector) {
        // Valida que el sector tingui un ID positiu coherent.
        if (idSector <= 0) throw new IllegalArgumentException("El sector no és vàlid");
        this.idSector = idSector;
    }

    public void setLlargadaTotal(Integer llargadaTotal) { this.llargadaTotal = llargadaTotal; }

    public void setGrau(String grau)                    { this.grau          = grau; }

    public void setOrientacio(String orientacio) {
        // Valida que l'orientació coincideixi amb els punts cardinals mitjançant una expressió regular.
        if (orientacio != null && !orientacio.matches("^(N|NE|NO|E|O|SE|SO|S)$"))
            throw new IllegalArgumentException("Orientació invàlida: " + orientacio);
        this.orientacio = orientacio;
    }

    public void setDataIniciNoApte(LocalDate d) { this.dataIniciNoApte = d; }

    public void setDataFiNoApte(LocalDate d)    { this.dataFiNoApte    = d; }

    public void setEstat(String estat) {
        // Restringeix els estats possibles a una llista tancada de valors.
        if (estat != null && !estat.equals("apte") && !estat.equals("construccio") && !estat.equals("tancada"))
            throw new IllegalArgumentException("Estat invàlid: " + estat);
        // Lògica de negoci: si no és "apte", és obligatori tenir una data de finalització de la restricció.
        if ((estat != null && !estat.equals("apte")) && this.dataFiNoApte == null)
            throw new IllegalArgumentException("Cal definir data_fi_no_apte si l'estat no és apte");
        this.estat = estat;
    }

    public void setAncoratge(String ancoratge) {
        // Validació específica per a Gel: exclou ancoratges fixos (spits/químics) i permet només materials de protecció flotant.
        if (ancoratge != null &&
            !ancoratge.equals("friends") && !ancoratge.equals("tascons") &&
            !ancoratge.equals("bagues")  && !ancoratge.equals("pitons") &&
            !ancoratge.equals("tricams") && !ancoratge.equals("bigbros"))
            throw new IllegalArgumentException("Ancoratge invàlid per via de gel: " + ancoratge);
        this.ancoratge = ancoratge;
    }

    public void setTipusRoca(String tipusRoca) {
        // Valida que el tipus de roca sigui un dels permesos pel sistema.
        if (tipusRoca != null &&
            !tipusRoca.equals("conglomerat") && !tipusRoca.equals("granit") &&
            !tipusRoca.equals("calcaria")    && !tipusRoca.equals("arenisca") &&
            !tipusRoca.equals("altres"))
            throw new IllegalArgumentException("Tipus de roca invàlid: " + tipusRoca);
        this.tipusRoca = tipusRoca;
    }

    public void setIdCreador(Integer idCreador)      { this.idCreador    = idCreador; }

    public void setRestriccions(String restriccions) { this.restriccions = restriccions; }

    // Sobrecarrega el mètode toString per representar l'objecte com una cadena de text llegible.
    @Override
    public String toString() {
        return "ViaGel {" +
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
