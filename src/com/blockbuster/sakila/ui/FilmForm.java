package com.blockbuster.sakila.ui;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.FilmController;
import com.blockbuster.sakila.viewmodels.ActorViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.FilmViewModel;

public class FilmForm extends JPanel{
	
	
	private JTextField txtTitle, txtDescription, txtReleaseYear, txtRentalDuration,
	txtRentalRate, txtReplacementCost, txtRating;
	private JButton btnConfirm, btnCancel;
	private JComboBox<ActorViewModel> cmbActors;
	private ActorViewModel[] actors;
	private JComboBox<CategoryViewModel> cmbCategories;
	private CategoryViewModel[] categories;


	public FilmForm(FilmController controller) {

	//	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel wrapper = new JPanel();
		txtTitle = new JTextField();
		txtDescription = new JTextField();
		txtReleaseYear = new JTextField();
		txtRentalDuration = new JTextField();
		txtRentalRate = new JTextField();
		txtReplacementCost = new JTextField();
		txtRating = new JTextField();
		cmbActors = new JComboBox<ActorViewModel>();
	    cmbCategories = new JComboBox<CategoryViewModel>();


		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddFilm());

		btnCancel = new JButton("Cancel");
		//btnCancel.addActionListener(e -> controller.closeFilmForm());
		

		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		txtPanel.setLayout(new BoxLayout(txtPanel, BoxLayout.PAGE_AXIS));
		txtPanel.add(new JLabel("Title:"));
		txtPanel.add(txtTitle);
		txtPanel.add(new JLabel("Description:"));
		txtPanel.add(txtDescription);
	    txtPanel.add(new JLabel("Category:"));
	    txtPanel.add(cmbCategories);
		txtPanel.add(new JLabel("Actor:"));
		txtPanel.add(cmbActors);
		txtPanel.add(new JLabel("Realse Year:"));
		txtPanel.add(txtReleaseYear);
		txtPanel.add(new JLabel("Rental Duration:"));
		txtPanel.add(txtRentalDuration);
		txtPanel.add(new JLabel("Rental Rate:"));
		txtPanel.add(txtRentalRate);
		txtPanel.add(new JLabel("Replacement Cost:"));
		txtPanel.add(txtReplacementCost);
		txtPanel.add(new JLabel("Rating:"));
		txtPanel.add(txtRating);

		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnConfirm);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnCancel);

		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
		wrapper.add(txtPanel);
		wrapper.add(btnPanel);
		
		this.add(wrapper);
//        this.setContentPane(wrapper);
//		this.pack();
//		this.setResizable(false);
//		this.setLocationRelativeTo(null);
	}
	
	public void setActors(List<ActorViewModel> actors) {
		ActorViewModel[] arr = new ActorViewModel[actors.size()];
		actors.toArray(arr);
		this.actors = arr;
		cmbActors.setModel(new DefaultComboBoxModel<ActorViewModel>(arr));
	}
	
	  public void setCategories(List<CategoryViewModel> categories) {
	     CategoryViewModel[] arr = new CategoryViewModel[categories.size()];
	     categories.toArray(arr);
	     this.categories = arr;
	     cmbCategories.setModel(new DefaultComboBoxModel<CategoryViewModel>(arr));
	    }


	public FilmViewModel getFilm()
	{
		FilmViewModel film = new FilmViewModel();
		film.title = txtTitle.getText();
		film.description = txtDescription.getText();
		film.release_year = txtReleaseYear.getText();
		film.rental_duration = Integer.parseInt(txtRentalDuration.getText());
		film.replacement_cost = Double.parseDouble(txtReplacementCost.getText());
		film.rating = txtRating.getText();
		
		return film;
	}
}
