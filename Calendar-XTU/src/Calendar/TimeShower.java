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
 * �����ӱ���ǰ����չʾ����
 * ����JPanel��ʽ
 * ����ActionListener�ӿ�
 * �ڱ������г���ʹ����MouseListener()��MouseAdapter()������������ʽ
 * MouseListener()����ʡ��δ�õļ����¼�
 * MouseAdapter()����ʡ��δ�õļ����¼�
 * 
 * @author FangHaiPeng
 */
public class TimeShower extends JPanel implements ActionListener{
	
	private static final int Limit = 1;
	private static final long serialVersionUID = 2L;
	long startTime ;
	long endTime;
	//�����ʽ����ʽ
	SimpleDateFormat Time_Format = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat Day_Format = new SimpleDateFormat("YYYY��MM��dd��");
	
	//��������Ӻ͵�ǰʱ��Label
	JLabel Electronic_Clock = new JLabel("",JLabel.LEFT);
	JLabel Current_Date = new JLabel("",JLabel.LEFT);
	
	//�������ʱ�乹��
    private JFrame Network_Time_Service;
    private JPanel Network_Select;
    private JComboBox<String> Network_Select_JCombobox;
    private JButton Update_Do;
    private JLabel NetWork_Select_Now;//��ʱ��ѡ�еĿ�
    
    private String AllWebUrl[] = {
	    		"Local Time",//����ʱ��
	    		"http://www.baidu.com",//�ٶ�
	            "http://www.taobao.com",//�Ա�
	            "http://www.ntsc.ac.cn",//�й���ѧԺ������ʱ����
	            "http://www.360.cn",//360
	            "http://www.beijing-time.org"
            };//����ʱ��
    private String SelectedUrl;//ȷ�ϵ�ͬ����ַ
    

    /*
     * �ú��뼶time����ǰʱ���ʱ��
     * ���ڱ��������������ʱ��ʱ�ظ���ȡ����ʱ��
     * �������м�ʱ������ʵ��ʱ���ʱ
     * +200ms��Ϊ
     * 		�������ԣ���������ʱ���ʹ��ʾ��ʱ��������ʱ��������200ms
     */
    long time = System.currentTimeMillis()+250;
    
    /**
     * 
     * TimeShower���췽��
     * ͨ��Timer��ʱ��ʵ��ˢ�¹���
     * 
     */
	public TimeShower() {
		this.setLayout(null);
		
		/*
		 * ��һ���Ƚ�ʱ�����úã�������ֳ�������ʱ�ϲ����ʱ�Ӻ�������ʱ����
		 */
		String  Time_Gained = Time_Format.format(time);
		Electronic_Clock.setText(" " + Time_Gained);
		Current_Date.setText("   "+Day_Format.format(new Date()) + " " + Lunar.GetCompleteDate());
		
		/*���õ���ʱ��չʾ�����λ�ü���ʽ*/
		Electronic_Clock.setBounds(0, 17, 300, 50);
		Electronic_Clock.setFont(new Font("����",Font.PLAIN,45));
		Electronic_Clock.setForeground(Color.WHITE);
		
		/*
		 * ��׽��
		 * Entered����ɫ�任
		 * Exited����ɫ�任
		 * Clicked������������ʱ
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
		
		/*���õ�ǰʱ��չʾ�����λ�ü���ʽ*/
		Current_Date.setBounds(0, 60, 375, 50);
		Current_Date.setFont(new Font("����",Font.PLAIN,16));      	
		Current_Date.setForeground(new Color(60,156,228));
		/*
		 * ��׽��
		 * Entered����ɫ�任
		 * Exited����ɫ�任
		 * Clicked��չʾ���յ�ת��ʱ��
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
		 * ����Timer��ʱ��ʵ��ͬ�߳����ƵĴ��¹���
		 */
		Timer timer = new Timer(1000,this);
		timer.start(); 
		
	}
	
