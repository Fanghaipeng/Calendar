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
 * ��ʾ����ũ����弰���ְ�������
 * �̳�JPanel��ʽ
 * ����Runnable�ӿ�ʵ�ֶ��߳�
 * @author FangHaiPeng
 * 
 */
public class CalendarViewAndClock extends JPanel implements Runnable{
	

	private static final long serialVersionUID = 5L;
    
	//�洢��ʽ���洢ũ��������
	private final String [] week = new String[] {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    private int []day = new int[49];//��������
    private String[] LunardayOrVacation = new String[49];//ũ��
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//��ǰ���ڸ�ʽ
    
    //CalendarView���Ա
    Date date = new Date();
    String ss = format.format(date);
    String[] array = ss.split("-");
    private int year = Integer.valueOf(array[0]);
    private int month = Integer.valueOf(array[1]);
    private int nowday = Integer.valueOf(array[2]);
    final private int nowmonth = Integer.valueOf(array[1]);
    final private int nowyear = Integer.valueOf(array[0]);
    
    //�������ؼ�
    private JPanel NowDayAndControl = new JPanel();//��ʾ��ǰ���¼�������ת����
    private JPanel CalendarDay = new JPanel();//��������
    private JLabel days[][] = new JLabel[7][7];//��ʾ���ڼ���ǰ����
    private JLabel NowDate = new JLabel("",JLabel.LEFT);//��ʾ��ǰ������
    private JButton JumpToLastMonth = new JButton("��");//��ת���ϸ���
    private JButton JumpToNextMonth = new JButton("��");//��ת���¸���
	
    private int flag = -1; //��¼��ǰ�����������е�λ��
    
    Thread t = new Thread(this);
    
    //��ת���ƶ����ڿؼ�
    private JFrame JumpToMonth;
    private JPanel settime;
    private JLabel TipsInputYear;
    private JLabel TipsInputMonth;
    private JTextField inputYear;
    private JTextField inputMonth;
    private JButton JumpDo;
    
    //ʱ��
	Clock clock = new Clock();
	
	//��ַ
	private JLabel Location ;
	public CalendarViewAndClock() {
		this.setLayout(null);
		//������������
		NowDayAndControl.setBounds(10, 8, 340, 35);
		NowDayAndControl.setBackground(new Color(60,60,60));
		NowDayAndControl.setLayout(null);
		this.add(NowDayAndControl);

        //������������
        CalendarDay.setBounds(10, 40, 340, 300);
        CalendarDay.setBackground(new Color(60,60,60));
		CalendarDay.setLayout(new GridLayout(7, 7, 1, 1));
		
		this.add(CalendarDay);

        //������ʾ��ǰ���±�ǩ
		NowDate.setFont(new Font("����", Font.PLAIN, 18));
		NowDate.setBounds(15, 0, 180, 35);
		NowDate.setOpaque(true);
		NowDate.setBackground(new Color(60,60,60));
		NowDate.setForeground(Color.white);
		NowDate.setText(String.valueOf(year)+ "��"+ String.valueOf(month) + "��");
		NowDayAndControl.add(NowDate);

        //������ʾ�ϸ��µİ�ť
		JumpToLastMonth.setBounds(234, 0, 53, 35);
		JumpToLastMonth.setBackground(new Color(60,60,60));
		JumpToLastMonth.setFont(new Font("����", Font.PLAIN, 14));
		JumpToLastMonth.setForeground(Color.white);
		JumpToLastMonth.setFocusable(false);
		JumpToLastMonth.setBorderPainted(false);
		JumpToLastMonth.setVisible(true);
		NowDayAndControl.add(JumpToLastMonth);
		
        //������ʾ�¸��µİ�ť
		JumpToNextMonth.setBounds(287, 0, 53,35);
		JumpToNextMonth.setBackground(new Color(60,60,60));
		JumpToNextMonth.setFont(new Font("����", Font.PLAIN, 14));
		JumpToNextMonth.setForeground(Color.white);
		JumpToNextMonth.setFocusable(false);
		JumpToNextMonth.setBorderPainted(false);
		JumpToNextMonth.setVisible(true);
		NowDayAndControl.add(JumpToNextMonth);
		
        //�����ַ
        Location = new JLabel();
        Location.setFont(new Font("����", Font.PLAIN, 18));
        Location.setBounds(265, 352, 100, 18);
        Location.setOpaque(true);
        Location.setBackground(new Color(60,60,60));
        Location.setForeground(Color.white);
        Location.setText("�й� ��̶");
		this.add(Location);
		
    	clock.setBounds(30,370, 300, 300);
    	clock.showd();
    	clock.setBackground(new Color(60, 60, 60));
    	this.add(clock);
		//����days��ÿ��չʾlabel�ĸ�ʽ
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
				days[i][j].setFont(new Font("����",Font.PLAIN,14));
			}
		}
		
		//����������
		for(int j = 0;j < 7; j++)
		{
			days[0][j].setText(week[j]);
		}
		
		//����ÿ��label�е����ڼ�ũ����ڼ���
        SetLunarOrVacation();
        
