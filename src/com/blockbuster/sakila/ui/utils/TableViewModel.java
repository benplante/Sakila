package com.blockbuster.sakila.ui.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Concrete implementation of AbstractTableModel using a generic List as
 *         the collection type. Reflection is used to access specific cells in
 *         the data
 *
 * @param <T> A view model type
 */
public class TableViewModel<T> extends AbstractTableModel {
	private List<T> data;
	private Class<T> type;

	public TableViewModel(Class<T> type) {
		this(new ArrayList<>(), type);
	}
	
	public TableViewModel(List<T> data, Class<T> type) {
		super();
		this.data = data;
		this.type = type;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return type.getFields().length;
	}

	/**
	 * Uses reflection to get the column name from the
	 * annoted ColumnName of the member
	 */
	@Override
	public String getColumnName(int column) {
		Field f = type.getFields()[column];

		var c = f.getAnnotation(ColumnName.class);

		if (c != null)
			return c.columnName();
		else
			return f.getName();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Field[] fields = type.getFields();
		T row = data.get(rowIndex);

		try {
			return fields[columnIndex].get(row);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the view model for the specified row
	 * 
	 * @param rowIndex row index
	 * @return View Model
	 */
	public T getAtRow(int rowIndex) {
		return data.get(rowIndex);
	}

	/**
	 * Resets the data in the model
	 * @param data list to set
	 */
	public void setData(List<T> data) {
		this.data = data;
		this.fireTableDataChanged();
	}
}
