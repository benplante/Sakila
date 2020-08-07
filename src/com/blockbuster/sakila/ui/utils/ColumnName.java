package com.blockbuster.sakila.ui.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Annotation used to decorate fields with a Column Name for use in a
 *         TableModel
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
	String columnName();
}
