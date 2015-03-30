package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.Discount;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.io.IOException;
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
    
    static int realYear, realMonth, realDay, curYear, curMonth;
    final static String[] months =  {"January", "February", "March", "April", "May", "June"
    	, "July", "August", "September", "October", "November", "December"};
    final static String[] dow = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //Day of the week
    
    private Discount disc;
    
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
    	
        lblMonth = new JLabel ("January");
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
        
        lblYear = new JLabel ("Change year:");
        lblYear.setBounds(10, 305, 80, 20);
        
        cmbYear = new JComboBox<String>();
        cmbYear.setBounds(230, 305, 80, 20);
        cmbYear.addActionListener(new cmbYear_Action());
        
        btnPrev = new JButton ("<<");
        btnPrev.setBounds(10, 25, 50, 25);
        btnPrev.addActionListener(new btnPrev_Action());
        
        btnNext = new JButton (">>");
        btnNext.setBounds(260, 25, 50, 25);
        btnNext.addActionListener(new btnNext_Action());
        
        calModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex){return false;}
		};
        for (int i=0; i<7; i++){
        	calModel.addColumn(dow[i]);
        }
        calTable = new JTable(calModel);
        calScrollPane = new JScrollPane(calTable);
        calScrollPane.setBounds(10, 50, 300, 250);
        
        calPanel = new JPanel(null);
        //calPanel.setBounds(0, 0, 320, 335);
        calPanel.setSize(330, 375);
        calPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
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
        calTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        calTable.setRowHeight(38);
        calModel.setColumnCount(7);
        calModel.setRowCount(6);
        
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }
        
        repaint(realMonth, realYear);
        
        ListSelectionModel cellSelectionModel = calTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
              String selCell = null;

              selCell = curYear+""+curMonth+""+ calTable.getValueAt(calTable.getSelectedRow(), 
            		  calTable.getSelectedColumn());

              System.out.println("Selected: " + selCell);
            }
          });

        return calPanel;
    }

    public static void repaint(int month, int year){
        int nod, som; //Number Of Days, Start Of Month
        
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear-10) {btnPrev.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100) {btnNext.setEnabled(false);} //Too late
        
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear calendar
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
            	calModel.setValueAt(null, i, j);
            }
        }
        
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        nod = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	//Number of days
        som = gc.get(GregorianCalendar.DAY_OF_WEEK);	//The first day of week
        
        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            calModel.setValueAt(i, row, column);
        }
        calTable.setDefaultRenderer(calTable.getColumnClass(0), new calTableRenderer());
    }
    
    static class calTableRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int col){
            super.getTableCellRendererComponent(table, value, selected, focused, row, col);
            if (col == 0 || col == 6){ //Weekend
                setBackground(new Color(255, 220, 220));
            }
            else{ //Weekdays
                setBackground(new Color(255, 255, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && curMonth == realMonth && curYear == realYear){ //Today
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
            repaint(curMonth, curYear);
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
            repaint(curMonth, curYear);
        }
    }
    
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                curYear = Integer.parseInt(b);
                repaint(curMonth, curYear);
            }
        }
    }
    
    protected boolean performOkAction(){
        this.disc.getStartDate();
        
        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
        try {
        	dsFactory.getDiscountDS().update(this.disc);
        	return true;
        }
        catch (UpdateFailedException ufe) {
        	JOptionPane.showMessageDialog(null,
                    "Error :: " + ufe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        	this.disc = null;
        	return false;
        }
        catch (IOException ioe) {
        	JOptionPane.showMessageDialog(null,
                    "Error :: " + ioe.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        	this.disc = null;
        	return false;
        }
    }

}