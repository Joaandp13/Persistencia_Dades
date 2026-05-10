// Defineix el paquet per a la persistència de dades dels Escaladors.
package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Escalador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EscaladorDAO: Centralitza totes les operacions de lectura i escriptura 
 * a la taula 'escalador' de la base de dades.
 */
public class EscaladorDAO {

    /**
     * Mètode privat auxiliar per transformar una fila del ResultSet en un objecte Java 'Escalador'.
     * Reutilitzat per tots els mètodes de consulta (Read) per evitar duplicitat de codi.
     */
    private Escalador mapRow(ResultSet rs) throws SQLException {
        return new Escalador(
                rs.getInt("id_escalador"),
                rs.getString("nom"),
                rs.getString("alias"),
                rs.getInt("edat"),
                rs.getString("nivell"),
                rs.getString("via_max_nivell"),
                rs.getString("estil_preferit"),
                rs.getString("historial")
        );
    }

    // ── CREATE (Inserir) ──
    /**
     * Registra un nou escalador al sistema. 
     * Utilitza RETURN_GENERATED_KEYS per actualitzar l'ID de l'objecte amb el valor creat per la BDD.
     */
    public void inserir(Escalador e) throws SQLException {
        String sql = """
                INSERT INTO escalador (nom, alias, edat, nivell, via_max_nivell, estil_preferit, historial)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNom());
            ps.setString(2, e.getAlias());
            ps.setInt   (3, e.getEdat());
            ps.setString(4, e.getNivell());
            ps.setString(5, e.getViaMaxNivell());
            ps.setString(6, e.getEstilPreferit());
            ps.setString(7, e.getHistorial());
            ps.executeUpdate();

            // Sincronitza l'ID generat per la base de dades amb l'objecte en memòria.
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                e.setIdEscalador(keys.getInt(1));
            }
        }
    }

    // ── READ ONE (Cerca Individual) ──

    /** Cerca un escalador pel seu ID únic. */
    public Escalador cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM escalador WHERE id_escalador = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null; // Retorna null si no existeix cap escalador amb aquest ID.
        }
    }

    /** Cerca un escalador pel seu nom complet. */
    public Escalador cercarPerNom(String nom) throws SQLException {
        String sql = "SELECT * FROM escalador WHERE nom = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    // ── READ ALL (Llistats) ──

    /** Retorna la llista completa de tots els escaladors registrats. */
    public List<Escalador> llistarTots() throws SQLException {
        List<Escalador> llista = new ArrayList<>();
        String sql = "SELECT * FROM escalador";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                llista.add(mapRow(rs));
            }
        }
        return llista;
    }

    /** * Retorna els escaladors que tenen un nivell tècnic específic (ex: '6a', '7b+').
     * Molt útil per trobar companys de cordada del mateix nivell.
     */
    public List<Escalador> llistarPerNivell(String nivell) throws SQLException {
        List<Escalador> llista = new ArrayList<>();
        String sql = "SELECT * FROM escalador WHERE nivell = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nivell);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                llista.add(mapRow(rs));
            }
        }
        return llista;
    }

    // ── UPDATE (Modificar) ──
    /**
     * Sobreescriu les dades d'un escalador existent utilitzant el seu ID com a referència.
     */
    public void modificar(Escalador e) throws SQLException {
        String sql = """
                UPDATE escalador
                SET nom = ?, alias = ?, edat = ?, nivell = ?,
                    via_max_nivell = ?, estil_preferit = ?, historial = ?
                WHERE id_escalador = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNom());
            ps.setString(2, e.getAlias());
            ps.setInt   (3, e.getEdat());
            ps.setString(4, e.getNivell());
            ps.setString(5, e.getViaMaxNivell());
            ps.setString(6, e.getEstilPreferit());
            ps.setString(7, e.getHistorial());
            ps.setInt   (8, e.getIdEscalador());
            ps.executeUpdate();
        }
    }

    // ── DELETE (Eliminar) ──
    /**
     * Elimina un escalador de la base de dades.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM escalador WHERE id_escalador = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
