package Calendar;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * ת���ӱ���
 * ����JFrame��ʽ
 * @author FangHaiPeng
 * 
 */
public class Horologe extends JFrame{
	
	private static final long serialVersionUID = 3L;/*��Java����ת��Ϊ�ֽ����еĹ��̳�Ϊ��������л���*/

	public Horologe() {
		//��ǰ�����ʽ����
		this.setLayout(null);
		this.setTitle("Horologe");
		this.setBounds(760,150, 400,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(1);
		this.setVisible(true);
		this.setBackground(new Color(60,60,60));
		Container contentPane = this.getContentPane();
		contentPane.setBackground(new Color(60, 60, 60));
		
		//ʱ����ʽ����
		Clock clock = new Clock();
		clock.setBackground(new Color(60, 60, 60));
		clock.setBounds(40, 30, 300, 300);
		clock.showd();
		contentPane.add(clock);

		}
}
