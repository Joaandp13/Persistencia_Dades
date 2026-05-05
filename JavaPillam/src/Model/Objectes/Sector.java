package Model.Objectes;

public class Sector {

    private int idSector;
    private String nom;
    private int idEscola;
    private String coordenades;
    private String aproximacio;
    private int numVies;
    private String popularitat;
    private String restriccions;
    private String tipusVies;

    // ==================== CONSTRUCTORS ====================

    // Constructor complet (amb ID)
    public Sector(int idSector, String nom, int idEscola,
                  String coordenades, String aproximacio, int numVies,
                  String popularitat, String restriccions, String tipusVies) {

        setIdSector(idSector);
        setNom(nom);
        setIdEscola(idEscola);
        setCoordenades(coordenades);
        setAproximacio(aproximacio);
        setNumVies(numVies);
        setPopularitat(popularitat);
        setRestriccions(restriccions);
        setTipusVies(tipusVies);
    }

    // Constructor sense ID (per a insercions noves)
    public Sector(String nom, int idEscola,
                  String coordenades, String aproximacio, int numVies,
                  String popularitat, String restriccions, String tipusVies) {

        setNom(nom);
        setIdEscola(idEscola);
        setCoordenades(coordenades);
        setAproximacio(aproximacio);
        setNumVies(numVies);
        setPopularitat(popularitat);
        setRestriccions(restriccions);
        setTipusVies(tipusVies);
    }

    // ==================== GETTERS ====================

    public int getIdSector()      { return idSector; }
    public String getNom()        { return nom; }
    public int getIdEscola()      { return idEscola; }
    public String getCoordenades(){ return coordenades; }
    public String getAproximacio(){ return aproximacio; }
    public int getNumVies()       { return numVies; }
    public String getPopularitat(){ return popularitat; }
    public String getRestriccions(){ return restriccions; }
    public String getTipusVies()  { return tipusVies; }

    // ==================== SETTERS ====================

    public void setIdSector(int idSector) {
        if (idSector < 0) {
            throw new IllegalArgumentException("L'id del sector no pot ser negatiu");
        }
        this.idSector = idSector;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("El nom del sector no pot estar buit");
        }
        this.nom = nom.trim();
    }

    public void setIdEscola(int idEscola) {
        if (idEscola < 0) {
            throw new IllegalArgumentException("L'id de l'escola no pot ser negatiu");
        }
        this.idEscola = idEscola;
    }

    // Camps opcionals
    public void setCoordenades(String coordenades) {
        if (coordenades != null && !coordenades.isBlank()) {
            this.coordenades = coordenades.trim();
        } else {
            this.coordenades = null;
        }
    }

    public void setAproximacio(String aproximacio) {
        if (aproximacio != null && !aproximacio.isBlank()) {
            this.aproximacio = aproximacio.trim();
        } else {
            this.aproximacio = null;
        }
    }

    public void setNumVies(int numVies) {
        if (numVies < 0) {
            throw new IllegalArgumentException("El nombre de vies no pot ser negatiu");
        }
        this.numVies = numVies;
    }

    public void setPopularitat(String popularitat) {
        if (popularitat != null && !popularitat.isBlank()) {
            String p = popularitat.toLowerCase().trim();
            if (!p.equals("baixa") && !p.equals("mitjana") && !p.equals("alta")) {
                throw new IllegalArgumentException("La popularitat ha de ser Baixa, Mitjana o Alta!");
            }
            this.popularitat = p;
        } else {
            this.popularitat = null;
        }
    }

    public void setRestriccions(String restriccions) {
        if (restriccions != null && !restriccions.isBlank()) {
            this.restriccions = restriccions.trim();
        } else {
            this.restriccions = null;
        }
    }

    public void setTipusVies(String tipusVies) {
        if (tipusVies == null || tipusVies.isBlank()) {
            throw new IllegalArgumentException("El tipus de vies no pot estar buit");
        }

        String t = tipusVies.toLowerCase().trim();

        boolean teGel       = t.contains("gel");
        boolean teClassica  = t.contains("clàssica") || t.contains("classica");
        boolean teEsportiva = t.contains("esportiva");

        if (teGel && (teClassica || teEsportiva)) {
            throw new IllegalArgumentException(
                    "Un sector de Gel no pot tenir vies de Clàssica o Esportiva!");
        }

        if (teGel) {
            this.tipusVies = "gel";
        } else if (teClassica || teEsportiva) {
            this.tipusVies = "classica_esportiva";
        } else {
            throw new IllegalArgumentException(
                    "El tipus de via ha de ser 'gel' o 'classica_esportiva'");
        }
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "Sector{" +
                "idSector=" + idSector +
                ", nom='" + nom + '\'' +
                ", idEscola=" + idEscola +
                ", coordenades='" + coordenades + '\'' +
                ", aproximacio='" + aproximacio + '\'' +
                ", numVies=" + numVies +
                ", popularitat='" + popularitat + '\'' +
                ", restriccions='" + restriccions + '\'' +
                ", tipusVies='" + tipusVies + '\'' +
                '}';
    }
}
