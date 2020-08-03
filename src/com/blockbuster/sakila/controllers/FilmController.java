package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.FilmForm;
import com.blockbuster.sakila.ui.FilmListView;
import com.blockbuster.sakila.viewmodels.ActorViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.FilmViewModel;

public class FilmController
{

	private SakilaDatabase db;
	//private FilmListView filmListViewPanel;
	private FilmForm filmFormFrame;

	private TableViewModel<ActorViewModel> model;
	private TableViewModel<CategoryViewModel> categoryModel;


	public FilmController(SakilaDatabase db) {
		this.db = db;
		filmFormFrame = new FilmForm(this);		
		model = new TableViewModel<>(getActorsFromDB(), ActorViewModel.class);
		filmFormFrame.setActors(getActorsFromDB());
		categoryModel = new TableViewModel<>(getCategoriesFromDB(),CategoryViewModel.class);
	    filmFormFrame.setCategories(getCategoriesFromDB());
	}

	private List<ActorViewModel> getActorsFromDB() {
		try {
			return db.selectActors();
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}
	
	private List<CategoryViewModel> getCategoriesFromDB() {
        try {
            return db.selectCategories();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
	  }
	public JPanel getPanel() {
		return filmFormFrame;
	}
	
	public void confirmAddFilm() {
		//customerListViewPanel.setEnabled(true);
		FilmViewModel vm = filmFormFrame.getFilm();
		String type = "";
		try {
			db.insertFilm(vm);
			JOptionPane.showMessageDialog(filmFormFrame, type + " film succeeded!");
			filmFormFrame.setVisible(false);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(filmFormFrame,  "Error: " + e.getMessage(), type + " failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
