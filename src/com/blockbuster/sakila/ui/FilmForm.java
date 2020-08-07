package com.blockbuster.sakila.ui;

import java.awt.GridLayout;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.FilmController;
import com.blockbuster.sakila.ui.utils.ComboCheckBox;
import com.blockbuster.sakila.viewmodels.ActorViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.FilmViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Film form in a JFrame contains text fields for a films Insert and
 *         Delete
 */

public class FilmForm extends JFrame {
	// FilmForm members
	private JTextField txtTitle, txtDescription, txtReleaseYear, txtReplacementCost, txtLength;
	private JButton btnConfirm, btnCancel;
	private ComboCheckBox<ActorViewModel> cmbActors;
	private ActorViewModel[] actors;
	private JComboBox<CategoryViewModel> cmbCategories;
	private CategoryViewModel[] categories;
	private JComboBox<Integer> cmbDurations;
	private JComboBox<String> cmbLanguages;
	private JComboBox<String> cmbSpecialFeature;
	private JComboBox<String> cmbRating;
	private FilmViewModel film;

	/** 
	 * Method Name: FilmForm
	 * Purpose: FilmForm is a JFrame for user input.
	 * Accepts: FilmController to handle for listener events. 
	 * Return: A FilmForm object.
	 */
	public FilmForm(FilmController controller) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel wrapper = new JPanel();
		txtTitle = new JTextField();
		txtDescription = new JTextField();
		txtReleaseYear = new JTextField();
		txtLength = new JTextField();
		txtReplacementCost = new JTextField();
		cmbActors = new ComboCheckBox<>("Actors");
		cmbCategories = new JComboBox<CategoryViewModel>();
		
		// loading combo boxes with data base information
		cmbDurations = new JComboBox<Integer>();
		Integer[] durationArr = { 3, 4, 5, 6, 7 };
		cmbDurations.setModel(new DefaultComboBoxModel<Integer>(durationArr));

		cmbLanguages = new JComboBox<String>();
		String[] langArr = { "English", "Italian", "Japanese", "Mandarin", "French", "German" };
		cmbLanguages.setModel(new DefaultComboBoxModel<String>(langArr));

		String[] ratingArr = { "G", "PG", "PG-13", "R", "NC-17" };
		cmbRating = new JComboBox<String>();
		cmbRating.setModel(new DefaultComboBoxModel<String>(ratingArr));

