package com.example.touristguide.Repository;

import com.example.touristguide.Model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TouristRepository {
    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<TouristAttraction> findAllAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT a.id, a.name, a.description, a.city, GROUP_CONCAT(t.tag SEPARATOR ', ') AS tags "
                + "FROM attractions a "
                + "LEFT JOIN attraction_tags at ON a.id = at.attraction_id "
                + "LEFT JOIN tags t ON at.tag_id = t.id "
                + "GROUP BY a.id";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                List<String> tags = Arrays.asList(rs.getString("tags").split(", "));
                attractions.add(new TouristAttraction(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("city"),
                        tags
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractions;
    }

    public TouristAttraction save(TouristAttraction touristAttraction) {
        String sql = "INSERT INTO attractions (name, description, city) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, touristAttraction.getName());
            pstmt.setString(2, touristAttraction.getDescription());
            pstmt.setString(3, touristAttraction.getCity());


            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        touristAttraction.setId(rs.getInt(1));
                    }
                }
            }

            return touristAttraction;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String name) {
        String sql = "DELETE FROM attractions WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TouristAttraction update(String name, TouristAttraction updatedAttraction) {
        String sql = "UPDATE attractions SET description = ?, city = ? WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedAttraction.getDescription());
            pstmt.setString(2, updatedAttraction.getCity());
            pstmt.setString(3, name);
            // Jeg kan opdatere tags hvis nødvendigt

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return updatedAttraction;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}