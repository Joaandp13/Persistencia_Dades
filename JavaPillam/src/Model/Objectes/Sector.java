// Defineix el paquet on s'ubica el POJO que representa un Sector d'escalada.
package Model.Objectes;

/**
 * Classe que modela un Sector. 
 * Un sector és una zona específica dins d'una Escola que agrupa diverses vies.
 */
public class Sector {

    // Atributs privats que mapegen les columnes de la taula 'Sector' a la base de dades.
    private int idSector;
    private String nom;
    private int idEscola;       // Clau Forana (FK) que connecta el sector amb la seva Escola.
    private double latitud;     // Coordenada geogràfica (Eix Y).
    private double longitud;    // Coordenada geogràfica (Eix X).
    private String aproximacio; // Descripció de com arribar al peu de via des del pàrquing.
    private String popularitat; // Grau de freqüentació (baixa, mitjana, alta).
    private String restriccions;// Possibles limitacions (per niuament, temporals, etc.).
    private String tipusVies;   // Diferencia entre zones de "gel" o de roca ("classica_esportiva").

    // ── Constructors ──

    // Constructor buit per defecte.
    public Sector() {}

    /**
     * Constructor complet (amb ID). 
     * S'utilitza per carregar dades ja existents de la base de dades.
     */
    public Sector(int idSector, String nom, int idEscola, double latitud, double longitud,
                  String aproximacio, String popularitat, String restriccions, String tipusVies) {
        setIdSector(idSector);
        setNom(nom);
        setIdEscola(idEscola);
        setLatitud(latitud);
        setLongitud(longitud);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
        setRestriccions(restriccions);
        setTipusVies(tipusVies);
    }

    /**
     * Constructor sense ID. 
     * Ideal per crear sectors nous abans de ser persistits (on l'ID és autoincremental).
     */
    public Sector(String nom, int idEscola, double latitud, double longitud,
                  String aproximacio, String popularitat, String restriccions, String tipusVies) {
        setNom(nom);
        setIdEscola(idEscola);
        setLatitud(latitud);
        setLongitud(longitud);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
        setRestriccions(restriccions);
        setTipusVies(tipusVies);
    }

    // ── Getters (Mètodes de consulta) ──
    public int getIdSector()       { return idSector; }
    public String getNom()         { return nom; }
    public int getIdEscola()       { return idEscola; }
    public double getLatitud()     { return latitud; }
    public double getLongitud()    { return longitud; }
    public String getAproximacio() { return aproximacio; }
    public String getPopularitat() { return popularitat; }
    public String getRestriccions(){ return restriccions; }
    public String getTipusVies()   { return tipusVies; }

    // ── Setters (Mètodes de modificació amb validacions de domini) ──

    public void setIdSector(int idSector) {
        if (idSector < 0) throw new IllegalArgumentException("L'id no pot ser negatiu");
        this.idSector = idSector;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom;
    }

    public void setIdEscola(int idEscola) {
        if (idEscola <= 0) throw new IllegalArgumentException("L'id de l'escola no és vàlid");
        this.idEscola = idEscola;
    }

    public void setLatitud(double latitud) {
        // La latitud ha d'estar entre els pols (-90° a 90°).
        if (latitud < -90 || latitud > 90) throw new IllegalArgumentException("Latitud invàlida");
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        // La longitud ha d'estar entre -180° i 180°.
        if (longitud < -180 || longitud > 180) throw new IllegalArgumentException("Longitud invàlida");
        this.longitud = longitud;
    }

    public void setAproximacio(String aproximacio) {
        this.aproximacio = aproximacio; // Atribut opcional, sense validació estricta.
    }

    public void setPopularitat(String popularitat) {
        // Valida que el valor estigui dins del rang predefinit.
        if (popularitat != null &&
            !popularitat.equalsIgnoreCase("baixa") &&
            !popularitat.equalsIgnoreCase("mitjana") &&
            !popularitat.equalsIgnoreCase("alta"))
            throw new IllegalArgumentException("Ha de ser baixa, mitjana o alta");
        this.popularitat = popularitat;
    }

    public void setRestriccions(String restriccions) {
        this.restriccions = restriccions; // Atribut opcional.
    }

    public void setTipusVies(String tipusVies) {
        // Lògica d'exclusivitat: un sector o és de gel o és de roca (clàssica/esportiva).
        if (tipusVies != null &&
            !tipusVies.equals("gel") &&
            !tipusVies.equals("classica_esportiva"))
            throw new IllegalArgumentException("Tipus de vies invàlid: " + tipusVies);
        this.tipusVies = tipusVies;
    }

    // Sobrecarrega el mètode toString per mostrar la informació del sector de forma llegible.
    @Override
    public String toString() {
        return "Sector {" +
               "\n  id          = " + idSector +
               "\n  nom         = " + nom +
               "\n  escola      = " + idEscola +
               "\n  latitud     = " + latitud +
               "\n  longitud    = " + longitud +
               "\n  aproximacio = " + aproximacio +
               "\n  popularitat = " + popularitat +
               "\n  restriccions= " + restriccions +
               "\n  tipusVies   = " + tipusVies +
               "\n}";
    }
}
