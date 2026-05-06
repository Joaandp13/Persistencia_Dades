package Model.Objectes;

public class Escola {
    private int idEscola;
    private String nom;
    private String poblacio;
    private String aproximacio;
    private String popularitat;

    // ── Constructors ──
    public Escola(int idEscola, String nom, String poblacio, String aproximacio, String popularitat) {
        setIdEscola(idEscola);
        setNom(nom);
        setPoblacio(poblacio);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
    }

    public Escola(String nom, String poblacio, String aproximacio, String popularitat) {
        setNom(nom);
        setPoblacio(poblacio);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
    }

    // ── Getters ──
    public int getIdEscola()       { return idEscola; }
    public String getNom()         { return nom; }
    public String getPoblacio()    { return poblacio; }
    public String getAproximacio() { return aproximacio; }
    public String getPopularitat() { return popularitat; }

    // ── Setters ──
    public void setIdEscola(int idEscola) {
        if (idEscola < 0)
            throw new IllegalArgumentException("L'id no pot ser negatiu");
        this.idEscola = idEscola;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank())
            throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom;
    }

    public void setPoblacio(String poblacio) {
        if (poblacio == null || poblacio.isBlank())
            throw new IllegalArgumentException("La població no pot estar buida");
        this.poblacio = poblacio;
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

    // ── toString ──
    @Override
    public String toString() {
        return "Escola {" +
               "\n  id          = " + idEscola +
               "\n  nom         = " + nom +
               "\n  poblacio    = " + poblacio +
               "\n  aproximacio = " + aproximacio +
               "\n  popularitat = " + popularitat +
               "\n}";
    }
}
