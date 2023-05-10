package demodb;

import agence.metier.Adresse;
import agence.metier.Client;
import agence.metier.Location;
import agence.metier.Taxi;
import myconnections.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class CRUDA {
    private Scanner sc = new Scanner(System.in);
    private Connection dbConnect;

    public void gestion() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("Connexion établie");
        do {
            System.out.println("1.Ajout\n2.Recherche\n3.Modification\n4.Suppression\n5.Tous\n6.Fin");
            System.out.print("Choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    modification();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    tous();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide recommencez");
            }
        } while (true);
    }

    public void ajout() {
        System.out.print("Mail : ");
        String mail = sc.nextLine();
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Téléphone : ");
        String tel = sc.nextLine();
        String query1 = "insert into APITCLIENT(mail,nom,prenom,telephone) values(?,?,?,?)";
        String query2 = "select idclient from APITCLIENT where mail = ?";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setString(1,mail);
            pstm1.setString(2,nom);
            pstm1.setString(3,prenom);
            pstm1.setString(4,tel);
            int n = pstm1.executeUpdate();
            System.out.println(n+" ligne insérée.");
            if(n==1){
                pstm2.setString(1,mail);
                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int idclient= rs.getInt(1);
                    System.out.println("idclient = "+idclient);
                }
                else System.out.println("Record introuvable.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }


    public void recherche() {
        System.out.println("Id du client recherché : ");
        int idrech = sc.nextInt();
        String query = "select * from APITCLIENT where idclient = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                try {
                    Client cl = new Client(idrech,mail,nom,prenom,tel);
                    System.out.println(cl);
                    opSpeciales(cl);
                }
                catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            else System.out.println("Record introuvable.");
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }

    public void modification() {
        System.out.println("Id du client recherché : ");
        int idrech = sc.nextInt();
        String query = "select * from APITCLIENT where idclient = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                try {
                    Client cl = new Client(idrech,mail,nom,prenom,tel);
                    System.out.println(cl);
                    opModification(cl);
                }
                catch (Exception e) {
                    System.out.println("Erreur : "+e.getMessage());
                }
            }
            else System.out.println("Record introuvable.");
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }

    private void opModification(Client client) {
        do {
            System.out.println("1.Mail\n2.Nom\n3.Prénom\n4.Téléphone\n5.Menu principal");
            System.out.print("Choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    mail(client);
                    break;
                case 2:
                    nom(client);
                    break;
                case 3:
                    prenom(client);
                    break;
                case 4:
                    tel(client);
                    break;
                case 5: return;
                default:
                    System.out.println("Choix invalide recommencez");
            }
        } while (true);
    }

    private void mail(Client client){
        System.out.println("Nouveau mail : ");
        String nmail = sc.nextLine();
        String query = "update APITCLIENT set mail = ? where idclient = ?";
        modificationClient(client,query,nmail);
    }

    private void nom(Client client){
        System.out.println("Nouveau nom : ");
        String nnom = sc.nextLine();
        String query = "update APITCLIENT set nom = ? where idclient = ?";
        modificationClient(client,query,nnom);
    }

    private void prenom(Client client){
        System.out.println("Nouveau prénom : ");
        String nprenom = sc.nextLine();
        String query = "update APITCLIENT set prenom = ? where idclient = ?";
        modificationClient(client,query,nprenom);
    }

    private void tel(Client client){
        System.out.println("Nouveau téléphone : ");
        String ntel = sc.nextLine();
        String query = "update APITCLIENT set telephone = ? where idclient = ?";
        modificationClient(client,query,ntel);
    }

    private void modificationClient(Client client, String query, String newinfo){
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,newinfo);
            pstm.setInt(2,client.getIdClient());
            int n = pstm.executeUpdate();
            if(n!=0) System.out.println(n+" ligne mise à jour.");
            else System.out.println("Record introuvable.");
        } catch (SQLException e) {
            System.out.println("Erreur sql : " + e);
        }
    }

    public void suppression() {
        System.out.println("Id du client recherché : ");
        int idrech = sc.nextInt();
        String query = "delete from APITCLIENT where idclient = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            int n = pstm.executeUpdate();
            if(n!=0) System.out.println(n+" ligne supprimée.");
            else System.out.println("Record introuvable.");

        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }

    }

    private void tous() {
        String query="select * from APITCLIENT order by idclient";
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
                    System.out.println(cl);
                }
                catch (Exception e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }
    private void opSpeciales(Client client) {
        do {
            System.out.println("1.Taxis utilisés\n2.Locations effectuées\n3.Destination\n4.Menu principal");
            System.out.print("Choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    taxis(client);
                    break;
                case 2:
                    locations(client);
                    break;
                case 3:
                    destinations(client);
                    break;
                case 4: return;
                default:
                    System.out.println("Choix invalide recommencez");
            }
        } while (true);
    }

    private void taxis(Client client) {
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
                Taxi tx = new Taxi(idtaxi,immatriculation,carburant,prixkm);
                System.out.println(tx);
            }
            if(!trouve) System.out.println("Aucun taxi trouvé");
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }

    private void locations(Client client){
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
                Location lo = new Location(idlocation,dateloc,kmtotal);
                System.out.println(lo.toString2());
            }
            if(!trouve) System.out.println("Aucune location trouvée");
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }

    private void destinations(Client client){
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
                Adresse adr = new Adresse(idadresse,cp,localite,rue,num);
                System.out.println(adr);
            }
            if(!trouve) System.out.println("Aucune adresse trouvée");
        } catch (SQLException e) {
            System.out.println("Erreur sql : "+e);
        }
    }

    public static void main(String[] args) {
        CRUDA g = new CRUDA();
        g.gestion();
    }
}
