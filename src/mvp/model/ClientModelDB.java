package mvp.model;

import agence.metier.Client;
import myconnections.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientModelDB implements DAOClient {
    private Connection dbConnect;

    public ClientModelDB(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.err.println("Erreur de connexion");
            System.exit(1);
        }
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
                else System.err.println("Record introuvable.");
                return null;
            }
            else return null;

        } catch (SQLException e) {
            System.err.println("Erreur sql : "+e);
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
            System.err.println("Erreur sql : "+e);
            return false;
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
                Client cl = new Client(idclient,mail,nom,prenom,tel);
                lc.add(cl);
            }
            return lc;
        } catch (SQLException e) {
            System.err.println("Erreur sql : "+e);
            return null;
        }
    }
}