        t.start();//�߳̿�ʼ
        
	}
	public void run(){
		NowDate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    year = nowyear;
                    month = nowmonth;
                    NowDate.setText(String.valueOf(year)+ "��"+ String.valueOf(month) + "��");
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
                    NowDate.setText(String.valueOf(year)+ "��"+ String.valueOf(month) + "��");
                    clearFormat();
                    SetLunarOrVacation();
                }catch (Exception es){
                        System.out.println("��ȡʱ�����");
                }
            }
        });
        //�¸���
		JumpToNextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try{
                	if(month == 12) {
                        month = 0;
                        year++;
                    }
                    month++;
                    NowDate.setText(String.valueOf(year)+ "��"+ String.valueOf(month) + "��");
                    clearFormat();
                    SetLunarOrVacation();
                }catch (Exception es){
                        System.out.println("��ȡʱ�����");
                }
            }
        });
	}
    //��������
    public void SetLunarOrVacation(){
        GregorianCalendar CalendarNow = new GregorianCalendar(year,month-1,1);//����
        GregorianCalendar CalendarLast = new GregorianCalendar(year,month-2,1);//�ϸ���
        
        int startday = CalendarNow.get(Calendar.DAY_OF_WEEK) - 1;//���¿�ʼ������
        int TotalDaysOfThisMonth = CalendarNow.getActualMaximum(Calendar.DAY_OF_MONTH);//���µ�����
        int TotalDaysOfLatsMonth = CalendarLast.getActualMaximum(Calendar.DAY_OF_MONTH);//�ϸ��µ�����

        //������������
        for(int i = startday - 1;i >= 0;i--) {
            day[i] = TotalDaysOfLatsMonth;
            if(month == 1) LunardayOrVacation[i] = Lunar.GetLunarOfOneDay(year-1,12,TotalDaysOfLatsMonth--);
            else
                LunardayOrVacation[i] = Lunar.GetLunarOfOneDay(year,month-1,TotalDaysOfLatsMonth--);
        }
        
        //��ʼ�����µ�����
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
        
        //���������ʽ�ӱ�����ɫ
        SetToday();
        
        //��ȫ���ڼ������ö�Ӧ����ɫ
        for (int i = 1,k = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
            	char ChinaWord[] = LunardayOrVacation[k].toCharArray();
            	if(ChinaWord[0]!='��' && ChinaWord[0]!='ʮ' && ChinaWord[0]!='إ' && ChinaWord[0]!='ئ' && ChinaWord[1]!='��') {
            		days[i][j].setForeground(new Color(106,250,192));
            	}
            	k++;
            }
        
        //���Ǳ��²��ֵ��ɻ�ɫ
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
    
    //���ȫ����ʽ�������޸�����·�ʱʹ��
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
     * ����JumpTimeJrame���
     * 
     */
    private void JumpTimeJrame(){
    	//���弰��ʼ��
        JumpToMonth = new JFrame("Jump to time");
        settime = new JPanel();
        TipsInputYear = new JLabel();
        TipsInputMonth = new JLabel();
        inputYear = new JTextField();
        inputMonth = new JTextField();
        JumpDo = new JButton("ȷ��");
        

        //���ÿ��λ��
        JumpToMonth.setLayout(null);
        JumpToMonth.setBounds(830,230,350,90);
        JumpToMonth.setBackground(Color.LIGHT_GRAY);
        JumpToMonth.setResizable(false);
        JumpToMonth.setVisible(true);

        //�����������ʱ��λ�ü���ʽ
        settime.setBounds(5,5,326,40);
        settime.setBackground(Color.white);
        settime.setLayout(null);
        JumpToMonth.add(settime);

        //����������������ʾ��λ�ü���ʽ
        TipsInputYear.setText("�������:");
        TipsInputYear.setFont(new Font("˼Դ����", Font.PLAIN, 13));
        TipsInputYear.setBounds(5,5,60,30);
        settime.add(TipsInputYear);

        //������������·���ʾ��λ�ü���ʽ
        TipsInputMonth.setBounds(110,5,60,30);
        TipsInputMonth.setText("�����·�:");
        TipsInputMonth.setFont(new Font("˼Դ����", Font.PLAIN, 13));
        settime.add(TipsInputMonth);
        
        //��������������λ�ü���ʽ
        inputYear.setBounds(65,5,40,30);
        settime.add(inputYear);

        //������������·�λ�ü���ʽ
        inputMonth.setBounds(170,5,40,30);
        settime.add(inputMonth);

        //������ת��ťλ�ü���ʽ
        JumpDo.setBounds(250,5,65,30);
        JumpDo.setBackground(new Color(159, 222, 223));
        JumpDo.setFont(new Font("˼Դ����", Font.PLAIN, 14));
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
                NowDate.setText(String.valueOf(year)+ "��"+ String.valueOf(month) + "��");
                clearFormat();
                try{
                    SetLunarOrVacation();
                }catch (Exception es){
                        System.out.println("��ȡʱ�����");
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
    
    //���ñ��ձ�����ɫ
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
    
    //labelͳһ�����¼�
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
