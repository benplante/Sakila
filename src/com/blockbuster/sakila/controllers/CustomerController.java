package com.blockbuster.sakila.controllers;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

public class CustomerController {
	private SakilaDatabase db;
	
	private TableViewModel<CustomerViewModel> model;
	
	public CustomerController(SakilaDatabase db) {
		this.db = db;
		
		model = new TableViewModel<>(db.selectCustomers(), CustomerViewModel.class);
	}
}
