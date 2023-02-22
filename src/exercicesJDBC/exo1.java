package exercicesJDBC;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class exo1 {

    public void demo() {
        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        String query = "SELECT * FROM APILOCATION WHERE KMTOTAL = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            System.out.print("Nombre de km : ");
            String km = sc.nextLine();
            pstm.setString(1, km);
            boolean trouve = false;
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    trouve = true;
                    int id = rs.getInt(1);
                    LocalDate dt = rs.getDate(2).toLocalDate();
                    String kmtotal = rs.getString(3);
                    System.out.println("ID : "+id+" | Date : "+dt+" | Kilomètre(s) :"+kmtotal);
                }
                if (!trouve) {
                    System.out.println("Aucune location ne correspond.");
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Erreur SQL = " + e);
        }

        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        exo1 ex = new exo1();
        ex.demo();
    }
}
