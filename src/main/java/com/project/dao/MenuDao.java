package com.project.dao;

import java.util.List;
import com.project.model.Menu;

	public interface MenuDao {

	    void addMenu(Menu menu);

	    Menu getMenuById(int menuId);

	    List<Menu> getAllMenuByRestaurant(int restaurantId);

	    void updateMenu(Menu menu);

	    void deleteMenu(int menuId);
	}
	
