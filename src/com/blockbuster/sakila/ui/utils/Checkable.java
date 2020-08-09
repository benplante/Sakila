package com.blockbuster.sakila.ui.utils;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *     
 * Checkable wrapper to allow for toggleable items
 * 
 * @param <E> type of the checkable item
 */

public class Checkable<E> {
	private final E data;
	private boolean isSelected;

	public Checkable(E data) {
		this(data, false);
	}
	
	public Checkable(E data, boolean selected) {
		this.data = data;
		this.isSelected = selected;
	}

	public E getData() {
		return this.data;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public String toString() {
		return data.toString();
	}
	
	/**
	 * 
	 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
	 * 
	 * Special sub-type of Checkable that has no data but shows as 
	 * "Select All". used to provide a checkbox to select all items
	 *
	 * @param <E> type of the checkable item
	 */
	public static class SelectAll<E> extends Checkable<E> {
		public SelectAll() {
			super(null);
		}
		
		@Override
		public String toString() {
			return "Select All";
		}
	}
}
