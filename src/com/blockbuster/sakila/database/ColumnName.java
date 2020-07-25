package com.blockbuster.sakila.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author Ben Plante
 *
 * Annotation used to decorate fields with a Column Name
 * for use in a TableModel
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
	String columnName();
}
