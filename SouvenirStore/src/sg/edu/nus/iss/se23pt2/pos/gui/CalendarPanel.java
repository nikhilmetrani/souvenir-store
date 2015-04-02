package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarPanel extends OkCancelDialog{

	private static final long serialVersionUID = 1L;
	
    static JPanel calPanel;
    static JScrollPane calScrollPane;
    static JTable calTable;
    static DefaultTableModel calModel;
    static JButton btnPrev, btnNext;
    static JLabel lblMonth, lblYear;
    static JComboBox<String> cmbYear;
    static String selDate;	//selected date from calendar (in 'yyyy-MM-dd' format)
    
    static int realYear, realMonth, realDay, curYear, curMonth;
    final static String[] months =  {"January", "February", "March", "April", "May", "June"
    	, "July", "August", "September", "October", "November", "December"};
    final static String[] dow = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //Day of the week
    
    public CalendarPanel(JFrame parent){
        super(parent, "Choose a date");
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
    }
    
    protected JPanel createFormPanel () {
    	try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
    	
    	lblYear = new JLabel ("Change year (+/-20):");
        lblYear.setBounds(15, 10, 150, 20);
        
        cmbYear = new JComboBox<String>();
        cmbYear.setBounds(205, 10, 80, 20);
        cmbYear.addActionListener(new cmbYear_Action());
        
        lblMonth = new JLabel ("January");
        lblMonth.setBounds(145-lblMonth.getPreferredSize().width/2, 40, 100, 25);
        lblMonth.setFont(new Font(null, Font.BOLD, 14));
        
        btnPrev = new JButton ("<<");
        btnPrev.setBounds(15, 40, 50, 25);
        btnPrev.setToolTipText("Previous month");
        btnPrev.addActionListener(new btnPrev_Action());
        
        btnNext = new JButton (">>");
        btnNext.setBounds(235, 40, 50, 25);
        btnNext.setToolTipText("Next month");
        btnNext.addActionListener(new btnNext_Action());
        
        calModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex){return false;}
		};
        for (int i=0; i<7; i++){
        	calModel.addColumn(dow[i]);	//Add table header
        }
        calTable = new JTable(calModel);
        calTable.setGridColor(new Color(225, 225, 225));	//Grid color: Light grey
        calTable.setToolTipText("Choose a start date");
        calScrollPane = new JScrollPane(calTable);
        calScrollPane.setBounds(15, 80, 270, 245);
        
        calPanel = new JPanel(null);
        calPanel.setPreferredSize(new Dimension(300,335));
        calPanel.add(lblMonth);
        calPanel.add(lblYear);
        calPanel.add(cmbYear);
        calPanel.add(btnPrev);
        calPanel.add(btnNext);
        calPanel.add(calScrollPane);
        
        GregorianCalendar gc = new GregorianCalendar();
        realDay = gc.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = gc.get(GregorianCalendar.MONTH);
        realYear = gc.get(GregorianCalendar.YEAR);
        curMonth = realMonth;
        curYear = realYear;
        
        calTable.getParent().setBackground(calTable.getBackground());
        calTable.getTableHeader().setResizingAllowed(false);
        calTable.getTableHeader().setReorderingAllowed(false);
        calTable.setColumnSelectionAllowed(true);
        calTable.setRowSelectionAllowed(true);
        //calTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        calTable.setRowHeight(35);
        calModel.setColumnCount(7);
        calModel.setRowCount(6);
        
        for (int i=realYear-20; i<=realYear+20; i++){
            cmbYear.addItem(String.valueOf(i));
        }
        
        calTable.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
            	String origDate = curYear+"-"+(curMonth+1)+"-"+calTable.getValueAt(calTable.getSelectedRow(), 
						  calTable.getSelectedColumn());
            	try {
					Date fd1 = new SimpleDateFormat("yyyy-MM-dd").parse(origDate);
					SimpleDateFormat fd2 = new SimpleDateFormat("yyyy-MM-dd");
					selDate = fd2.format(fd1);
					System.out.println(selDate);
				} catch (ParseException pe) {
					JOptionPane.showMessageDialog(calPanel, "Invalid Date!");
				}
            }
        });
        
        repaint(realYear, realMonth);
        
        return calPanel;
    }

    public static void repaint(int year, int month){
        int nod, som; //Number Of Days, Start Of Month
        
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear-10) {btnPrev.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+20) {btnNext.setEnabled(false);} //Too late
        
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(145-lblMonth.getPreferredSize().width/2, 40, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear calendar
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
            	calModel.setValueAt(null, i, j);
            }
        }
        
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        nod = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	//Number of days
        som = gc.get(GregorianCalendar.DAY_OF_WEEK);	//The first day of month
        
        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            calModel.setValueAt(i, row, column);
        }
        
        //Define a renderer which aligns each date of the calendar to the center
        DefaultTableCellRenderer centerRenderer = new calTableRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i=0; i<calModel.getColumnCount(); i++) {
        	calTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        calTable.setDefaultRenderer(calTable.getColumnClass(0), centerRenderer);
    }
    
    static class calTableRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent (JTable table, Object obj, boolean selected, boolean focused, int row, int col){
            super.getTableCellRendererComponent(table, obj, selected, focused, row, col);
            if (col >= 1 && col <= 5){ //Weekdays: Background = white
            	setBackground(new Color(255, 255, 255));
            }
            else{ //Weekends: Background = light yellow
            	setBackground(new Color(255, 255, 200));
            }
            if (obj != null){
                if (Integer.parseInt(obj.toString()) == realDay && curMonth == realMonth && curYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (curMonth == 0){ //Back to the previous year
                curMonth = 11;
                curYear -= 1;
            }
            else{ //Back to the previous month
                curMonth -= 1;
            }
            repaint(curYear, curMonth);
        }
    }
    
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (curMonth == 11){ //Forward to the next year
                curMonth = 0;
                curYear += 1;
            }
            else{ //Forward to the next month
                curMonth += 1;
            }
            repaint(curYear, curMonth);
        }
    }
    
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                curYear = Integer.parseInt(b);
                repaint(curYear, curMonth);
            }
        }
    }
    
    public String getSelDate() {
    	return selDate;
    }
    
    protected boolean performOkAction(){
    	return true;
    }

}