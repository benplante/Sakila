package com.blockbuster.sakila.database;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Ben Plante
 *
 * Concrete implementation of AbstractTableModel using a generic List as the 
 * collection type. Reflection is used to access specific cells in the data
 *
 * @param <T> A view model type
 */
public class TableViewModel<T> extends AbstractTableModel {
	private List<T> data;
	private Class<T> type;
	
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
	
	@Override
	public String getColumnName(int column) {
		Field f = type.getFields()[column];
		
		var c = f.getAnnotation(ColumnName.class);
		
		if (c != null) return c.columnName();
		else return f.getName();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Field[] fields = type.getFields();
		T row = data.get(rowIndex);
		
		try {
			return fields[columnIndex].get(row);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	public T getAtRow(int rowIndex) {
		return data.get(rowIndex);
	}
	
	public void setData(List<T> data) {
		this.data = data;
		this.fireTableDataChanged();
	}
}
