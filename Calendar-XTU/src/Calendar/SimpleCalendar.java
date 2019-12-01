package Calendar;
/**
 * 
 * Description:
 * �ó���������ʾ����������ʱ�ӣ�ת��ʱ�ӣ�ũ�����ڼ��յ�
 * @author FangHaiPeng
 * @version 3.0�ȶ���
 * 
 */
import java.awt.*;
import javax.swing.*;
 
/**
 * 
 *  �������ڹ�����������������ʵ��ҳ�沼�ֺ�Ƕ�������ؼ�����
 *  ������ܲ��ֲ���JFrame�ڼ���JPanel����ʽ
 *  ��ܲ����޲�����ʽ��ȫ���������겼��
 *  ����JFrame��ʽ
 *  
 */
public class SimpleCalendar extends JFrame{
	
	private static final long serialVersionUID = 1L;/*��Java����ת��Ϊ�ֽ����еĹ��̳�Ϊ��������л���*/
	
	/*���г�Ա*/
	Container Global_Container ;
	TimeShower TimeShower ;
	CalendarViewAndClock CalendarViewAndClock ;
	/**
	 *  
	 *  SimpleCalendar�������췽��
	 *  
	 */
	public SimpleCalendar() {
		
		/*�����������ʽ*/
		this.setLayout(null);
		
		/*���ø���������������������*/
		Global_Container = this.getContentPane();
		Global_Container.setBackground(Color.GRAY); 
		
		/*��������ϲ㲿����ʽ������ʾ����ʱ�Ӻ�����*/
		TimeShower = new TimeShower(); 
		TimeShower.setBackground(new Color(60,60,60)); 
		TimeShower.setBounds(0, 0, 375, 114);
		TimeShower.setLayout(null);
		Global_Container.add(TimeShower); 
		
		/*��������²㲿����ʽ������ʾ��������*/
		CalendarViewAndClock = new CalendarViewAndClock();
		CalendarViewAndClock.setBackground(new Color(60,60,60)); 
		CalendarViewAndClock.setBounds(0, 115, 375, 660);
		Global_Container.add(CalendarViewAndClock);
		
	}
	
	/**
	 *  
	 *  ����������main����
	 *  
	 */
	public static void main(String[] args){

		SimpleCalendar simpleCalendar = new SimpleCalendar();
		
		/*�������������ʽ��д�ڴ˴���ʹ�������и���*/
		simpleCalendar.setTitle("SimpleCalendar");
		simpleCalendar.setBounds(1170, 55, 375, 775);
		simpleCalendar.setResizable(false);/*���ɵ�����С*/
		simpleCalendar.setDefaultCloseOperation(3);
		simpleCalendar.setVisible(true);
	}	
}
