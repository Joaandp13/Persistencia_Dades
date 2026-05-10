// Defineix el paquet per a la persistència de les vies d'escalada clàssica.
package Model.DAO.Clases.Via;

import Model.DAO.DBConnection;
import Model.Objectes.ViaClassica;
import java.sql.*;

/**
 * ViaClassicaDAO: Gestiona les dades de les vies clàssiques (auto-protecció).
 * Estén de ViaDAO<ViaClassica> per heretar tota la lògica de llistats i cerques.
 */
public class ViaClassicaDAO extends ViaDAO<ViaClassica> {

    // Indica que la taula objectiu a la base de dades és 'via_classica'.
    @Override
    protected String getTaula() { return "via_classica"; }

    /**
     * Implementació del mapeig de files per a Vies Clàssiques.
     * Utilitza els mètodes auxiliars de la classe pare per gestionar valors nuls
     * en camps com la llargada total o el creador.
     */
    @Override
    protected ViaClassica mapRow(ResultSet rs) throws SQLException {
        ViaClassica v = new ViaClassica();
        v.setIdVia(rs.getInt("id_via"));
        v.setNom(rs.getString("nom"));
        v.setIdSector(rs.getInt("id_sector"));
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

    // SQL per inserir: Noteu que no s'inclou 'llargada_total' ja que es calcula a posteriori.
    @Override
    protected String getSqlInserir() {
        return """
               INSERT INTO via_classica
                   (nom, id_sector, grau, orientacio, estat,
                    data_inici_no_apte, data_fi_no_apte, ancoratge, tipus_roca, id_creador, restriccions)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;
    }

    // SQL per actualitzar les dades existents.
    @Override
    protected String getSqlModificar() {
        return """
               UPDATE via_classica SET
                   nom=?, id_sector=?, grau=?, orientacio=?, estat=?,
                   data_inici_no_apte=?, data_fi_no_apte=?,
                   ancoratge=?, tipus_roca=?, id_creador=?, restriccions=?
               WHERE id_via=?
               """;
    }

    /**
     * Assigna els atributs de l'objecte ViaClassica als paràmetres de la consulta INSERT.
     */
    @Override
    protected void setInserirParams(PreparedStatement ps, ViaClassica v) throws SQLException {
        ps.setString(1, v.getNom());
        ps.setInt(2, v.getIdSector());
        ps.setString(3, v.getGrau());
        ps.setString(4, v.getOrientacio());
        ps.setString(5, v.getEstat());
        
        // Conversió de LocalDate a java.sql.Date.
        ps.setDate(6, v.getDataIniciNoApte() != null ? Date.valueOf(v.getDataIniciNoApte()) : null);
        ps.setDate(7, v.getDataFiNoApte()    != null ? Date.valueOf(v.getDataFiNoApte())    : null);
        
        ps.setString(8, v.getAncoratge());
        ps.setString(9, v.getTipusRoca());
        
        // Control de nul·litat per a la clau forana de l'escalador creador.
        if (v.getIdCreador() != null) ps.setInt(10, v.getIdCreador());
        else ps.setNull(10, Types.INTEGER);
        
        ps.setString(11, v.getRestriccions());
    }

    /**
     * Prepara els paràmetres per a l'UPDATE, afegint l'ID de la via al final.
     */
    @Override
    protected void setModificarParams(PreparedStatement ps, ViaClassica v) throws SQLException {
        setInserirParams(ps, v);
        ps.setInt(12, v.getIdVia());
    }

    // Assigna l'identificador autogenerat un cop s'ha inserit la via.
    @Override
    protected void setIdGenerat(ViaClassica v, int id) { v.setIdVia(id); }

    // ── FUNCIONALITAT ESPECÍFICA DE CLÀSSICA ──

    /**
     * Mètode per actualitzar la columna 'llargada_total' de la via
     * realitzant el sumatori de la llargada de tots els trams associats a ella.
     */
    public void actualitzarLlargadaTotal(int idVia) throws SQLException {
        String sql = """
                UPDATE via_classica SET llargada_total = (
                    SELECT SUM(llargada) FROM trams WHERE id_via_classica = ?
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
