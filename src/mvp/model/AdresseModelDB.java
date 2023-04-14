package mvp.model;

import agence.metier.Adresse;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresseModelDB implements DAOAdresse {
    private static final Logger logger = LogManager.getLogger(ClientModelDB.class);
    private Connection dbConnect;

    public AdresseModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("Erreur de connexion");
            logger.error("Erreur de connexion");
            System.exit(1);
        }
        logger.info("Connexion établie");
    }

    @Override
    public Adresse addAdresse(Adresse adresse) {
        String query1 = "insert into APIADRESSE(cp,localite,rue,num) values(?,?,?,?)";
        String query2 = "select idadresse from APIADRESSE where cp = ? and localite = ? and rue = ? and num = ?";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setInt(1,adresse.getCp());
            pstm1.setString(2,adresse.getLocalite());
            pstm1.setString(3,adresse.getRue());
            pstm1.setString(4,adresse.getNum());
            int n = pstm1.executeUpdate();
            if(n==1){
                pstm2.setInt(1,adresse.getCp());
                pstm2.setString(2,adresse.getLocalite());
                pstm2.setString(3,adresse.getRue());
                pstm2.setString(4,adresse.getNum());
                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int idadresse= rs.getInt(1);
                    adresse.setIdAdresse(idadresse);
                    return adresse;
                }
                else {
                    //System.err.println("Record introuvable.");
                    logger.error("Record introuvable");
                    return null;
                }
            }
            else return null;

        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur sql : "+e);
            return null;
        }
    }

    @Override
    public boolean removeAdresse(Adresse adresse) {
        String query = "delete from APIADRESSE where idadresse = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,adresse.getIdAdresse());
            int n = pstm.executeUpdate();
            if(n!=0) return true;
            else return false;
        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur d'effacement : "+e);
            return false;
        }
    }

    @Override
    public Adresse updateAdresse(Adresse adresse) {
        String query = "update APIADRESSE set cp = ?, localite = ?, rue = ?, num = ? where idadresse = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,adresse.getCp());
            pstm.setString(2,adresse.getLocalite());
            pstm.setString(3,adresse.getRue());
            pstm.setString(4,adresse.getNum());
            pstm.setInt(5,adresse.getIdAdresse());
            int n = pstm.executeUpdate();
            if(n!=0) return readAdresse(adresse.getIdAdresse());
            else return null;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur d'update : "+e);
            return null;
        }
    }

    @Override
    public Adresse readAdresse(int idAdresse) {
        String query = "select * from APIADRESSE where idadresse = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idAdresse);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                int cp = rs.getInt(2);
                String localite = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                Adresse adr = new Adresse(idAdresse,cp,localite,rue,num);
                return adr;
            }
            else {
                //System.out.println("Record introuvable.");
                logger.error("Record introuvable");
                return null;
            }
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }

    @Override
    public List<Adresse> getAdresses() {
        List<Adresse> la = new ArrayList<>();
        String query="select * from APIADRESSE";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idadresse = rs.getInt(1);
                int cp = rs.getInt(2);
                String localite = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                Adresse adr = new Adresse(idadresse,cp,localite,rue,num);
                la.add(adr);
            }
            return la;
        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }
}
