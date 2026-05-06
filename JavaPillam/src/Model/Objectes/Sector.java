package Model.Objectes;

public class Sector {

    private int idSector;
    private String nom;
    private int idEscola;
    private double latitud;
    private double longitud;
    private String aproximacio;
    private String popularitat;
    private String restriccions;
    private String tipusVies;  // "gel" o "classica_esportiva"

    // ── Constructors ──
    public Sector() {}

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

    // ── Getters ──
    public int getIdSector()       { return idSector; }
    public String getNom()         { return nom; }
    public int getIdEscola()       { return idEscola; }
    public double getLatitud()     { return latitud; }
    public double getLongitud()    { return longitud; }
    public String getAproximacio() { return aproximacio; }
    public String getPopularitat() { return popularitat; }
    public String getRestriccions(){ return restriccions; }
    public String getTipusVies()   { return tipusVies; }

    // ── Setters ──
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
        if (latitud < -90 || latitud > 90) throw new IllegalArgumentException("Latitud invàlida");
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        if (longitud < -180 || longitud > 180) throw new IllegalArgumentException("Longitud invàlida");
        this.longitud = longitud;
    }

    public void setAproximacio(String aproximacio) {
        this.aproximacio = aproximacio; // opcional
    }

    public void setPopularitat(String popularitat) {
        if (popularitat != null &&
            !popularitat.equalsIgnoreCase("baixa") &&
            !popularitat.equalsIgnoreCase("mitjana") &&
            !popularitat.equalsIgnoreCase("alta"))
            throw new IllegalArgumentException("Ha de ser baixa, mitjana o alta");
        this.popularitat = popularitat;
    }

    public void setRestriccions(String restriccions) {
        this.restriccions = restriccions; // opcional
    }

    public void setTipusVies(String tipusVies) {
        if (tipusVies != null &&
            !tipusVies.equals("gel") &&
            !tipusVies.equals("classica_esportiva"))
            throw new IllegalArgumentException("Tipus de vies invàlid: " + tipusVies);
        this.tipusVies = tipusVies;
    }

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
