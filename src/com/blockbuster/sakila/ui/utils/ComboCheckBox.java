package com.blockbuster.sakila.ui.utils;

import java.util.List;

import javax.swing.JComboBox;

/**
 *  @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *  
 *  ComboBox extension to hold a dropdown list of checkboxes 
 *  to allow for multiple selections
 */

@SuppressWarnings("serial")
public class ComboCheckBox<E> extends JComboBox<Checkable<E>> {
	
	private ComboCheckBoxModel<E> model;
	
	public ComboCheckBox(String title) {
		this(new ComboCheckBoxModel<>(null), title);
	}
	
	public ComboCheckBox(List<E> items, String title) {
		this(new ComboCheckBoxModel<>(items), title);
	}
	
	public ComboCheckBox(ComboCheckBoxModel<E> model, String title) {
		super();
		this.model = model;
		this.setModel(model);
		this.setRenderer(new ComboCheckBoxRenderer<E>(title));
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
        if (anObject != null) {
            model.setSelectedItem(anObject);

            // Copied from JComboBox source
            if (selectedItemReminder != model.getSelectedItem()) {
                selectedItemChanged();
            }
        }
        fireActionEvent();
    }
	
	@Override
	public void setPopupVisible(boolean visible) {
		// Doesn't dismiss the ComboBox when a user selects a checkable item
		if (visible) {
			super.setPopupVisible(visible);
		}
	}
	
	/**
	 * Sets the items of the model
	 * @param items Items to set
	 */
	public void setItems(List<E> items) {
		this.model.setItems(items);
		this.setModel(this.model);
	}
	
	/**
	 * Get all selected items from the model
	 * @return List of selected items
	 */
	public List<E> getSelectedItems() {
		return model.getAllSelected();
	}
}
