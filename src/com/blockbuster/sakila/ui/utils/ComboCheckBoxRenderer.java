package com.blockbuster.sakila.ui.utils;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboCheckBoxRenderer<T> implements ListCellRenderer<Checkable<T>> {

	private JLabel lblList;
	private JCheckBox chkItem;
	private String title;
	
	public ComboCheckBoxRenderer(String title) {
		lblList = new JLabel("");
		chkItem = new JCheckBox("");
		this.title = title;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Checkable<T>> list, Checkable<T> value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (index < 0) {
			lblList.setText(title);
			return lblList;
		}
		else {
			if (value != null) {
				chkItem.setText(value.toString());
				chkItem.setSelected(value.isSelected());
				chkItem.addActionListener(e -> System.out.println("Action" + e.getID()));
				
		        if (isSelected) {
		            chkItem.setBackground(list.getSelectionBackground());
		            chkItem.setForeground(list.getSelectionForeground());
		        } else {
		            chkItem.setBackground(list.getBackground());
		            chkItem.setForeground(list.getForeground());
		        }
		        
		        return chkItem;
			}
			else {
				lblList.setText("");
				return lblList;
			}
		}
	}
}