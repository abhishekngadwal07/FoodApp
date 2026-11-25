package com.project.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.dao.MenuDao;
import com.project.model.Menu;

import Util.DBConnection;

public class MenuDaoImpl implements MenuDao {

    private Connection connection;

    public MenuDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public MenuDaoImpl() {
    	connection = DBConnection.getConnection();
    }

	@Override
    public void addMenu(Menu menu) {

        String query = "INSERT INTO menu (restaurant_id, item_name, description, price, is_available, image_url) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, menu.getRestaurantId());
            ps.setString(2, menu.getItemName());
            ps.setString(3, menu.getDescription());
            ps.setDouble(4, menu.getPrice());
            ps.setBoolean(5, menu.isAvailable());
            ps.setString(6, menu.getImageUrl());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Menu getMenuById(int menuId) {

        String query = "SELECT * FROM menu WHERE menu_id = ?";
        Menu menu = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, menuId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                menu = extractMenu(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menu;
    }

    @Override
    public List<Menu> getAllMenuByRestaurant(int restaurantId) {

        List<Menu> list = new ArrayList<>();
        String query = "SELECT * FROM menu WHERE restaurant_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractMenu(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateMenu(Menu menu) {

        String query = "UPDATE menu SET item_name=?, description=?, price=?, is_available=?, image_url=? WHERE menu_id=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, menu.getItemName());
            ps.setString(2, menu.getDescription());
            ps.setDouble(3, menu.getPrice());
            ps.setBoolean(4, menu.isAvailable());
            ps.setString(5, menu.getImageUrl());
            ps.setInt(6, menu.getMenuId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMenu(int menuId) {

        String query = "DELETE FROM menu WHERE menu_id=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, menuId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Menu extractMenu(ResultSet rs) throws SQLException {

        Menu m = new Menu();

        m.setMenuId(rs.getInt("menu_id"));
        m.setRestaurantId(rs.getInt("restaurant_id"));
        m.setItemName(rs.getString("item_name"));
        m.setDescription(rs.getString("description"));
        m.setPrice(rs.getDouble("price"));
        m.setAvailable(rs.getBoolean("is_available"));
        m.setImageUrl(rs.getString("image_url"));

        return m;
    }
}