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
import java.util.Scanner;

public class ClientModelDB implements DAOClient, ClientSpecial {
    private static final Logger logger = LogManager.getLogger(ClientModelDB.class);
    private Connection dbConnect;

    public ClientModelDB(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("Erreur de connexion");
            logger.error("Erreur de connexion");
            System.exit(1);
        }
        logger.info("Connexion établie");
    }

    @Override
    public Client addClient(Client client) {
        String query1 = "insert into APITCLIENT(mail,nom,prenom,telephone) values(?,?,?,?)";
        String query2 = "select idclient from APITCLIENT where mail = ?";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setString(1,client.getMail());
            pstm1.setString(2,client.getNom());
            pstm1.setString(3,client.getPrenom());
            pstm1.setString(4,client.getTelephone());
            int n = pstm1.executeUpdate();
            if(n==1){
                pstm2.setString(1,client.getMail());
                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int idclient= rs.getInt(1);
                    client.setIdClient(idclient);
                    return client;
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
    public boolean removeClient(Client client) {
        String query = "delete from APITCLIENT where idclient = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,client.getIdClient());
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
    public Client updateClient(Client client) {
        String query = "update APITCLIENT set mail = ?, nom = ?, prenom = ?, telephone = ? where idclient = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,client.getMail());
            pstm.setString(2,client.getNom());
            pstm.setString(3,client.getPrenom());
            pstm.setString(4,client.getTelephone());
            pstm.setInt(5,client.getIdClient());
            int n = pstm.executeUpdate();
            if(n!=0) return readClient(client.getIdClient());
            else return null;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur d'update : "+e);
            return null;
        }
    }

    @Override
    public Client readClient(int idClient) {
        String query = "select * from APITCLIENT where idclient = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idClient);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                try {
                    Client cl = new Client(idClient,mail,nom,prenom,tel);
                    return cl;
                }
                catch (Exception e) {
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
    public List<Client> getClients() {
        List<Client> lc = new ArrayList<>();
        String query="select * from APITCLIENT";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idclient = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                try {
                    Client cl = new Client(idclient,mail,nom,prenom,tel);
                    lc.add(cl);
                }
                catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            return lc;
        } catch (SQLException e) {
            //System.err.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }

    @Override
    public List<Taxi> taxis(Client client) {
        List<Taxi> lt = new ArrayList<>();
        String query="select * from VUE2_CLIENT_TAXI where idclient = ? order by idtaxi";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,client.getIdClient());
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int idtaxi = rs.getInt(4);
                String immatriculation = rs.getString(5);
                String carburant = rs.getString(6);
                BigDecimal prixkm = rs.getBigDecimal(7);
                try {
                    Taxi tx = new Taxi(idtaxi,immatriculation,carburant,prixkm);
                    lt.add(tx);
                } catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            if(!trouve) {
                //System.out.println("Aucun taxi trouvé");
                logger.error("Record introuvable");
                return null;
            }
            return lt;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }

    @Override
    public List<Location> locations(Client client) {
        List<Location> lloc = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String query="select * from APILOCATION where idclient = ? and dateloc between ? and ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,client.getIdClient());
            System.out.print("Date début : ");
            int j = sc.nextInt();
            int m = sc.nextInt();
            int a = sc.nextInt();
            LocalDate dd = LocalDate.of(a, m, j);
            pstm.setDate(2, java.sql.Date.valueOf(dd));
            System.out.print("Date fin : ");
            int j1 = sc.nextInt();
            int m1 = sc.nextInt();
            int a1 = sc.nextInt();
            LocalDate df = LocalDate.of(a1, m1, j1);
            pstm.setDate(3, java.sql.Date.valueOf(df));
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int idlocation = rs.getInt(1);
                LocalDate dateloc = rs.getDate(2).toLocalDate();
                int kmtotal = rs.getInt(3);
                try {
                    Location lo = new Location(idlocation,dateloc,kmtotal);
                    lloc.add(lo);
                } catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            if(!trouve) {
                //System.out.println("Aucune location trouvée");
                logger.error("Record introuvable");
                return null;
            }
            return lloc;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }

    @Override
    public List<Adresse> destinations(Client client) {
        List<Adresse> ladr = new ArrayList<>();
        String query="select distinct idadresse, cp, localite, rue, num from VUE1_ADRESSE_LOCATION where idclient = ? order by idadresse";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,client.getIdClient());
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int idadresse = rs.getInt(1);
                int cp = rs.getInt(2);
                String localite = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                try {
                    Adresse adr = new Adresse(idadresse,cp,localite,rue,num);
                    ladr.add(adr);
                } catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            if(!trouve) {
                //System.out.println("Aucune adresse trouvée");
                logger.error("Record introuvable");
                return null;
            }
            return ladr;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur SQL : "+e);
            return null;
        }
    }
}
