package exercicesJDBC;

import myconnections.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class exo2 {

    public void demo() {
        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        String query = "SELECT * FROM APITAXI WHERE PRIXKM >= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            System.out.print("Prix minimum km : ");
            BigDecimal prix = sc.nextBigDecimal();
            pstm.setBigDecimal(1, prix);
            boolean trouve = false;
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    trouve = true;
                    int id = rs.getInt(1);
                    String imm = rs.getString(2);
                    String carburant = rs.getString(3);
                    BigDecimal pr = rs.getBigDecimal(4);
                    System.out.println("ID : "+id+" | Immatriculation : "+imm+" | Carburant :"+carburant+" | Prix km : "+pr);
                }
                if (!trouve) {
                    System.out.println("Aucun prix égal ou supérieur à "+prix);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur SQL = " + e);
        }

        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        exo2 ex = new exo2();
        ex.demo();
    }
}
