package mvp.model;

import agence.metier.Location;
import agence.metier.Taxi;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxiModelDB implements DAOTaxi {
    private static final Logger logger = LogManager.getLogger(ClientModelDB.class);
    private Connection dbConnect;

    public TaxiModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("Erreur de connexion");
            logger.error("Erreur de connexion");
            System.exit(1);
        }
        logger.info("Connexion établie");
    }

    @Override
    public Taxi addTaxi(Taxi taxi) {
        String query1 = "insert into APITAXI(immatriculation,carburant,prixkm) values(?,?,?)";
        String query2 = "select idtaxi from APITAXI where immatriculation = ?";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setString(1,taxi.getImmatriculation());
            pstm1.setString(2,taxi.getCarburant());
            pstm1.setBigDecimal(3,taxi.getPrixKm());
            int n = pstm1.executeUpdate();
            if(n==1){
                pstm2.setString(1, taxi.getImmatriculation());
                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int idtaxi= rs.getInt(1);
                    taxi.setIdTaxi(idtaxi);
                    return taxi;
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
    public boolean removeTaxi(Taxi taxi) {
        String query = "delete from APITAXI where idtaxi = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,taxi.getIdTaxi());
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
    public Taxi updateTaxi(Taxi taxi) {
        String query = "update APITAXI set immatriculation = ?, carburant = ?, prixkm = ? where idtaxi = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,taxi.getImmatriculation());
            pstm.setString(2,taxi.getCarburant());
            pstm.setBigDecimal(3,taxi.getPrixKm());
            pstm.setInt(4,taxi.getIdTaxi());
            int n = pstm.executeUpdate();
            if(n!=0) return readTaxi(taxi.getIdTaxi());
            else return null;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur d'update : "+e);
            return null;
        }
    }

    @Override
    public Taxi readTaxi(int idTaxi) {
        String query = "select * from APITAXI where idtaxi = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idTaxi);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String immatriculation = rs.getString(2);
                String carburant = rs.getString(3);
                BigDecimal prixkm = rs.getBigDecimal(4);
                try {
                    Taxi tx = new Taxi(idTaxi,immatriculation,carburant,prixkm);
                    return tx;
                } catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                    return null;
                }
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
    public List<Taxi> getTaxis() {
        List<Taxi> lt = new ArrayList<>();
        String query="select * from APITAXI";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idtaxi = rs.getInt(1);
                String immatriculation = rs.getString(2);
                String carburant = rs.getString(3);
                BigDecimal prixkm = rs.getBigDecimal(4);
                try {
                    Taxi tx = new Taxi(idtaxi,immatriculation,carburant,prixkm);
                    lt.add(tx);
                } catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            return lt;
        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }

    @Override
    public List<Taxi> getTaxisNotUsed(Location location) {
        List<Taxi> lt = new ArrayList<>();
        String query = "select * FROM APITAXI where idtaxi NOT IN (SELECT T.idtaxi FROM APIFACTURATION AF INNER JOIN APILOCATION AL on AF.idlocation = AL.idlocation INNER JOIN APITAXI T on AF.idtaxi = T.idtaxi WHERE AF.idlocation = ?)";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,location.getIdLocation());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                int idtaxi = rs.getInt(1);
                String immatriculation = rs.getString(2);
                String carburant = rs.getString(3);
                BigDecimal prixkm = rs.getBigDecimal(4);
                try {
                    Taxi tx = new Taxi(idtaxi,immatriculation,carburant,prixkm);
                    lt.add(tx);
                } catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            return lt;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }
}
