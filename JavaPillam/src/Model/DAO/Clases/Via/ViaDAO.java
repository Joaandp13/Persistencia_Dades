// Defineix el paquet base per a la jerarquia de DAOs de Vies.
package Model.DAO.Clases.Via;

import Model.DAO.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ViaDAO: Classe abstracta genèrica que defineix el comportament base per a 
 * qualsevol tipus de via (Esportiva, Clàssica, Gel).
 * @param <T> El tipus d'objecte de via que gestionarà la subclasse.
 */
public abstract class ViaDAO<T> {

    // ── MÈTODES ABSTRACTES (Han de ser implementats per les subclasses) ──

    /** Retorna el nom de la taula (ex: "via_esportiva"). */
    protected abstract String getTaula();

    /** Converteix una fila de la BDD en l'objecte del tipus T. */
    protected abstract T mapRow(ResultSet rs) throws SQLException;

    /** Defineix els paràmetres per a la sentència d'inserció. */
    protected abstract void setInserirParams(PreparedStatement ps, T via) throws SQLException;
    
    /** Defineix els paràmetres per a la sentència de modificació. */
    protected abstract void setModificarParams(PreparedStatement ps, T via) throws SQLException;
    
    /** Retorna la cadena SQL d'inserció. */
    protected abstract String getSqlInserir();
    
    /** Retorna la cadena SQL d'actualització. */
    protected abstract String getSqlModificar();
    
    /** Assigna l'ID generat per la BDD a l'objecte Java. */
    protected abstract void setIdGenerat(T via, int id);

    // ── CREATE ──
    /**
     * Insereix una via a la base de dades i recupera la seva clau primària.
     */
    public void inserir(T via) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(getSqlInserir(), Statement.RETURN_GENERATED_KEYS);
        setInserirParams(ps, via);
        ps.executeUpdate();
        
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) setIdGenerat(via, keys.getInt(1));
        ps.close();
    }

    // ── READ (Consultes genèriques) ──

    /** Cerca una via per ID a la taula corresponent. */
    public T cercarPerId(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTaula() + " WHERE id_via = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        T via = rs.next() ? mapRow(rs) : null;
        ps.close();
        return via;
    }

    /** Llista totes les vies de la taula ordenades per nom. */
    public List<T> llistarTotes() throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " ORDER BY nom";
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) llista.add(mapRow(rs));
        st.close();
        return llista;
    }

    /** Filtra les vies segons el sector on es troben. */
    public List<T> llistarPerSector(int idSector) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " WHERE id_sector = ? ORDER BY nom";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idSector);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    /** Filtra les vies per estat (apte, tancada, construccio). */
    public List<T> llistarPerEstat(String estat) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " WHERE estat = ? ORDER BY nom";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, estat);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    /** * Consulta complexa: Retorna només les vies "aptes" que pertanyen a una escola 
     * concreta fent un JOIN amb la taula de sectors.
     */
    public List<T> llistarAptesPerEscola(int idEscola) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT v.* FROM " + getTaula() + " v " +
                "JOIN sector s ON v.id_sector = s.id_sector " +
                "WHERE s.id_escola = ? AND v.estat = 'apte' ORDER BY v.nom";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEscola);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    /** Filtra vies segons un rang de dificultat (grau). */
    public List<T> llistarPerRangGrau(String grauMin, String grauMax) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() + " WHERE grau >= ? AND grau <= ? ORDER BY grau";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, grauMin);
        ps.setString(2, grauMax);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    /** * Lògica de negoci: Vies que han passat a estat 'apte' en els darrers 30 dies.
     * Útil per a la secció de "Novetats".
     */
    public List<T> llistarRecentmentAptes() throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT * FROM " + getTaula() +
                " WHERE estat = 'apte'" +
                "   AND data_fi_no_apte IS NOT NULL" +
                "   AND data_fi_no_apte >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)" +
                " ORDER BY data_fi_no_apte DESC";
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) llista.add(mapRow(rs));
        st.close();
        return llista;
    }

    /** Ordena les vies d'una escola de la més llarga a la més curta. */
    public List<T> llistarMesLlarguesPerEscola(int idEscola) throws SQLException {
        List<T> llista = new ArrayList<>();
        String sql = "SELECT v.* FROM " + getTaula() + " v " +
                "JOIN sector s ON v.id_sector = s.id_sector " +
                "WHERE s.id_escola = ? ORDER BY v.llargada_total DESC";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEscola);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) llista.add(mapRow(rs));
        ps.close();
        return llista;
    }

    // ── UPDATE ──

    /** Modifica tots els camps d'una via. */
    public void modificar(T via) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(getSqlModificar());
        setModificarParams(ps, via);
        ps.executeUpdate();
        ps.close();
    }

    /** Actualització ràpida només de l'estat i les dates de restricció. */
    public void actualitzarEstat(int idVia, String nouEstat,
                                 Date dataInici, Date dataFi) throws SQLException {
        String sql = "UPDATE " + getTaula() +
                " SET estat = ?, data_inici_no_apte = ?, data_fi_no_apte = ?" +
                " WHERE id_via = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nouEstat);
        ps.setDate(2, dataInici);
        ps.setDate(3, dataFi);
        ps.setInt(4, idVia);
        ps.executeUpdate();
        ps.close();
    }

    // ── DELETE ──

    /** Elimina una via pel seu ID. */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM " + getTaula() + " WHERE id_via = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    // ── UTILITATS DE SUPORT PER A LES SUBCLASSES ──

    /** Gestiona la lectura d'enters que poden ser NULL a la BDD. */
    protected Integer getIntOrNull(ResultSet rs, String col) throws SQLException {
        int val = rs.getInt(col);
        return rs.wasNull() ? null : val;
    }

    /** Gestiona la conversió de java.sql.Date a java.time.LocalDate (permet nuls). */
    protected java.time.LocalDate getDateOrNull(ResultSet rs, String col) throws SQLException {
        Date d = rs.getDate(col);
        return d != null ? d.toLocalDate() : null;
    }
}
