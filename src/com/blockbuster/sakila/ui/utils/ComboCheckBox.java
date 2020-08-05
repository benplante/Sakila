package com.blockbuster.sakila.ui.utils;

import java.util.List;

import javax.swing.JComboBox;

public class ComboCheckBox<E> extends JComboBox<Checkable<E>> {
	
	private ComboCheckBoxModel<E> model;
	
	public ComboCheckBox(String title) {
		this(null, title);
	}
	
	public ComboCheckBox(List<E> items, String title) {
		super();
		model = new ComboCheckBoxModel<>(items);
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
		model.setItems(items);
	}
	
	public List<E> getSelectedItems() {
		return model.getAllSelected();
	}
}
