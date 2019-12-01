package Calendar;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.naming.LimitExceededException;
import javax.swing.*;
import javax.swing.Timer;

/**
 * 
 * 电子钟表及当前日期展示界面
 * 基于JPanel样式
 * 利用ActionListener接口
 * 在本代码中尝试使用了MouseListener()和MouseAdapter()两种鼠标监听方式
 * MouseListener()不能省略未用的监听事件
 * MouseAdapter()可以省略未用的监听事件
 * 
 * @author FangHaiPeng
 */
public class TimeShower extends JPanel implements ActionListener{
	
	private static final int Limit = 1;
	private static final long serialVersionUID = 2L;
	long startTime ;
	long endTime;
	//定义格式化方式
	SimpleDateFormat Time_Format = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat Day_Format = new SimpleDateFormat("YYYY年MM月dd日");
	
	//定义电子钟和当前时间Label
	JLabel Electronic_Clock = new JLabel("",JLabel.LEFT);
	JLabel Current_Date = new JLabel("",JLabel.LEFT);
	
	//定义更新时间构件
    private JFrame Network_Time_Service;
    private JPanel Network_Select;
    private JComboBox<String> Network_Select_JCombobox;
    private JButton Update_Do;
    private JLabel NetWork_Select_Now;//暂时被选中的框
    
    private String AllWebUrl[] = {
	    		"Local Time",//本地时间
	    		"http://www.baidu.com",//百度
	            "http://www.taobao.com",//淘宝
	            "http://www.ntsc.ac.cn",//中国科学院国家授时中心
	            "http://www.360.cn",//360
	            "http://www.beijing-time.org"
            };//北京时间
    private String SelectedUrl;//确认的同步地址
    

    /*
     * 用毫秒级time代表当前时间计时器
     * 用于避免后续网络授予时间时重复获取网络时间
     * 而是运行计时器方法实现时间计时
     * +200ms因为
     * 		：经测试，程序运行时间会使显示的时间与真正时间产生相差200ms
     */
    long time = System.currentTimeMillis()+250;
    
