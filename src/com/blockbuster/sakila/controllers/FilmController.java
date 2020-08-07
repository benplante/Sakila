package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.FilmForm;
import com.blockbuster.sakila.ui.FilmListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.ActorViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.FilmViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Film controller. Interacts with FilmForm, FilmListView and SakilaDatabase.
 */


public class FilmController
{

	private SakilaDatabase db;
	private FilmListView filmListViewPanel;
	private FilmForm filmFormFrame;
	private TableViewModel<FilmViewModel> model;
	private TableViewModel<ActorViewModel> actorModel;
	private TableViewModel<CategoryViewModel> categoryModel;

	public FilmController(SakilaDatabase db) {
		this.db = db;
		filmListViewPanel = new FilmListView(this);
		filmFormFrame = new FilmForm(this);	
		model = new TableViewModel<FilmViewModel>(getFilmsFromDB(), FilmViewModel.class);
		filmListViewPanel.setFilmList(model);
		actorModel = new TableViewModel<ActorViewModel>(getActorsFromDB(), ActorViewModel.class);
		filmFormFrame.setActors(getActorsFromDB());
		categoryModel = new TableViewModel<CategoryViewModel>(getCategoriesFromDB(),CategoryViewModel.class);
	  filmFormFrame.setCategories(getCategoriesFromDB());
	}
	
	private List<FilmViewModel> getFilmsFromDB() {
		try {
			return db.selectFilms();
		} catch (SQLException e) {
			return new ArrayList<>();
		}
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
		return filmListViewPanel;
	}
	
	public void openAddFilmForm() {
		filmListViewPanel.setEnabled(false);
		filmFormFrame.setName("Add Film");
		filmFormFrame.setVisible(true);
	}
	
	public void closeFilmForm() {
		filmListViewPanel.setEnabled(true);
		filmFormFrame.setVisible(false);
	}
	
	public void confirmAddFilm() {
		filmListViewPanel.setEnabled(true);
		FilmViewModel vm = filmFormFrame.getFilm();
		if(vm == null) {
			return;
		}
		try {
			db.insertFilm(vm);
			JOptionPane.showMessageDialog(filmFormFrame,"Add film succeeded!");
			filmFormFrame.setVisible(false);
			model.setData(getFilmsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(filmFormFrame,  "Error: " + e.getMessage(), "Add failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteFilm() {
		FilmViewModel vm = filmListViewPanel.getSelectedFilm();
		if (vm == null) {
			return;
		}
		try {
			db.deleteFilm(vm);
			JOptionPane.showMessageDialog(filmFormFrame, "Delete film succeeded!");
			model.setData(getFilmsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(filmFormFrame,  "Error: " + e.getMessage(), "Delete failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
