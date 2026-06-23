package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Attendance;
import model.CheckResult;
import service.AttendanceCheckService;
import service.SummaryService;
import util.CsvUtil;

public class MainFrame extends JFrame{

	private JTextField fileField;
	
	private DefaultTableModel tableModel;
	
	private List<CheckResult> resultList;
	
	public MainFrame() {
		
		setTitle("勤怠チェックシステム");
		
		setSize(700, 500);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		
		//上部パネル
		JPanel topPanel= new JPanel();
		
		topPanel.setLayout(new FlowLayout());
		
		fileField= new JTextField(30);
		
		JButton selectButton= new JButton("CSV選択");
		
		JButton executeButton = new JButton("チェック実行");
		
		JButton exportButton = new JButton("結果CSV出力");
		
		JButton summaryButton = new JButton("集計CSV出力");
		
		topPanel.add(fileField);
		
		topPanel.add(selectButton);
		
		topPanel.add(executeButton);
		
		topPanel.add(exportButton);
		
		topPanel.add(summaryButton);
		
		add(topPanel, BorderLayout.NORTH);
		
		//テーブル
		String[] columns = {
				"社員ID",
				"日付",
				"判定",
				"備考"
		};
		
		tableModel = new DefaultTableModel(columns, 0);
		
		JTable table = new JTable(tableModel);
		
		table.setDefaultRenderer(Object.class, new ResultTableCellRenderer());
		
		JScrollPane scrollPane = 
				new JScrollPane(table);
		
		add(scrollPane, BorderLayout.CENTER);
		
		//CSV選択
		selectButton.addActionListener(e->{
			
			JFileChooser chooser =
					new JFileChooser();
			
			int result =
					chooser.showOpenDialog(null);
			
			if(result==JFileChooser.APPROVE_OPTION) {
				
				File file =
						chooser.getSelectedFile();
				
				fileField.setText(
						file.getAbsolutePath()
						);
			}
		});
		
		//チェック実行
		executeButton.addActionListener(e->{
			
			try {
				String path=
						fileField.getText();
				
				List<Attendance> attendanceList=
						CsvUtil.readAttendanceCsv(path);
				
				AttendanceCheckService service=
						new AttendanceCheckService();
				
				List<CheckResult> results=
						service.check(attendanceList);
				
				resultList= results;
				
				//テーブルの初期化
				tableModel.setRowCount(0);
				
				//表示
				for(CheckResult r : results) {
					
					tableModel.addRow(new Object[] {
							r.getEmployeeId(),
							r.getDate(),
							r.getStatus(),
							r.getRemark()
					});
				}
			}catch(Exception ex) {
				
				ex.printStackTrace();
			}
		});
		
		exportButton.addActionListener(e->{
			
			try {
				
				if(resultList == null ||
						resultList.isEmpty()) {
					
					javax.swing.JOptionPane.showMessageDialog(this,
							"先にチェックを実行してください"
							);
					
					return;
				}
				
				CsvUtil.writeResultCsv(
						"result_attendance.csv",
						resultList
						);
				
				javax.swing.JOptionPane.showMessageDialog(this,
						"result_attendance.csvを出力しました");
			}catch(Exception ex) {
				ex.printStackTrace();
				
				javax.swing.JOptionPane.showMessageDialog(this,
						"CSV出力に失敗しました");
			}
		});
		
		summaryButton.addActionListener(e -> {
			try {
				if(resultList == null||
						resultList.isEmpty()) {
					javax.swing.JOptionPane.showMessageDialog(this, "先にチェックを実行してください");
					
					return;
				}
				
				SummaryService summaryService =
						new SummaryService();
				
				Map<String, int[]> summary =
						summaryService.summarize(resultList);
				
				CsvUtil.writeSummaryCsv("summary.csv", summary);
					
				javax.swing.JOptionPane.showMessageDialog(this,"summary.csvを出力しました");
			}catch(Exception ex) {
				ex.printStackTrace();
				javax.swing.JOptionPane.showMessageDialog(this, "集計CSV出力に失敗しました");
			}
		});
	}
}
