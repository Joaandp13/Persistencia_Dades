// Defineix el paquet on s'ubiquen les classes d'accés a dades (DAO).
package Model.DAO.Clases;

import Model.DAO.DBConnection;
import Model.Objectes.Tram;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TramDAO: Gestiona les operacions CRUD (Crear, Llegir, Actualitzar, Eliminar)
 * de la taula 'trams' a la base de dades.
 */
public class TramDAO {

    /**
     * Mètode auxiliar privat per convertir una fila del ResultSet (BDD) en un objecte Tram (Java).
     * Gestió de valors NULL: Com que les FK de les vies poden ser nul·les, fem servir wasNull().
     */
    private Tram mapRow(ResultSet rs) throws SQLException {
        // En Java, rs.getInt() retorna 0 si el camp és NULL a la BDD. 
        // Per això comprovem wasNull() per assignar un null real a l'Integer.
        int vc = rs.getInt("id_via_classica"); 
        Integer idVc = rs.wasNull() ? null : vc;
        
        int ve = rs.getInt("id_via_esportiva"); 
        Integer idVe = rs.wasNull() ? null : ve;
        
        int vg = rs.getInt("id_via_gel");       
        Integer idVg = rs.wasNull() ? null : vg;
        
        return new Tram(
            rs.getInt("id_tram"), 
            idVc, idVe, idVg,
            rs.getInt("num_tram"), 
            rs.getInt("llargada"), 
            rs.getString("grau")
        );
    }

    // ── CREATE (Inserir) ──
    /**
     * Insereix un nou tram a la base de dades.
     * Recupera la clau primària generada automàticament (id_tram) i l'assigna a l'objecte.
     */
    public void inserir(Tram t) throws SQLException {
        String sql = """
                INSERT INTO trams (id_via_classica, id_via_esportiva, id_via_gel, num_tram, llargada, grau)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        Connection conn = DBConnection.getConnection();
        // Fem servir RETURN_GENERATED_KEYS per saber quin ID ha posat la BDD.
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Si l'objecte té null en una ID de via, passem un NULL a la BDD amb Types.INTEGER.
        if (t.getIdViaClasica()   != null) ps.setInt(1, t.getIdViaClasica());   else ps.setNull(1, Types.INTEGER);
        if (t.getIdViaEsportiva() != null) ps.setInt(2, t.getIdViaEsportiva()); else ps.setNull(2, Types.INTEGER);
        if (t.getIdViaGel()       != null) ps.setInt(3, t.getIdViaGel());       else ps.setNull(3, Types.INTEGER);
        
        ps.setInt(4, t.getNumTram());
        ps.setInt(5, t.getLlargada());
        ps.setString(6, t.getGrau());
        
        ps.executeUpdate();

        // Obtenim l'ID generat i l'actualitzem al model Java.
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) t.setIdTram(keys.getInt(1));
        ps.close();
    }

    // ── READ ONE (Cercar per ID) ──
    public Tram cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM trams WHERE id_tram = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Tram t = rs.next() ? mapRow(rs) : null;
        ps.close();
        return t;
    }

    // ── MÈTODES DE LLISTAT (Per tipus de via) ──

    public List<Tram> llistarPerViaEsportiva(int idVia) throws SQLException {
        return llistarPerVia("id_via_esportiva", idVia);
    }

    public List<Tram> llistarPerViaClassica(int idVia) throws SQLException {
        return llistarPerVia("id_via_classica", idVia);
    }

    public List<Tram> llistarPerViaGel(int idVia) throws SQLException {
        return llistarPerVia("id_via_gel", idVia);
    }

    /**
     * Mètode genèric per llistar trams segons una columna específica (FK).
     * Ordena els trams pel seu número (L1, L2, L3...) de forma ascendent.
     */
    private List<Tram> llistarPerVia(String columna, int idVia) throws SQLException {
        List<Tram> llista = new ArrayList<>();
        String sql = "SELECT * FROM trams WHERE " + columna + " = ? ORDER BY num_tram";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVia);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            llista.add(mapRow(rs));
        }
        ps.close();
        return llista;
    }

    // ── UPDATE (Modificar) ──
    /**
     * Actualitza la informació de llargada i grau d'un tram existent.
     * No permet moure un tram de via per evitar inconsistències (UPDATE restringit).
     */
    public void modificar(Tram t) throws SQLException {
        String sql = """
                UPDATE trams SET llargada = ?, grau = ?
                WHERE id_tram = ?
                """;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, t.getLlargada());
        ps.setString(2, t.getGrau());
        ps.setInt(3, t.getIdTram());
        
        ps.executeUpdate();
        ps.close();
    }

    // ── DELETE (Eliminar) ──
    
    /** Elimina un tram concret pel seu ID únic. */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM trams WHERE id_tram = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    // Mètodes per eliminar de cop tots els trams d'una via (cascada manual o neteja).
    public void eliminarPerViaEsportiva(int idVia) throws SQLException { eliminarPerVia("id_via_esportiva", idVia); }
    public void eliminarPerViaClassica(int idVia)  throws SQLException { eliminarPerVia("id_via_classica",  idVia); }
    public void eliminarPerViaGel(int idVia)       throws SQLException { eliminarPerVia("id_via_gel",       idVia); }

    /**
     * Mètode genèric intern per executar l'eliminació massiva per columna FK.
     */
    private void eliminarPerVia(String columna, int idVia) throws SQLException {
        String sql = "DELETE FROM trams WHERE " + columna + " = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVia);
        ps.executeUpdate();
        ps.close();
    }
}
