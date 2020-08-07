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
	// FilmController members
	private SakilaDatabase db;
	private FilmListView filmListViewPanel;
	private FilmForm filmFormFrame;
	private TableViewModel<FilmViewModel> model;

	/**
	 * Method Name: FilmController
	 * Purpose: FilmController is instantiated in SuperController with a Singleton instance of MySqlSakilaDatabase.
	 * 					FilmListView and FilmForm is instantiated and passed this controller for listener events.
	 * 					TableViewModel is instantiated with a list of FilmViewModels and populated via MySqlSakilaDatabase. 
	 * 					TableViewModel is then used to pass to the FilmListView to generate the list in its tab.
	 * Accepts: A SakilaDatabase object.
	 * Returns: A FilmController object.
	 */
	public FilmController(SakilaDatabase db) {
		this.db = db;
		filmListViewPanel = new FilmListView(this);
		filmFormFrame = new FilmForm(this);	
		model = new TableViewModel<FilmViewModel>(getFilmsFromDB(), FilmViewModel.class);
		filmListViewPanel.setFilmList(model);
	}
		
	/**
	 *  Method Name: refreshDB
	 *  Purpose: Refreshes CustomerFormFrame data on every tab change. Is essential if data from other tabs is deleted.
	 *  Accepts: Nothing.
	 *  Returns: Nothing.
	 */
	public void refreshDB() {
		filmFormFrame.setActors(getActorsFromDB());
		filmFormFrame.setCategories(getCategoriesFromDB());
	}
	
	/** 
	 * Method Name: getFilmsFromDB
	 * Purpose: Gets the films from the database.
	 * Accepts: Nothing.
	 * Return: A list of FilmViewModel objects.
	 */
	private List<FilmViewModel> getFilmsFromDB() {
		try {
			return db.selectFilms();
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}

	/** 
	 * Method Name: getActorsFromDB
	 * Purpose: Gets the actors from the database. Used for FilmForm's JComboBox cmbActors.
	 * Accepts: Nothing.
	 * Return: A list of ActorViewModel objects.
	 */
	private List<ActorViewModel> getActorsFromDB() {
		try {
			return db.selectActors();
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}
	
	/** 
	 * Method Name: getCategoriesFromDB
	 * Purpose: Gets the categories from the database. Used for FilmForm's JComboBox cmbCategories.
	 * Accepts: Nothing.
	 * Return: A list of CategoryViewModel objects.
	 */
	private List<CategoryViewModel> getCategoriesFromDB() {
		try {
	    return db.selectCategories();
		} catch (SQLException e) {
	    return new ArrayList<>();
		}
  }
	
	/** 
	 * Method Name: openAddFilmForm
	 * Purpose: The actions to be performed when listening for 'Add' button in FilmListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void openAddFilmForm() {
		filmListViewPanel.setEnabled(false);
		filmFormFrame.setTitle("Add Film");
		filmFormFrame.setFilm();
		filmFormFrame.setVisible(true);
	}
	
	/** 
	 * Method Name: deleteFilm
	 * Purpose: The actions to be performed when listening for 'Delete' button in FilmListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
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
	
	/** 
	 * Method Name: confirmAddFilm
	 * Purpose: The actions to be performed when listening for 'Confirm' button in FilmForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
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
	
	/** 
	 * Method Name: closeFilmForm
	 * Purpose: The actions to be performed when listening for 'Close' button in FilmForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void closeFilmForm() {
		filmListViewPanel.setEnabled(true);
		filmFormFrame.setVisible(false);
	}
	
	/** 
	 * Method Name: getPanel
	 * Purpose: Gets FilmController's FilmListView.
	 * 					Is used when adding a tab to the SuperController.
	 * Accepts: Nothing.
	 * Return: A FilmListView object.
	 */
	public JPanel getPanel() {
		return filmListViewPanel;
	}
}
