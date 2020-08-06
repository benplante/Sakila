package com.blockbuster.sakila.ui.utils;

import java.lang.reflect.Array;
import java.util.List;

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
