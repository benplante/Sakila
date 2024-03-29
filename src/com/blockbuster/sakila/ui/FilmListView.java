package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.FilmController;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.FilmViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Panel for viewing a list of all Films in the database Displays
 *         films in a JTable and contains buttons to add, update and delete a
 *         film
 */

 @SuppressWarnings("serial")
public class FilmListView extends JPanel {
	// FilmListView members
	private JTable filmTable;
	private JButton btnAdd, btnDelete;
	private TableViewModel<FilmViewModel> model;
	
	/** 
	 * Method Name: FilmListView
	 * Purpose: FilmListView is a JPanel to show list of films and its attributes.
	 * Accepts: FilmController to handle for listener events.
	 * Return: A FilmListView object.
	 */
	public FilmListView(FilmController controller) {
		super();

		filmTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(filmTable);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> controller.openAddFilmForm());
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(e -> controller.deleteFilm());
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnDelete);

		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.add(btnPanel);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	/** 
	 * Method Name: setFilmList
	 * Purpose: To populate the FilmListView.
	 * Accepts: A TableViewModel object of FilmViewModel objects. 
	 * Return: Nothing.
	 */
	public void setFilmList(TableViewModel<FilmViewModel> model) {
		this.model = model;
		filmTable.setModel(model);
	}
	
	/** 
	 * Method Name: getSelectedFilm
	 * Purpose: To get the user selected row of the list. 
	 * 					Used in FilmController's deleteFilm().
	 * Accepts: Nothing.
	 * Return: The selected row's FilmViewModel.
	 */
	public FilmViewModel getSelectedFilm() {
		if (filmTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Please select a film.", "Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return model.getAtRow(filmTable.getSelectedRow());
	}
}
