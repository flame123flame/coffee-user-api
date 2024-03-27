package framework.model;

import org.apache.poi.ss.usermodel.CellStyle;

import lombok.Data;

@Data
public class ColumnValueModel {
	private String cellValue;
	private CellStyle cellStyle;
}