		cmbSpecialFeature = new JComboBox<String>();
		String[] specialFeaturesArr = { "Trailers", "Commentaries", "Deleted Scenes", "Behind the Scenes" };
		cmbSpecialFeature.setModel(new DefaultComboBoxModel<String>(specialFeaturesArr));

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddFilm());

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> controller.closeFilmForm());

		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		txtPanel.setLayout(new GridLayout(0, 1, 0, 5));
		txtPanel.add(new JLabel("Title:"));
		txtPanel.add(txtTitle);
		txtPanel.add(new JLabel("Description:"));
		txtPanel.add(txtDescription);
		txtPanel.add(new JLabel("Category:"));
		txtPanel.add(cmbCategories);
		txtPanel.add(new JLabel("Language:"));
		txtPanel.add(cmbLanguages);
		txtPanel.add(new JLabel("Actor:"));
		txtPanel.add(cmbActors);
		txtPanel.add(new JLabel("Release Year:"));
		txtPanel.add(txtReleaseYear);
		txtPanel.add(new JLabel("Rental Duration:"));
		txtPanel.add(cmbDurations);
		txtPanel.add(new JLabel("Length:"));
		txtPanel.add(txtLength);
		txtPanel.add(new JLabel("Replacement Cost:"));
		txtPanel.add(txtReplacementCost);
		txtPanel.add(new JLabel("Rating:"));
		txtPanel.add((cmbRating));
		txtPanel.add(new JLabel("Special Features:"));
		txtPanel.add(cmbSpecialFeature);

		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnConfirm);
		btnPanel.add(Box.createHorizontalStrut(50));
		btnPanel.add(btnCancel);

		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
		wrapper.add(txtPanel);
		wrapper.add(btnPanel);

		this.add(wrapper);
		this.setContentPane(wrapper);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	/** 
	 * Method Name: getFilm
	 * Purpose: To validate input and get FilmViewModel when user selects 'Confirm' in FilmForm.
	 * Accepts: Nothing.
	 * Returns: FilmViewModel object populated with user input.
	 */
	public FilmViewModel getFilm() {
		if (txtTitle.getText().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a title", "Invalid", JOptionPane.WARNING_MESSAGE);
			txtTitle.requestFocus();
			return null;
		}
		film.title = txtTitle.getText();

		film.description = txtDescription.getText();

		try {
			film.releaseYear = Integer.parseInt(txtReleaseYear.getText());

			int thisYear = Year.now().getValue();

			if (film.releaseYear < 1 || film.releaseYear > thisYear) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid year.\nEx: 2020", "Invalid",
					JOptionPane.WARNING_MESSAGE);
			txtReleaseYear.requestFocus();
			return null;
		}

		film.setLanguageId((int) cmbLanguages.getSelectedIndex() + 1);

		// decide rental rate based on its release year
		if (film.releaseYear > 2001) {
			film.rentalRate = 4.99;
		} else if (film.releaseYear > 1991) {
			film.rentalRate = 2.99;
		} else if (film.releaseYear > 1981) {
			film.rentalRate = 0.99;
		}

		try {
			film.length = Integer.parseInt(txtLength.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid length.", "Invalid",
					JOptionPane.WARNING_MESSAGE);
			txtLength.requestFocus();
			return null;
		}

		film.rentalDuration = (int) cmbDurations.getSelectedItem();

		try {
			film.replacementCost = Double.parseDouble(txtReplacementCost.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid amount.", "Invalid",
					JOptionPane.WARNING_MESSAGE);
			txtReplacementCost.requestFocus();
			return null;
		}

		film.rating = (String) cmbRating.getSelectedItem();
		film.setActors(cmbActors.getSelectedItems().stream().map(a -> a.getActorId()).collect(Collectors.toList()));
		film.setCategoryId(categories[cmbCategories.getSelectedIndex()].getCategoryId());
		film.specialFeatures = (String) cmbSpecialFeature.getSelectedItem();
		return film;
	}
	
	/** 
	 * Method Name: setFilm
	 * Purpose: Instantiates FilmForm's FilmViewModel and clears all fields 
	 * 					when user selects 'Add' from FilmListView.
	 * Accepts: Nothing.
	 * Returns: Nothing.
	 */
	public void setFilm() {
		this.film = new FilmViewModel();
		this.txtTitle.setText("");
		this.txtDescription.setText("");
		this.cmbCategories.setSelectedIndex(0);
		this.cmbLanguages.setSelectedIndex(0);
		this.cmbActors.setSelectedIndex(-1);
		this.txtReleaseYear.setText("");
		this.cmbDurations.setSelectedIndex(0);
		this.txtLength.setText("");
		this.txtReplacementCost.setText("");
		this.cmbRating.setSelectedIndex(0);
		this.cmbSpecialFeature.setSelectedIndex(0);
	}
	
	/** 
	 * Method Name: setActors
	 * Purpose: Instantiates ActorViewModel array 
	 * 					and sets the array to the ComboCheckBox.
	 * Accepts: A list of ActorViewModel objects.
	 * Returns: Nothing.
	 */
	public void setActors(List<ActorViewModel> actors) {
		ActorViewModel[] arr = new ActorViewModel[actors.size()];
		actors.toArray(arr);
		this.actors = arr;
		cmbActors.setItems(actors);
	}

	/** 
	 * Method Name: setCategories
	 * Purpose: Instantiates CategoryViewModel array
	 * 					and sets the array to the JComboBox.
	 * Accepts: A list of CategoryViewModel objects.
	 * Returns: Nothing.
	 */
	public void setCategories(List<CategoryViewModel> categories) {
		CategoryViewModel[] arr = new CategoryViewModel[categories.size()];
		categories.toArray(arr);
		this.categories = arr;
		cmbCategories.setModel(new DefaultComboBoxModel<CategoryViewModel>(arr));
	}
}
