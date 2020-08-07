package com.blockbuster.sakila.ui.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.blockbuster.sakila.ui.utils.Checkable.SelectAll;

/**
 *  @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *  
 *  CheckBoxModel to hold a list of checkable items
 */

public class ComboCheckBoxModel<E> extends AbstractListModel<Checkable<E>> implements ComboBoxModel<Checkable<E>> {
	
	private List<Checkable<E>> items;
	private Checkable<?> lastSelectedItem;
	
	public ComboCheckBoxModel() {
		this(new ArrayList<>());
	}
	
	public ComboCheckBoxModel(List<E> items) {
		this.items = new ArrayList<>();
		if (items == null) items = new ArrayList<>();
		this.items.add(new Checkable.SelectAll<>());
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
	}
	
	public List<E> getItems() {
		return this.items.parallelStream()
				.filter(i -> !(i instanceof SelectAll))
				.map(i -> i.getData()).collect(Collectors.toList());
	}
	
	public void setItems(List<E> items) {
		this.items = Stream.concat(
					Stream.of(new Checkable.SelectAll<E>()),
					items.parallelStream().map(Checkable<E>::new)
				).collect(Collectors.toList());
		super.fireContentsChanged(this, 0, this.getSize());
	}
	
	public List<E> getAllSelected() {
		return items.parallelStream()
				.filter(c -> c.isSelected() && !(c instanceof SelectAll))
				.map(i -> i.getData())
				.collect(Collectors.toList());
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if (anItem instanceof Checkable<?>) {
			if (anItem instanceof Checkable.SelectAll<?>) {
				boolean newSetting = !((Checkable.SelectAll<?>)anItem).isSelected();
				this.items.forEach(i -> i.setSelected(newSetting));
			}
			else {
				int idx = this.items.indexOf(anItem);
				if (idx != -1) {
					this.lastSelectedItem = (Checkable<?>)anItem;
					var item = this.items.get(idx);
					item.setSelected(!item.isSelected());
				}
			}
			super.fireContentsChanged(this, -1, -1);
		}
	}

	@Override
	public Object getSelectedItem() {
		return this.lastSelectedItem;
	}
}
