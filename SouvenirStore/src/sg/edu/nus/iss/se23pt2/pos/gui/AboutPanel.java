package sg.edu.nus.iss.se23pt2.pos.gui;


import javax.swing.JLabel;
import javax.swing.JPanel;


public class AboutPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel yiming;
	private JPanel list;
	private JLabel jing;
	private JLabel sabethan;
	private JLabel nikhil;
	private JLabel jv;
	private JLabel godwin;
	private JPanel nameList;
	private JLabel debasish;
	private JLabel desc;
	private JLabel rushabh;
	
	public AboutPanel(){
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			{
				desc = new JLabel();
				this.add(desc, "North");
				desc.setText("University Souvenir Store Application Created By SE23 PT2 Team :");
				desc.setBounds(12, 8, 500, 19);
			}
			{
				nameList = new JPanel();
				this.add(nameList);
				nameList.setBounds(7, 42, 178, 272);
				nameList.setLayout(null);
				{
					rushabh = new JLabel();
					nameList.add(rushabh);
					rushabh.setText("Rushabh Shah ");
					rushabh.setBounds(2, 10, 113, 26);
				}
				{
					sabethan = new JLabel();
					nameList.add(sabethan);
					sabethan.setText("Sabethan Vivekananthan");
					sabethan.setBounds(1, 96, 162, 21);
				}
				{
					jing = new JLabel();
					nameList.add(jing);
					jing.setText("Jing Dong");
					jing.setBounds(2, 68, 67, 21);
				}
				{
					jv = new JLabel();
					nameList.add(jv);
					jv.setText("Jaya Vignesh");
					jv.setBounds(2, 40, 84, 21);
				}
			}
			{
				list = new JPanel();
				this.add(list);
				list.setBounds(229, 43, 159, 229);
				list.setLayout(null);
				{
					nikhil = new JLabel();
					list.add(nikhil);
					nikhil.setText("Nikhil Metrani");
					nikhil.setBounds(11, 13, 93, 21);
				}
				{
					yiming = new JLabel();
					list.add(yiming);
					yiming.setText("Yiming Niu");
					yiming.setBounds(11, 68, 99, 21);
				}
				{
					debasish = new JLabel();
					list.add(debasish);
					debasish.setText("Debasish Routaray");
					debasish.setBounds(11, 40, 123, 21);
				}
				{
					godwin = new JLabel();
					list.add(godwin);
					godwin.setText("Godwin Wong");
					godwin.setBounds(10, 95, 95, 21);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
