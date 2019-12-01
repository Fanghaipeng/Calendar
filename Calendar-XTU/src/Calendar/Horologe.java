package Calendar;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * 转盘钟表类
 * 基于JFrame样式
 * @author FangHaiPeng
 * 
 */
public class Horologe extends JFrame{
	
	private static final long serialVersionUID = 3L;/*把Java对象转换为字节序列的过程称为对象的序列化。*/

	public Horologe() {
		//当前框架样式设置
		this.setLayout(null);
		this.setTitle("Horologe");
		this.setBounds(760,150, 400,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(1);
		this.setVisible(true);
		this.setBackground(new Color(60,60,60));
		Container contentPane = this.getContentPane();
		contentPane.setBackground(new Color(60, 60, 60));
		
		//时钟样式设置
		Clock clock = new Clock();
		clock.setBackground(new Color(60, 60, 60));
		clock.setBounds(40, 30, 300, 300);
		clock.showd();
		contentPane.add(clock);

		}
}
