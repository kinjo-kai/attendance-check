package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ResultTableCellRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(
	JTable table,
	Object value,
	boolean isSelected,
	boolean hasFocus,
	int row,
	int column) {
		
		Component c =
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String status =
				table.getValueAt(row, 2).toString();
		
		if("エラー".equals(status)) {
			c.setForeground(Color.RED);
		}else if("遅刻".equals(status)){
			c.setForeground(Color.ORANGE);
		}else {
			c.setForeground(Color.BLACK);
		}
		return c;
	}
}
