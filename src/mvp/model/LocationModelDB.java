package mvp.model;

import agence.metier.Adresse;
import agence.metier.Client;
import agence.metier.Location;
import agence.metier.Taxi;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocationModelDB implements DAOLocation, LocationSpecial {
    private static final Logger logger = LogManager.getLogger(ClientModelDB.class);
    private Connection dbConnect;

    public LocationModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("Erreur de connexion");
            logger.error("Erreur de connexion");
            System.exit(1);
        }
        logger.info("Connexion établie");
    }

    @Override
    public Location addLocation(Location location) {
        String query1 = "insert into APILOCATION(dateloc,kmtotal,idadresse,idclient) values(?,?,?,?)";
        String query2 = "select max(idlocation) from APILOCATION where idadresse = ? and idclient = ?";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setDate(1,Date.valueOf(location.getDateLoc()));
            pstm1.setInt(2,location.getKmtotal());
            pstm1.setInt(3,location.getAdrDepart().getIdAdresse());
            pstm1.setInt(4,location.getClient().getIdClient());
            int n = pstm1.executeUpdate();
            if(n==1){
                pstm2.setInt(1,location.getAdrDepart().getIdAdresse());
                pstm2.setInt(2,location.getClient().getIdClient());
                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int idlocation= rs.getInt(1);
                    location.setIdLocation(idlocation);
                    return location;
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
    public boolean removeLocation(Location location) {
        String query = "delete from APILOCATION where idlocation = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,location.getIdLocation());
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
    public Location updateLocation(Location location) {
        String query = "update APILOCATION set dateloc = ?, kmtotal = ? where idlocation = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setDate(1,Date.valueOf(location.getDateLoc()));
            pstm.setInt(2,location.getKmtotal());
            pstm.setInt(3,location.getIdLocation());
            int n = pstm.executeUpdate();
            if(n!=0) return readLocation(location.getIdLocation());
            else return null;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur d'update : "+e);
            return null;
        }
    }

    @Override
    public Location readLocation(int idLocation) {
        String query = "select * from VUE4_CLIENT_ADRESSE_LOCATION where idlocation = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idLocation);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                int idclient = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                Client cl = new Client(idclient,mail,nom,prenom,tel);
                int idadresse = rs.getInt(6);
                int cp = rs.getInt(7);
                String localite = rs.getString(8);
                String rue = rs.getString(9);
                String num = rs.getString(10);
                Adresse adr = new Adresse(idadresse,cp,localite,rue,num);
                LocalDate datecom = rs.getDate(12).toLocalDate();
                int kmtotal = rs.getInt(13);
                Location lo = new Location(idLocation,datecom,kmtotal,cl,adr);
                return lo;
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
    public List<Location> getLocations() {
        List<Location> lloc = new ArrayList<>();
        String query="select * from VUE4_CLIENT_ADRESSE_LOCATION";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idclient = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                Client cl = new Client(idclient,mail,nom,prenom,tel);
                int idadresse = rs.getInt(6);
                int cp = rs.getInt(7);
                String localite = rs.getString(8);
                String rue = rs.getString(9);
                String num = rs.getString(10);
                Adresse adr = new Adresse(idadresse,cp,localite,rue,num);
                int idlocation = rs.getInt(11);
                LocalDate datecom = rs.getDate(12).toLocalDate();
                int kmtotal = rs.getInt(13);
                Location lo = new Location(idlocation,datecom,kmtotal,cl,adr);
                lloc.add(lo);
            }
            return lloc;
        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }

    @Override
    public boolean addFacturation(Location lo, Taxi tx) {
        String query = "insert into APIFACTURATION(idlocation,idtaxi,cout) values(?,?,?)";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,lo.getIdLocation());
            pstm.setInt(2,tx.getIdTaxi());
            pstm.setBigDecimal(3,tx.getPrixKm().multiply(BigDecimal.valueOf(lo.getKmtotal())));
            int n = pstm.executeUpdate();
            if(n!=0) return true;
            else return false;
        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return false;
        }
    }
}
