// Defineix el paquet per a la persistència de les vies de gel.
package Model.DAO.Clases.Via;

import Model.DAO.DBConnection;
import Model.Objectes.ViaGel;
import java.sql.*;

/**
 * ViaGelDAO: Gestiona les dades de les cascades de gel.
 * Estén de ViaDAO per aprofitar la lògica comuna de CRUD.
 */
public class ViaGelDAO extends ViaDAO<ViaGel> {

    // Retorna el nom de la taula específica a la base de dades.
    @Override
    protected String getTaula() { return "via_gel"; }

    /**
     * Converteix una fila de la taula 'via_gel' en un objecte ViaGel.
     * Inclou camps específics d'aquesta modalitat com l'ancoratge o el tipus de roca/gel.
     */
    @Override
    protected ViaGel mapRow(ResultSet rs) throws SQLException {
        ViaGel v = new ViaGel();
        v.setIdVia(rs.getInt("id_via"));
        v.setNom(rs.getString("nom"));
        v.setIdSector(rs.getInt("id_sector"));
        // Utilitza mètodes auxiliars (probablement definits a la classe mare) per gestionar NULLs.
        v.setLlargadaTotal(getIntOrNull(rs, "llargada_total"));
        v.setGrau(rs.getString("grau"));
        v.setOrientacio(rs.getString("orientacio"));
        v.setDataIniciNoApte(getDateOrNull(rs, "data_inici_no_apte"));
        v.setDataFiNoApte(getDateOrNull(rs, "data_fi_no_apte"));
        v.setEstat(rs.getString("estat"));
        v.setAncoratge(rs.getString("ancoratge"));
        v.setTipusRoca(rs.getString("tipus_roca"));
        v.setIdCreador(getIntOrNull(rs, "id_creador"));
        v.setRestriccions(rs.getString("restriccions"));
        return v;
    }

    // Defineix la sentència SQL per a la inserció de nous registres de gel.
    @Override
    protected String getSqlInserir() {
        return """
               INSERT INTO via_gel
                   (nom, id_sector, grau, orientacio, estat,
                    data_inici_no_apte, data_fi_no_apte, ancoratge, tipus_roca, id_creador, restriccions)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;
    }

    // Defineix la sentència SQL per a l'actualització de dades existents.
    @Override
    protected String getSqlModificar() {
        return """
               UPDATE via_gel SET
                   nom=?, id_sector=?, grau=?, orientacio=?, estat=?,
                   data_inici_no_apte=?, data_fi_no_apte=?,
                   ancoratge=?, tipus_roca=?, id_creador=?, restriccions=?
               WHERE id_via=?
               """;
    }

    /**
     * Mapeja els atributs de l'objecte ViaGel als paràmetres del PreparedStatement per a INSERT.
     */
    @Override
    protected void setInserirParams(PreparedStatement ps, ViaGel v) throws SQLException {
        ps.setString(1, v.getNom());
        ps.setInt(2, v.getIdSector());
        ps.setString(3, v.getGrau());
        ps.setString(4, v.getOrientacio());
        ps.setString(5, v.getEstat());
        // Conversió de LocalDate a java.sql.Date per a la BDD.
        ps.setDate(6, v.getDataIniciNoApte() != null ? Date.valueOf(v.getDataIniciNoApte()) : null);
        ps.setDate(7, v.getDataFiNoApte()    != null ? Date.valueOf(v.getDataFiNoApte())    : null);
        ps.setString(8, v.getAncoratge());
        ps.setString(9, v.getTipusRoca());
        // Gestió manual del possible valor nul de l'ID del creador.
        if (v.getIdCreador() != null) ps.setInt(10, v.getIdCreador());
        else ps.setNull(10, Types.INTEGER);
        ps.setString(11, v.getRestriccions());
    }

    /**
     * Reutilitza els paràmetres d'inserció i afegeix l'ID al final per a la clàusula WHERE de l'UPDATE.
     */
    @Override
    protected void setModificarParams(PreparedStatement ps, ViaGel v) throws SQLException {
        setInserirParams(ps, v);
        ps.setInt(12, v.getIdVia()); // El paràmetre 12 correspon al "WHERE id_via=?"
    }

    // Assigna l'ID autogenerat a l'objecte després d'una inserció amb èxit.
    @Override
    protected void setIdGenerat(ViaGel v, int id) { v.setIdVia(id); }

    // ── FUNCIONALITAT ESPECÍFICA ──

    /**
     * Recalcula la llargada total de la via sumant la llargada de tots els seus trams.
     * S'executa directament a la BDD mitjançant una subconsulta.
     */
    public void actualitzarLlargadaTotal(int idVia) throws SQLException {
        String sql = """
                UPDATE via_gel SET llargada_total = (
                    SELECT SUM(llargada) FROM trams WHERE id_via_gel = ?
                ) WHERE id_via = ?
                """;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idVia);
        ps.setInt(2, idVia);
        ps.executeUpdate();
        ps.close();
    }
}
