package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ReportController;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.viewmodels.ReportViewModel;

public class ReportListView extends JPanel {
	private JTable reportTable;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;

	private TableViewModel<ReportViewModel> model;
	
	public ReportListView(ReportController controller) {
		super();

		reportTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(reportTable);

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
}
