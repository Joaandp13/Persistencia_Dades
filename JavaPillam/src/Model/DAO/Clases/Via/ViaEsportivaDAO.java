// Defineix el paquet per a la persistència de les vies d'escalada esportiva.
package Model.DAO.Clases.Via;

import Model.Objectes.ViaEsportiva;
import java.sql.*;

/**
 * ViaEsportivaDAO: Gestiona el cicle de vida de les dades de les vies esportives.
 * Estén de ViaDAO<ViaEsportiva> per reutilitzar la lògica genèrica d'accés a dades.
 */
public class ViaEsportivaDAO extends ViaDAO<ViaEsportiva> {

    // Especifica el nom de la taula de la base de dades on es guarden les vies esportives.
    @Override
    protected String getTaula() { return "via_esportiva"; }

    /**
     * Implementació del mapeig de ResultSet a l'objecte ViaEsportiva.
     * Recupera tota la informació tècnica i administrativa (estat, dates, creador).
     */
    @Override
    protected ViaEsportiva mapRow(ResultSet rs) throws SQLException {
        ViaEsportiva v = new ViaEsportiva();
        v.setIdVia(rs.getInt("id_via"));
        v.setNom(rs.getString("nom"));
        v.setIdSector(rs.getInt("id_sector"));
        v.setLlargadaTotal(rs.getInt("llargada_total"));
        v.setGrau(rs.getString("grau"));
        v.setOrientacio(rs.getString("orientacio"));
        
        // Gestió de dates de tancament (converteix de sql.Date a LocalDate mitjançant ViaDAO).
        v.setDataIniciNoApte(getDateOrNull(rs, "data_inici_no_apte"));
        v.setDataFiNoApte(getDateOrNull(rs, "data_fi_no_apte"));
        
        v.setEstat(rs.getString("estat"));
        v.setAncoratge(rs.getString("ancoratge"));
        v.setTipusRoca(rs.getString("tipus_roca"));
        
        // Gestió del camp id_creador per si el valor és nul a la base de dades.
        v.setIdCreador(getIntOrNull(rs, "id_creador"));
        
        v.setRestriccions(rs.getString("restriccions"));
        return v;
    }

    // Retorna la cadena SQL per inserir una nova via esportiva amb 12 paràmetres.
    @Override
    protected String getSqlInserir() {
        return """
               INSERT INTO via_esportiva
                   (nom, id_sector, llargada_total, grau, orientacio, estat,
                    data_inici_no_apte, data_fi_no_apte, ancoratge, tipus_roca, id_creador, restriccions)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;
    }

    // Retorna la cadena SQL per modificar una via esportiva basant-se en el seu ID.
    @Override
    protected String getSqlModificar() {
        return """
               UPDATE via_esportiva SET
                   nom=?, id_sector=?, llargada_total=?, grau=?, orientacio=?, estat=?,
                   data_inici_no_apte=?, data_fi_no_apte=?,
                   ancoratge=?, tipus_roca=?, id_creador=?, restriccions=?
               WHERE id_via=?
               """;
    }

    /**
     * Configura els valors del PreparedStatement per a les operacions d'escriptura.
     * Converteix objectes Java a tipus compatibles amb SQL.
     */
    @Override
    protected void setInserirParams(PreparedStatement ps, ViaEsportiva v) throws SQLException {
        ps.setString(1, v.getNom());
        ps.setInt(2, v.getIdSector());
        ps.setInt(3, v.getLlargadaTotal());
        ps.setString(4, v.getGrau());
        ps.setString(5, v.getOrientacio());
        ps.setString(6, v.getEstat());
        
        // Conversió segura de dates per evitar NullPointerException si la data no està definida.
        ps.setDate(7, v.getDataIniciNoApte() != null ? Date.valueOf(v.getDataIniciNoApte()) : null);
        ps.setDate(8, v.getDataFiNoApte()    != null ? Date.valueOf(v.getDataFiNoApte())    : null);
        
        ps.setString(9, v.getAncoratge());
        ps.setString(10, v.getTipusRoca());
        
        // Si el creador no existeix, s'indica un NULL de tipus INTEGER a la BDD.
        if (v.getIdCreador() != null) ps.setInt(11, v.getIdCreador());
        else ps.setNull(11, Types.INTEGER);
        
        ps.setString(12, v.getRestriccions());
    }

    /**
     * Aprofita els 12 paràmetres de setInserirParams i afegeix l'ID de la via
     * en la posició 13 per executar el filtre de la sentència UPDATE.
     */
    @Override
    protected void setModificarParams(PreparedStatement ps, ViaEsportiva v) throws SQLException {
        setInserirParams(ps, v);      // Omple del paràmetre 1 al 12.
        ps.setInt(13, v.getIdVia()); // Defineix l'ID per al "WHERE id_via = ?".
    }

    // Actualitza l'ID de l'objecte un cop la base de dades l'ha generat automàticament.
    @Override
    protected void setIdGenerat(ViaEsportiva v, int id) { v.setIdVia(id); }
}
