import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Main {

    public static void main(String[] args) {
    String url ="jdbc:mysql://localhost:3306/java_pt";
    String user ="root";
    String password = "";
try{
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println("connexion reussie ");


  //test users
     insertUser(connection, "ts", "ts", "ts@gmail.com", "Etudiant");
    deleteUserById(connection, 2);
    afficherUtilisateur(connection);
  // test events
    ajouterEvenement(connection, "tn", "2024-12-10", "fifa club tn", 1);
    afficherEvenements(connection);
    supprimerEvenement(connection, 5);
    modifierEvenement(connection, 6, "tn", "2024-12-12", "epic tn", 1);
    afficherEvenements(connection);
  // test salles 
    ajouterSalle(connection, "Salle A", 50);
    ajouterSalle(connection, "Salle B", 100);
    afficherSalles(connection);
    supprimerSalle(connection, 1);
    afficherSalles(connection);
  // test terrains 
    ajouterTerrain(connection, "Terrain  1", "foot");
    ajouterTerrain(connection, "Terrain  2", "sport");
    afficherTerrains(connection);
    supprimerTerrain(connection, 1);
    afficherTerrains(connection);

  // test reservations
    reserver(connection, 1, 6, 2, null, "2024-12-12");
    verifierDisponibilite(connection, 2, null, "2024-12-12");
    afficherReservations(connection);
    modifierReservation(connection, 1, 1, 6, null,2, "2024-12-30");
    afficherReservations(connection);
    supprimerReservation(connection, 1);
    afficherReservations(connection);



          
    connection.close();
    }catch (Exception e) {
        e.printStackTrace();
    }
    
}

//ajputer utilisateur
public static void insertUser(Connection connection, String nom, String prenom, String email, String type) {
    try {
        
        String insertQuery = "INSERT INTO utilisateurs (nom, prenom, email, type) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, type);
        int rowsInserted = preparedStatement.executeUpdate();
        
        if (rowsInserted > 0) {
            System.out.println("Insertion reussie ");
        }  

        preparedStatement.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
//afficher utilisateurs
public static void afficherUtilisateur(Connection connection) {
        try {
            String SelectQuery = "SELECT * FROM utilisateurs";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SelectQuery);

            System.out.println("Liste utilisateurs :");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_user"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Prenom: " + rs.getString("prenom"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("type: " + rs.getString("type"));
                
                }
                rs.close();
            statement.close();
        
        }catch (Exception e) {
            e.printStackTrace();
    }
}
//supprimer utilisateur 
public static void deleteUserById(Connection connection, int userId) {
    try {
        String deleteQuery = "DELETE FROM utilisateurs WHERE id_user = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, userId);

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Utilisateur  supprime avec succes ");
        }

        preparedStatement.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}





//ajouter evenement 
public static void ajouterEvenement(Connection connection, String nomEvent, String dateEvent, String description, int idUser) {
    try {
        String insertQuery = "INSERT INTO evenements (nom_event, date_event, description, id_user) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, nomEvent);
        preparedStatement.setString(2, dateEvent);
        preparedStatement.setString(3, description);
        preparedStatement.setInt(4, idUser);

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Evenement ajoute avec succes !");
        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//afficher evenements 
public static void afficherEvenements(Connection connection) {
    try {
        String selectQuery = "SELECT * FROM evenements";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectQuery);

        System.out.println("Liste des evenements :");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id_event"));
            System.out.println("Nom: " + rs.getString("nom_event"));
            System.out.println("Date: " + rs.getDate("date_event"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("ID Utilisateur: " + rs.getInt("id_user"));
        }

        rs.close();
        statement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//modifier evenment
public static void modifierEvenement(Connection connection, int idEvent, String nomEvent, String dateEvent, String description, int idUser) {
    try {
        String updateQuery = "UPDATE evenements SET nom_event = ?, date_event = ?, description = ?, id_user = ? WHERE id_event = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, nomEvent);
        preparedStatement.setString(2, dateEvent);
        preparedStatement.setString(3, description);
        preparedStatement.setInt(4, idUser);
        preparedStatement.setInt(5, idEvent);

        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Evenement modifie avec succes ");
        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//supprimer event
public static void supprimerEvenement(Connection connection, int idEvent) {
    try {
        String deleteQuery = "DELETE FROM evenements WHERE id_event = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, idEvent);

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Evenement supprime avec succes !");
        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}




//ajouter salle 
public static void ajouterSalle(Connection connection, String nomSalle, int capacite) {
    try {
        String insertQuery = "INSERT INTO salles (nom_salle, capacite) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, nomSalle);
        preparedStatement.setInt(2, capacite);

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Salle ajoute avec succes ");

        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//afficher salles
public static void afficherSalles(Connection connection) {
    try {
        String selectQuery = "SELECT * FROM salles";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectQuery);

        System.out.println("Liste des salles :");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id_salle"));
            System.out.println("Nom: " + rs.getString("nom_salle"));
            System.out.println("Capacite: " + rs.getInt("capacite"));
        }

        rs.close();
        statement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
// Supprimer salle
public static void supprimerSalle(Connection connection, int idSalle) {
    try {
        String deleteQuery = "DELETE FROM salles WHERE id_salle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, idSalle);

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Salle supprime avec succes ");
        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}




 // Ajouter terrain
 public static void ajouterTerrain(Connection connection, String nomTerrain, String type) {
    try {
        String insertQuery = "INSERT INTO terrains (nom_terrain, type) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, nomTerrain);
        preparedStatement.setString(2, type);

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Terrain ajoute avec succes ");
        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
// Afficher terrains
public static void afficherTerrains(Connection connection) {

    try {
        String selectQuery = "SELECT * FROM terrains";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectQuery);

        System.out.println("Liste des terrains :");
        while (rs.next()) {
            System.out.println("Id: " + rs.getInt("id_terrain"));
            System.out.println("Nom: " + rs.getString("nom_terrain"));
            System.out.println("type: " + rs.getString("type"));
        }

        rs.close();
        statement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
// Supprimer terrain
public static void supprimerTerrain(Connection connection, int idTerrain) {
    try {
        String deleteQuery = "DELETE FROM terrains WHERE id_terrain = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, idTerrain);

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Terrain supprime avec succes ");
        }

        preparedStatement.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}




// reservation  
public static void reserver(Connection connection, Integer idUser, Integer idEvent, Integer idSalle, Integer idTerrain, String dateReservation) {
    try {
        String insertQuery = "INSERT INTO reservations (id_user, id_event, id_salle, id_terrain, date_reservation) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setObject(1, idUser);
        preparedStatement.setObject(2, idEvent);
        preparedStatement.setObject(3, idSalle);
        preparedStatement.setObject(4, idTerrain);
        preparedStatement.setString(5, dateReservation);
        
        int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Reservation effectue avec succes ");
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// disponibilite
    public static void verifierDisponibilite(Connection connection, Integer idSalle, Integer idTerrain, String dateReservation) {
        try {
            String selectQuery = "SELECT COUNT(*) AS count FROM reservations WHERE date_reservation = ? AND (id_salle = ? OR id_terrain = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, dateReservation);
            preparedStatement.setObject(2, idSalle);
            preparedStatement.setObject(3, idTerrain);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    System.out.println(" salle ou  terrain est deja reserve pour cet date : ");
                } else {
                    System.out.println(" salle ou  terrain est disponible : ");
                }
            }

            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// afficher reservations  
    public static void afficherReservations(Connection connection) {
        try {
            String selectQuery = "SELECT * FROM reservations";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectQuery);

            System.out.println("Liste des reservations :");
            while (rs.next()) {
                System.out.println("ID Reservation: " + rs.getInt("id_reservation"));
                System.out.println("ID Uaer: " + rs.getObject("id_user"));
                System.out.println("ID Evenement: " + rs.getObject("id_event"));
                System.out.println("ID Salle: " + rs.getObject("id_salle"));
                System.out.println("ID Terrain: " + rs.getObject("id_terrain"));
                System.out.println("Date: " + rs.getDate("date_reservation"));
                System.out.println("---------------------------");
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// modifier reservation   
    public static void modifierReservation(Connection connection, int idReservation, Integer idUser, Integer idEvent, Integer idSalle, Integer idTerrain, String dateReservation) {
        try {
            String updateQuery = "UPDATE reservations SET id_user = ?, id_event = ?, id_salle = ?, id_terrain = ?, date_reservation = ? WHERE id_reservation = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setObject(1, idUser);
            preparedStatement.setObject(2, idEvent);
            preparedStatement.setObject(3, idSalle);
            preparedStatement.setObject(4, idTerrain);
            preparedStatement.setString(5, dateReservation);
            preparedStatement.setInt(6, idReservation);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reservation modifiee avec succes !");
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// supprimer reservation
    public static void supprimerReservation(Connection connection, int idReservation) {
        try {
            String deleteQuery = "DELETE FROM reservations WHERE id_reservation = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, idReservation);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Reservation supprimee avec succes ");
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



