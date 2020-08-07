package com.blockbuster.sakila.ui.utils;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *  @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *  
 *  Renderer class to show a dropdown list of checkboxes. 
 *  Top of the list is a title string.
 */

public class ComboCheckBoxRenderer<T> implements ListCellRenderer<Checkable<T>> {

	private JCheckBox chkItem;
	private String title;
	
	public ComboCheckBoxRenderer(String title) {
		chkItem = new JCheckBox("");
		this.title = title;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Checkable<T>> list, Checkable<T> value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value == null) {
			return new JLabel(title);
		}
		else {
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
	}
}