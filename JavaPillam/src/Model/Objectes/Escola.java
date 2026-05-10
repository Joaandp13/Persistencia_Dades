// Defineix el paquet on s'ubica el POJO que representa una Escola d'escalada.
package Model.Objectes;

/**
 * Classe que modela una Escola.
 * Una escola és una zona geogràfica general que conté diversos sectors d'escalada.
 */
public class Escola {
    // Atributs privats que mapegen les columnes de la taula 'Escola' a la base de dades.
    private int idEscola;
    private String nom;
    private String poblacio;
    private String aproximacio; // Descripció general d'accés a l'escola.
    private String popularitat; // Grau d'afluència (baixa, mitjana, alta).

    // ── Constructors ──

    /**
     * Constructor complet (amb ID).
     * S'utilitza habitualment per recuperar dades existents des de la base de dades.
     */
    public Escola(int idEscola, String nom, String poblacio, String aproximacio, String popularitat) {
        setIdEscola(idEscola);
        setNom(nom);
        setPoblacio(poblacio);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
    }

    /**
     * Constructor sense ID.
     * Útil per a la creació de noves escoles que encara no tenen una clau assignada pel sistema (autoincrement).
     */
    public Escola(String nom, String poblacio, String aproximacio, String popularitat) {
        setNom(nom);
        setPoblacio(poblacio);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
    }

    // ── Getters (Mètodes d'accés) ──
    public int getIdEscola()       { return idEscola; }
    public String getNom()         { return nom; }
    public String getPoblacio()    { return poblacio; }
    public String getAproximacio() { return aproximacio; }
    public String getPopularitat() { return popularitat; }

    // ── Setters (Mètodes de modificació amb validacions de negoci) ──

    public void setIdEscola(int idEscola) {
        // Valida que l'identificador no sigui un valor incoherent.
        if (idEscola < 0)
            throw new IllegalArgumentException("L'id no pot ser negatiu");
        this.idEscola = idEscola;
    }

    public void setNom(String nom) {
        // El nom és un camp obligatori a la base de dades.
        if (nom == null || nom.isBlank())
            throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom;
    }

    public void setPoblacio(String poblacio) {
        // La població és necessària per a la ubicació general.
        if (poblacio == null || poblacio.isBlank())
            throw new IllegalArgumentException("La població no pot estar buida");
        this.poblacio = poblacio;
    }

    public void setAproximacio(String aproximacio) {
        this.aproximacio = aproximacio; // Camp opcional: permet detallar l'accés general.
    }

    public void setPopularitat(String popularitat) {
        // Valida que el valor s'ajusti als enumerats lògics (ignorant majúscules/minúscules).
        if (popularitat != null &&
                !popularitat.equalsIgnoreCase("baixa") &&
                !popularitat.equalsIgnoreCase("mitjana") &&
                !popularitat.equalsIgnoreCase("alta"))
            throw new IllegalArgumentException("Ha de ser baixa, mitjana o alta");
        this.popularitat = popularitat;
    }

    // ── toString ──
    // Sobrecarrega el mètode per obtenir una representació textual clara de l'objecte Escola.
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