	/**
	   * ʱ���ʱ��ʱ��ˢ��
	   *
	   * @param time
	   * @return
	   */
	public void actionPerformed(ActionEvent e)
	{
		/*ʵ��ʱ���ʱ*/
		time +=1000;
		
		/*ʵ��ʱ��ˢ��*/
		String Time_Gained = Time_Format.format(time);
		Electronic_Clock.setText(" "+Time_Gained);
		Current_Date.setText("   "+Day_Format.format(new Date()) + " " + Lunar.GetCompleteDate());
		
	}
	
	/**
	 * 
	 * ����Network_Time_Service
	 *
	 */
	public void SetNetworkUrl(){

		//����������ʱJFrame���λ�ü���ʽ
        Network_Time_Service = new JFrame("Network_Time_Service");
        Network_Time_Service.setBounds(860, 80, 300, 240);
        Network_Time_Service.getContentPane().setBackground(Color.WHITE);
        Network_Time_Service.setLayout(null);
        Network_Time_Service.setResizable(false);
        Network_Time_Service.setVisible(true);
        
        //����������ʱJPanel����λ�ü���ʽ
        Network_Select = new JPanel();
        Network_Select.setBounds(5,5,290,230);
        Network_Select.setBackground(Color.white);
        Network_Select.setLayout(null);
        Network_Select.setVisible(true);
        
        //����������ʱѡ�������򹹼�λ�ü���ʽ
        Network_Select_JCombobox = new JComboBox<String>(AllWebUrl);
        Network_Select_JCombobox.setFont(new Font("����",Font.PLAIN,14));
        Network_Select_JCombobox.setVisible(true);
        Network_Select_JCombobox.setBounds(5,5,270,25);
        Network_Select_JCombobox.setBackground(Color.WHITE);
        Network_Select.add(Network_Select_JCombobox);

        //����������ʱ���°�ť����λ�ü���ʽ
        Update_Do = new JButton("��������");
        Update_Do.setBounds(85,160,100,30);
        Update_Do.setFont(new Font("����",Font.PLAIN,15));
        Update_Do.setBackground(new Color(159, 222, 223));
        Update_Do.setForeground(Color.BLACK);
        Update_Do.setFocusable(false);
        Update_Do.setBorderPainted(false);
        Network_Select.add(Update_Do);
        
        //����������ʱ����Ч����ʾ����λ�ü���ʽ
        NetWork_Select_Now = new JLabel();
        Network_Time_Service.getContentPane().add(Network_Select);
        NetWork_Select_Now.setFont(new Font("����", Font.PLAIN, 20));
        NetWork_Select_Now.setBounds(5, 45, 280, 50);
        NetWork_Select_Now.setOpaque(true);
        NetWork_Select_Now.setBackground(Color.WHITE);
        NetWork_Select_Now.setForeground(Color.black);
        Network_Select.add(NetWork_Select_Now);
        
    }
	
	/**
	   * 
	   *JCombobox����
	   *
	   */
    private void SetJCombobox(){
    	SetNetworkUrl();
		/*
		 * 
		 * ��׽��
		 * mouseClicked������������
		 * itemStateChanged���޸�Ŀ����
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
                    NetWork_Select_Now.setText("<html><body><p align = \"center\">"+"��"+SelectedUrl +"<br>"+"ͬ���ɹ���" +"</p></body></html>");
                } catch (Exception es) {
                	NetWork_Select_Now.setText("��������ӻ��������");
                }
            }
        });
    }
    
	/**
	   * ʱ���ʱ��ʱ��ˢ��
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
                    long time = uc.getDate() ;// ��ȡ��վ����ʱ��
                    Date date = new Date(time);// ת��Ϊ��׼ʱ�����
                    return date;
            	}
            }
        } catch (MalformedURLException e) {
        	NetWork_Select_Now.setText("��������ӻ��������");
        } catch (IOException e) {
        	NetWork_Select_Now.setText("��������ӻ��������");
        } 
        
        return null;
    }
}
