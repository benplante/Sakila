package com.blockbuster.sakila.controllers;

import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.FilmForm;
import com.blockbuster.sakila.ui.FilmListView;
import com.blockbuster.sakila.viewmodels.FilmViewModel;

public class FilmController
{

	private SakilaDatabase db;
	private FilmListView filmListViewPanel;
	private FilmForm filmFormFrame;

	private TableViewModel<FilmViewModel> model;

	public FilmController(SakilaDatabase db) {
		this.db = db;

		filmListViewPanel = new FilmListView(this);   
		filmFormFrame = new FilmForm(this);
	}

	public JPanel getPanel() {
		return filmListViewPanel;
	}
}