    /**
     * 
     * TimeShower构造方法
     * 通过Timer定时器实现刷新功能
     * 
     */
	public TimeShower() {
		this.setLayout(null);
		
		/*
		 * 第一次先将时间设置好，避免出现程序运行时上层电子时钟和日期延时出现
		 */
		String  Time_Gained = Time_Format.format(time);
		Electronic_Clock.setText(" " + Time_Gained);
		Current_Date.setText("   "+Day_Format.format(new Date()) + " " + Lunar.GetCompleteDate());
		
		/*设置电子时钟展示样板的位置及样式*/
		Electronic_Clock.setBounds(0, 17, 300, 50);
		Electronic_Clock.setFont(new Font("仿宋",Font.PLAIN,45));
		Electronic_Clock.setForeground(Color.WHITE);
		
		/*
		 * 捕捉：
		 * Entered：颜色变换
		 * Exited：颜色变换
		 * Clicked：设置网络授时
		 */
		Electronic_Clock.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Electronic_Clock.setForeground(Color.WHITE);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Electronic_Clock.setForeground(Color.GRAY);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(Limit == 1) {
					SetJCombobox();
				}
				else;
			}
		});
		this.add(Electronic_Clock);
		
		/*设置当前时间展示样板的位置及样式*/
		Current_Date.setBounds(0, 60, 375, 50);
		Current_Date.setFont(new Font("宋体",Font.PLAIN,16));      	
		Current_Date.setForeground(new Color(60,156,228));
		/*
		 * 捕捉：
		 * Entered：颜色变换
		 * Exited：颜色变换
		 * Clicked：展示本日的转表时钟
		 */
		Current_Date.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Current_Date.setForeground(new Color(60,156,228));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Current_Date.setForeground(Color.GRAY);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Horologe Horologe_Clock = new Horologe();
				Horologe_Clock.setBackground(new Color(60,60,60));
			}
		});
		this.add(Current_Date);
		
		/*
		 * 利用Timer定时器实现同线程相似的创新功能
		 */
		Timer timer = new Timer(1000,this);
		timer.start(); 
		
	}
	
	/**
	   * 时间计时和时间刷新
	   *
	   * @param time
	   * @return
	   */
	public void actionPerformed(ActionEvent e)
	{
		/*实现时间计时*/
		time +=1000;
		
		/*实现时间刷新*/
		String Time_Gained = Time_Format.format(time);
		Electronic_Clock.setText(" "+Time_Gained);
		Current_Date.setText("   "+Day_Format.format(new Date()) + " " + Lunar.GetCompleteDate());
		
	}
	
	/**
	 * 
	 * 构造Network_Time_Service
	 *
	 */
	public void SetNetworkUrl(){

		//设置网络授时JFrame组件位置及样式
        Network_Time_Service = new JFrame("Network_Time_Service");
        Network_Time_Service.setBounds(860, 80, 300, 240);
        Network_Time_Service.getContentPane().setBackground(Color.WHITE);
        Network_Time_Service.setLayout(null);
        Network_Time_Service.setResizable(false);
        Network_Time_Service.setVisible(true);
        
        //设置网络授时JPanel构件位置及样式
        Network_Select = new JPanel();
        Network_Select.setBounds(5,5,290,230);
        Network_Select.setBackground(Color.white);
        Network_Select.setLayout(null);
        Network_Select.setVisible(true);
        
        //设置网络授时选择下拉框构件位置及样式
        Network_Select_JCombobox = new JComboBox<String>(AllWebUrl);
        Network_Select_JCombobox.setFont(new Font("仿宋",Font.PLAIN,14));
        Network_Select_JCombobox.setVisible(true);
        Network_Select_JCombobox.setBounds(5,5,270,25);
        Network_Select_JCombobox.setBackground(Color.WHITE);
        Network_Select.add(Network_Select_JCombobox);

        //设置网络授时更新按钮构件位置及样式
        Update_Do = new JButton("立即更新");
        Update_Do.setBounds(85,160,100,30);
        Update_Do.setFont(new Font("仿宋",Font.PLAIN,15));
        Update_Do.setBackground(new Color(159, 222, 223));
        Update_Do.setForeground(Color.BLACK);
        Update_Do.setFocusable(false);
        Update_Do.setBorderPainted(false);
        Network_Select.add(Update_Do);
        
        //设置网络授时更新效果提示构件位置及样式
        NetWork_Select_Now = new JLabel();
        Network_Time_Service.getContentPane().add(Network_Select);
        NetWork_Select_Now.setFont(new Font("楷体", Font.PLAIN, 20));
        NetWork_Select_Now.setBounds(5, 45, 280, 50);
        NetWork_Select_Now.setOpaque(true);
        NetWork_Select_Now.setBackground(Color.WHITE);
        NetWork_Select_Now.setForeground(Color.black);
        Network_Select.add(NetWork_Select_Now);
        
    }
	
	/**
	   * 
	   *JCombobox设置
	   *
	   */
    private void SetJCombobox(){
    	SetNetworkUrl();
		/*
		 * 
		 * 捕捉：
		 * mouseClicked：弹出下拉框
		 * itemStateChanged：修改目标项
		 * 
		 */
        Network_Select_JCombobox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    SelectedUrl=(String) Network_Select_JCombobox.getSelectedItem();
                }

            }
        });
        Update_Do.addMouseListener(new MouseAdapter(){
        	@Override
            public void mouseClicked(MouseEvent e){
            	try {
            		
            		time = getWebtime(SelectedUrl).getTime();
            		endTime = System.currentTimeMillis();
            		time = time + (endTime - startTime);
                    NetWork_Select_Now.setText("<html><body><p align = \"center\">"+"与"+SelectedUrl +"<br>"+"同步成功！" +"</p></body></html>");
                } catch (Exception es) {
                	NetWork_Select_Now.setText("错误的连接或网络错误");
                }
            }
        });
    }
    
	/**
	   * 时间计时和时间刷新
	   *
	   * @param WebUrlSelected
	   * @return date
	   */
    public Date getWebtime(String WebUrlSelected) {
        try {
            if(WebUrlSelected.equals("Local Time")){
                Date nowTime = new Date();
                return nowTime;
            }
            else{
            	if(WebUrlSelected!=null) {
            		URL url = new URL(WebUrlSelected);
                    URLConnection uc = url.openConnection();
                    uc.connect();
                    startTime = System.currentTimeMillis();
                    long time = uc.getDate() ;// 读取网站日期时间
                    Date date = new Date(time);// 转换为标准时间对象
                    return date;
            	}
            }
        } catch (MalformedURLException e) {
        	NetWork_Select_Now.setText("错误的连接或网络错误");
        } catch (IOException e) {
        	NetWork_Select_Now.setText("错误的连接或网络错误");
        } 
        
        return null;
    }
}
