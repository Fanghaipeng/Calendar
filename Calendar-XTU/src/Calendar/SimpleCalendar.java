package Calendar;
/**
 * 
 * Description:
 * 该程序用于显示日历及电子时钟，转盘时钟，农历，节假日等
 * @author FangHaiPeng
 * @version 3.0稳定版
 * 
 */
import java.awt.*;
import javax.swing.*;
 
/**
 * 
 *  该类用于构建日历的主界面以实现页面布局和嵌入其他控件工具
 *  整个框架布局采用JFrame内加入JPanel的样式
 *  框架采用无布局样式，全部采用坐标布局
 *  基于JFrame样式
 *  
 */
public class SimpleCalendar extends JFrame{
	
	private static final long serialVersionUID = 1L;/*把Java对象转换为字节序列的过程称为对象的序列化。*/
	
	/*类中成员*/
	Container Global_Container ;
	TimeShower TimeShower ;
	CalendarViewAndClock CalendarViewAndClock ;
	/**
	 *  
	 *  SimpleCalendar类主构造方法
	 *  
	 */
	public SimpleCalendar() {
		
		/*设置主面板样式*/
		this.setLayout(null);
		
		/*设置覆盖容器，覆盖整个界面*/
		Global_Container = this.getContentPane();
		Global_Container.setBackground(Color.GRAY); 
		
		/*设置面板上层部分样式——显示电子时钟和日期*/
		TimeShower = new TimeShower(); 
		TimeShower.setBackground(new Color(60,60,60)); 
		TimeShower.setBounds(0, 0, 375, 114);
		TimeShower.setLayout(null);
		Global_Container.add(TimeShower); 
		
		/*设置面板下层部分样式——显示日历界面*/
		CalendarViewAndClock = new CalendarViewAndClock();
		CalendarViewAndClock.setBackground(new Color(60,60,60)); 
		CalendarViewAndClock.setBounds(0, 115, 375, 660);
		Global_Container.add(CalendarViewAndClock);
		
	}
	
	/**
	 *  
	 *  构造主界面main函数
	 *  
	 */
	public static void main(String[] args){

		SimpleCalendar simpleCalendar = new SimpleCalendar();
		
		/*设置主界面的样式，写在此处能使程序运行更快*/
		simpleCalendar.setTitle("SimpleCalendar");
		simpleCalendar.setBounds(1170, 55, 375, 775);
		simpleCalendar.setResizable(false);/*不可调整大小*/
		simpleCalendar.setDefaultCloseOperation(3);
		simpleCalendar.setVisible(true);
	}	
}
