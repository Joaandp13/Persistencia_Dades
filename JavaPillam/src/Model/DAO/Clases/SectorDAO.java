// Defineix el paquet per a la persistència de dades mitjançant el patró DAO.
package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Sector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SectorDAO: Implementa les operacions d'accés a la base de dades (CRUD)
 * per a la taula 'sector'.
 */
public class SectorDAO {

    /**
     * Mètode privat de suport que mapeja una fila de la base de dades (ResultSet)
     * a un objecte Java de la classe 'Sector'.
     */
    private Sector mapRow(ResultSet rs) throws SQLException {
        Sector s = new Sector();
        s.setIdSector(rs.getInt("id_sector"));
        s.setNom(rs.getString("nom"));
        s.setIdEscola(rs.getInt("id_escola"));
        s.setLatitud(rs.getDouble("latitud"));
        s.setLongitud(rs.getDouble("longitud"));
        s.setAproximacio(rs.getString("aproximacio"));
        s.setPopularitat(rs.getString("popularitat"));
        s.setRestriccions(rs.getString("restriccions"));
        s.setTipusVies(rs.getString("tipus_vies"));
        return s;
    }

    // ── CREATE (Inserir) ──
    /**
     * Insereix un nou sector a la base de dades.
     * Utilitza Statement.RETURN_GENERATED_KEYS per recuperar l'ID autoincremental generat.
     */
    public void inserir(Sector s) throws SQLException {
        String sql = """
                INSERT INTO sector (nom, id_escola, latitud, longitud, aproximacio, popularitat, restriccions, tipus_vies)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        // try-with-resources: garanteix el tancament de la connexió i el PreparedStatement automàticament.
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNom());
            ps.setInt(2, s.getIdEscola());
            ps.setDouble(3, s.getLatitud());
            ps.setDouble(4, s.getLongitud());
            ps.setString(5, s.getAproximacio());
            ps.setString(6, s.getPopularitat());
            ps.setString(7, s.getRestriccions());
            ps.setString(8, s.getTipusVies());
            ps.executeUpdate();

            // Recupera la clau primària (ID) que la BDD ha assignat al nou registre.
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) s.setIdSector(keys.getInt(1));
        }
    }

    // ── READ ONE (Cercar per ID) ──
    /**
     * Obté un sector concret mitjançant el seu identificador únic.
     */
    public Sector cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM sector WHERE id_sector = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            // Si hi ha un resultat, el mapegem a un objecte; si no, retornem null.
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    // ── READ ALL (Llistar tots) ──
    /**
     * Retorna una llista amb tots els sectors de la base de dades, ordenats alfabèticament.
     */
    public List<Sector> llistarTots() throws SQLException {
        List<Sector> llista = new ArrayList<>();
        String sql = "SELECT * FROM sector ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── READ PER ESCOLA ──
    /**
     * Filtra els sectors segons l'escola a la qual pertanyen (relació 1:N).
     */
    public List<Sector> llistarPerEscola(int idEscola) throws SQLException {
        List<Sector> llista = new ArrayList<>();
        String sql = "SELECT * FROM sector WHERE id_escola = ? ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEscola);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── READ COMPLEX (Lògica de negoci) ──
    /**
     * Busca sectors que tinguin més de X vies amb l'estat 'apte'.
     * Aquest mètode suma el recompte de les tres taules de vies (esportiva, clàssica i gel).
     */
    public List<Sector> llistarAmbMesDeXVies(int x) throws SQLException {
        List<Sector> llista = new ArrayList<>();
        String sql = """
                SELECT s.* FROM sector s
                WHERE (
                    (SELECT COUNT(*) FROM via_esportiva ve WHERE ve.id_sector = s.id_sector AND ve.estat = 'apte')
                  + (SELECT COUNT(*) FROM via_classica  vc WHERE vc.id_sector = s.id_sector AND vc.estat = 'apte')
                  + (SELECT COUNT(*) FROM via_gel        vg WHERE vg.id_sector = s.id_sector AND vg.estat = 'apte')
                ) > ?
                ORDER BY s.nom
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, x);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) llista.add(mapRow(rs));
        }
        return llista;
    }

    // ── UPDATE (Modificar) ──
    /**
     * Actualitza tots els camps d'un sector existent basant-se en el seu ID.
     */
    public void modificar(Sector s) throws SQLException {
        String sql = """
                UPDATE sector SET nom=?, id_escola=?, latitud=?, longitud=?,
                    aproximacio=?, popularitat=?, restriccions=?, tipus_vies=?
                WHERE id_sector=?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNom());
            ps.setInt(2, s.getIdEscola());
            ps.setDouble(3, s.getLatitud());
            ps.setDouble(4, s.getLongitud());
            ps.setString(5, s.getAproximacio());
            ps.setString(6, s.getPopularitat());
            ps.setString(7, s.getRestriccions());
            ps.setString(8, s.getTipusVies());
            ps.setInt(9, s.getIdSector());
            ps.executeUpdate();
        }
    }

    // ── DELETE (Eliminar) ──
    /**
     * Elimina el registre d'un sector mitjançant el seu ID.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM sector WHERE id_sector = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
