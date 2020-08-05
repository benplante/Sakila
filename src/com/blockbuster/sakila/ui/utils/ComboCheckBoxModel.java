package com.blockbuster.sakila.ui.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ComboCheckBoxModel<E> extends AbstractListModel<Checkable<E>> implements ComboBoxModel<Checkable<E>> {
	
	private List<Checkable<E>> items;
	private Checkable<?> lastSelectedItem;
	
	public ComboCheckBoxModel() {
		this(new ArrayList<>());
	}
	
	public ComboCheckBoxModel(List<E> items) {
		if (items == null) items = new ArrayList<>();
		items.forEach(i -> this.items.add(new Checkable<>(i)));
		lastSelectedItem = null;
	}
	
	public void addItem(E item) {
		this.items.add(new Checkable<>(item));
		super.fireIntervalAdded(this, items.size() - 1, items.size() - 1);
	}

	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public Checkable<E> getElementAt(int index) {
		if (index < 0 || index >= items.size()) 
			return null;
		return items.get(index);
	}

	public List<Checkable<E>> getCheckableItems() {
		return this.items;
	}
	
	public void setCheckableItems(List<Checkable<E>> items) {
		this.items = items;
		//super.fireContentsChanged(this, 0, this.items.size() - 1);
	}
	
	public List<E> getItems() {
		return this.items.parallelStream().map(i -> i.getData()).collect(Collectors.toList());
	}
	
	public void setItems(List<E> items) {
		this.items = items.parallelStream().map(Checkable<E>::new).collect(Collectors.toList());
		//super.fireContentsChanged(this, 0, this.items.size() - 1);
	}
	
	public List<E> getAllSelected() {
		return items.parallelStream().filter(c -> c.isSelected()).map(i -> i.getData()).collect(Collectors.toList());
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if (anItem instanceof Checkable<?>) {
			
			int idx = this.items.indexOf(anItem);
			if (idx != -1) {
				this.lastSelectedItem = (Checkable<?>)anItem;
				var item = this.items.get(idx);
				item.setSelected(!item.isSelected());
				System.out.println(item.getData().toString() + ": " + item.isSelected());
			}
			super.fireContentsChanged(this, -1, -1);
		}
	}

	@Override
	public Object getSelectedItem() {
		return this.lastSelectedItem;
	}
}
