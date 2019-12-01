package Calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import javax.swing.*;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 
 * 显示整个农历面板及部分按键功能
 * 继承JPanel样式
 * 加入Runnable接口实现多线程
 * @author FangHaiPeng
 * 
 */
public class CalendarViewAndClock extends JPanel implements Runnable{
	

	private static final long serialVersionUID = 5L;
    
	//存储样式及存储农历，日期
	private final String [] week = new String[] {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    private int []day = new int[49];//日期数组
    private String[] LunardayOrVacation = new String[49];//农历
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//当前日期格式
    
    //CalendarView类成员
    Date date = new Date();
    String ss = format.format(date);
    String[] array = ss.split("-");
    private int year = Integer.valueOf(array[0]);
    private int month = Integer.valueOf(array[1]);
    private int nowday = Integer.valueOf(array[2]);
    final private int nowmonth = Integer.valueOf(array[1]);
    final private int nowyear = Integer.valueOf(array[0]);
    
    //控制面板控件
    private JPanel NowDayAndControl = new JPanel();//显示当前年月及控制跳转日历
    private JPanel CalendarDay = new JPanel();//日历界面
    private JLabel days[][] = new JLabel[7][7];//显示星期及当前日历
    private JLabel NowDate = new JLabel("",JLabel.LEFT);//显示当前的年月
    private JButton JumpToLastMonth = new JButton("∧");//跳转到上个月
    private JButton JumpToNextMonth = new JButton("∨");//跳转到下个月
	
    private int flag = -1; //记录当前日期在数组中的位置
    
    Thread t = new Thread(this);
    
    //跳转到制定日期控件
    private JFrame JumpToMonth;
    private JPanel settime;
    private JLabel TipsInputYear;
    private JLabel TipsInputMonth;
    private JTextField inputYear;
    private JTextField inputMonth;
    private JButton JumpDo;
    
    //时钟
	Clock clock = new Clock();
	
	//地址
	private JLabel Location ;
	public CalendarViewAndClock() {
		this.setLayout(null);
		//设置上面的面板
		NowDayAndControl.setBounds(10, 8, 340, 35);
		NowDayAndControl.setBackground(new Color(60,60,60));
		NowDayAndControl.setLayout(null);
		this.add(NowDayAndControl);

        //设置下面的面板
        CalendarDay.setBounds(10, 40, 340, 300);
        CalendarDay.setBackground(new Color(60,60,60));
		CalendarDay.setLayout(new GridLayout(7, 7, 1, 1));
		
		this.add(CalendarDay);

        //设置显示当前年月标签
		NowDate.setFont(new Font("宋体", Font.PLAIN, 18));
		NowDate.setBounds(15, 0, 180, 35);
		NowDate.setOpaque(true);
		NowDate.setBackground(new Color(60,60,60));
		NowDate.setForeground(Color.white);
		NowDate.setText(String.valueOf(year)+ "年"+ String.valueOf(month) + "月");
		NowDayAndControl.add(NowDate);

        //设置显示上个月的按钮
		JumpToLastMonth.setBounds(234, 0, 53, 35);
		JumpToLastMonth.setBackground(new Color(60,60,60));
		JumpToLastMonth.setFont(new Font("仿宋", Font.PLAIN, 14));
		JumpToLastMonth.setForeground(Color.white);
		JumpToLastMonth.setFocusable(false);
		JumpToLastMonth.setBorderPainted(false);
		JumpToLastMonth.setVisible(true);
		NowDayAndControl.add(JumpToLastMonth);
		
        //设置显示下个月的按钮
		JumpToNextMonth.setBounds(287, 0, 53,35);
		JumpToNextMonth.setBackground(new Color(60,60,60));
		JumpToNextMonth.setFont(new Font("仿宋", Font.PLAIN, 14));
		JumpToNextMonth.setForeground(Color.white);
		JumpToNextMonth.setFocusable(false);
		JumpToNextMonth.setBorderPainted(false);
		JumpToNextMonth.setVisible(true);
		NowDayAndControl.add(JumpToNextMonth);
		
        //加入地址
        Location = new JLabel();
        Location.setFont(new Font("宋体", Font.PLAIN, 18));
        Location.setBounds(265, 352, 100, 18);
        Location.setOpaque(true);
        Location.setBackground(new Color(60,60,60));
        Location.setForeground(Color.white);
        Location.setText("中国 湘潭");
		this.add(Location);
		
    	clock.setBounds(30,370, 300, 300);
    	clock.showd();
    	clock.setBackground(new Color(60, 60, 60));
    	this.add(clock);
		//设置days中每个展示label的格式
		for(int i = 0;i < 7;i++)
		{
			for(int j = 0;j < 7; j++)
			{
				days[i][j] = new JLabel("",JLabel.CENTER);
				days[i][j].setSize(53,53);
				days[i][j].setFont(new Font("Times New Roman",Font.PLAIN,14));
				days[i][j].setBackground(new Color(60,60,60));
				days[i][j].setForeground(Color.WHITE);
				days[i][j].setOpaque(true);
				CalendarDay.add(days[i][j]);
				days[i][j].setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
				days[i][j].addMouseListener(new lable_listen(days[i][j]));
			}
		}
		for(int i = 1;i < 7;i++)
		{
			for(int j = 0;j < 7; j++)
			{
				days[i][j].setFont(new Font("仿宋",Font.PLAIN,14));
			}
		}
		
		//设置星期名
		for(int j = 0;j < 7; j++)
		{
			days[0][j].setText(week[j]);
		}
		
		//设置每个label中的日期及农历或节假日
        SetLunarOrVacation();
        
        t.start();//线程开始
        
	}
	public void run(){
		NowDate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    year = nowyear;
                    month = nowmonth;
                    NowDate.setText(String.valueOf(year)+ "年"+ String.valueOf(month) + "月");
                    clearFormat();
                    SetLunarOrVacation();
                }
                else{
                	GetJumpTime();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	NowDate.setForeground(new Color(198,250,194));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	NowDate.setForeground(Color.white);
            }
        });
		JumpToLastMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try{
            		if(month == 1) {
                        month = 13;
                        year--;
                    }
                    month--;
                    NowDate.setText(String.valueOf(year)+ "年"+ String.valueOf(month) + "月");
                    clearFormat();
                    SetLunarOrVacation();
                }catch (Exception es){
                        System.out.println("获取时间错误");
                }
            }
        });
        //下个月
		JumpToNextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try{
                	if(month == 12) {
                        month = 0;
                        year++;
                    }
                    month++;
                    NowDate.setText(String.valueOf(year)+ "年"+ String.valueOf(month) + "月");
                    clearFormat();
                    SetLunarOrVacation();
                }catch (Exception es){
                        System.out.println("获取时间错误");
                }
            }
        });
	}
    //设置日期
    public void SetLunarOrVacation(){
        GregorianCalendar CalendarNow = new GregorianCalendar(year,month-1,1);//本月
        GregorianCalendar CalendarLast = new GregorianCalendar(year,month-2,1);//上个月
        
        int startday = CalendarNow.get(Calendar.DAY_OF_WEEK) - 1;//本月开始的星期
        int TotalDaysOfThisMonth = CalendarNow.getActualMaximum(Calendar.DAY_OF_MONTH);//本月的天数
        int TotalDaysOfLatsMonth = CalendarLast.getActualMaximum(Calendar.DAY_OF_MONTH);//上个月的天数

        //设置上月日期
        for(int i = startday - 1;i >= 0;i--) {
            day[i] = TotalDaysOfLatsMonth;
            if(month == 1) LunardayOrVacation[i] = Lunar.GetLunarOfOneDay(year-1,12,TotalDaysOfLatsMonth--);
            else
                LunardayOrVacation[i] = Lunar.GetLunarOfOneDay(year,month-1,TotalDaysOfLatsMonth--);
        }
        
        //初始化本月的日历
        for(int i = 1;i <= TotalDaysOfThisMonth;i++){
            day[startday+i-1] = i;
            LunardayOrVacation[startday+i-1] = Lunar.GetLunarOfOneDay(year,month,i);
            if(i == nowday)
                flag = startday+i-1;
        }
        

        int c = 1;
        for(int i = TotalDaysOfThisMonth + startday-1;i < 48;i++) {
            day[i + 1] = c;
            if(month == 12) LunardayOrVacation[i+1] = Lunar.GetLunarOfOneDay(year+1,1,c++);
            else LunardayOrVacation[i+1] = Lunar.GetLunarOfOneDay(year,month+1,c++);
        }
        
        int Location_Now = startday - 1;
        int Location_Temp = 0;
        
        //将今天的样式加背景颜色
        SetToday();
        
        //对全部节假日设置对应的颜色
        for (int i = 1,k = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
            	char ChinaWord[] = LunardayOrVacation[k].toCharArray();
            	if(ChinaWord[0]!='初' && ChinaWord[0]!='十' && ChinaWord[0]!='廿' && ChinaWord[0]!='卅' && ChinaWord[1]!='月') {
            		days[i][j].setForeground(new Color(106,250,192));
            	}
            	k++;
            }
        
        //将非本月部分调成灰色
        for(int i = 1;i < 7;i++)
            for(int j = 0;j < 7;j++){
                if(Location_Now >= 0) {
                    Location_Now--;
                    days[i][j].setForeground(new Color(91,91,91));
                }
                else if(Location_Temp < TotalDaysOfThisMonth){
                    Location_Temp++;
                }
                else {
                	days[i][j].setForeground(new Color(91,91,91));
                }
            }

    }
    
    //清除全部格式，用于修改年份月份时使用
    public void clearFormat(){
        for(int i = 1;i < 7;i++)
            for(int j = 0;j < 7;j++){
				days[i][j].setBackground(new Color(60,60,60));
				days[i][j].setForeground(Color.white);
				days[i][j].setText(String.valueOf(day[(i-1)*7+j]));
				days[i][j].setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
            }
    }
    
    /**
     * 
     * 建立JumpTimeJrame组件
     * 
     */
    private void JumpTimeJrame(){
    	//定义及初始化
        JumpToMonth = new JFrame("Jump to time");
        settime = new JPanel();
        TipsInputYear = new JLabel();
        TipsInputMonth = new JLabel();
        inputYear = new JTextField();
        inputMonth = new JTextField();
        JumpDo = new JButton("确认");
        

        //设置框的位置
        JumpToMonth.setLayout(null);
        JumpToMonth.setBounds(830,230,350,90);
        JumpToMonth.setBackground(Color.LIGHT_GRAY);
        JumpToMonth.setResizable(false);
        JumpToMonth.setVisible(true);

        //设置组件设置时间位置及样式
        settime.setBounds(5,5,326,40);
        settime.setBackground(Color.white);
        settime.setLayout(null);
        JumpToMonth.add(settime);

        //设置组件输入年份提示框位置及样式
        TipsInputYear.setText("输入年份:");
        TipsInputYear.setFont(new Font("思源宋体", Font.PLAIN, 13));
        TipsInputYear.setBounds(5,5,60,30);
        settime.add(TipsInputYear);

        //设置组件输入月份提示框位置及样式
        TipsInputMonth.setBounds(110,5,60,30);
        TipsInputMonth.setText("输入月份:");
        TipsInputMonth.setFont(new Font("思源宋体", Font.PLAIN, 13));
        settime.add(TipsInputMonth);
        
        //设置组件输入年份位置及样式
        inputYear.setBounds(65,5,40,30);
        settime.add(inputYear);

        //设置组件输入月份位置及样式
        inputMonth.setBounds(170,5,40,30);
        settime.add(inputMonth);

        //设置跳转按钮位置及样式
        JumpDo.setBounds(250,5,65,30);
        JumpDo.setBackground(new Color(159, 222, 223));
        JumpDo.setFont(new Font("思源宋体", Font.PLAIN, 14));
        JumpDo.setForeground(Color.BLACK);
        JumpDo.setFocusable(false);
        JumpDo.setBorderPainted(false);
        settime.add(JumpDo);

    }

    private void GetJumpTime(){
        JumpTimeJrame();

        JumpDo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String Input_Year = inputYear.getText();
                String Input_Month = inputMonth.getText();
                int To_Year = Integer.valueOf(Input_Year);
                int To_Month = Integer.valueOf(Input_Month);
                if(1 < To_Year && To_Year < 2099 && 1 <= To_Month && To_Month <= 12){
                    year = To_Year;
                    month = To_Month;
                }
                NowDate.setText(String.valueOf(year)+ "年"+ String.valueOf(month) + "月");
                clearFormat();
                try{
                    SetLunarOrVacation();
                }catch (Exception es){
                        System.out.println("获取时间错误");
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                JumpDo.setForeground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JumpDo.setForeground(Color.black);
            }

        });

    }
    
    //设置本日背景颜色
    public void SetToday() {
    int ArrayLocation = 0;
    for (int i = 1; i < 7; i++)
        for (int j = 0; j < 7; j++) {
            if(ArrayLocation == flag && month == nowmonth && nowyear == year) {
            	days[i][j].setBackground(Color.BLUE);
            	}
            days[i][j].setText("<html><body><p align = \"center\">"+String.valueOf(day[ArrayLocation]) +
            		"<br>"+ LunardayOrVacation[ArrayLocation++] +"</p></body></html>");
        }
    }
    
    //label统一监听事件
    public class lable_listen implements MouseListener{
    	JLabel label1;
		Color color;
    	public lable_listen(JLabel label) {
    		// TODO Auto-generated constructor stub
    		this.label1 = label;
			color = label.getBackground();
    	}
    	@Override
    	public void mouseEntered(MouseEvent arg0) {
    		// TODO Auto-generated method stub
			label1.setBorder(BorderFactory.createLineBorder(Color.blue));
    	}

    	@Override
    	public void mouseExited(MouseEvent arg0) {
    		// TODO Auto-generated method stub
    		label1.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
			label1.setBackground(color);
			SetToday();
    	}

    	@Override
    	public void mousePressed(MouseEvent arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mouseClicked(MouseEvent arg0) {
    		// TODO Auto-generated method stub
    	}

    	@Override
    	public void mouseReleased(MouseEvent arg0) {
    		// TODO Auto-generated method stub
    		
    	}
    }
}
