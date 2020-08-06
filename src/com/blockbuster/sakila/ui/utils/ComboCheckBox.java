package com.blockbuster.sakila.ui.utils;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;

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

            if (selectedItemReminder != model.getSelectedItem()) {
                selectedItemChanged();
            }
        }
        fireActionEvent();
    }
	
	@Override
	public void setPopupVisible(boolean visible) {
		if (visible) {
			super.setPopupVisible(visible);
		}
	}
	
	public void setItems(List<E> items) {
		this.model.setItems(items);
		this.setModel(this.model);
	}
	
	public List<E> getSelectedItems() {
		return model.getAllSelected();
	}
}
