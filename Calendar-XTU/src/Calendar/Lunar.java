package Calendar;

import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;

/**
 * 
 * Lunar calendar in 200 years from 1900.
 * @author FangHaiPeng
 * 
 */
public class Lunar {
	
	//��Ϊ���������վ�Ϊũ��
    private int year;  
    private int month;  
    private int day;
    private int daysOfMonth;
    private boolean leap;
    final static String chineseNumber[] = {"һ", "��", "��", "��", "��", "��", "��", "��", "��", "ʮ", "��", "��"}; 
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy��MM��dd��");  
    
    /**
     * 
     * 1 - 4:��ʾ�������꣬�еĻ���Ϊ���µ��·ݣ�û��Ϊ0
     * 
     * 5 - 16����������֮�������·��Ǵ��»���С�£�1Ϊ30�죬0Ϊ29��
     *         ע�⣺��һ�µ�12�¶�Ӧ���ǵ�16λ����5λ
     *         
     * 17 - 20����ʾ�����Ǵ��»���С�£���������
     * 
     */
    final static long[] lunarInfo = new long[]  
    {
    	 0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,  
	     0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,  
	     0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,  
	     0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,  
	     0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,  
	     0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,  
	     0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,  
	     0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,  
	     0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,  
	     0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,  
	     0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,  
	     0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,  
	     0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,  
	     0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,  
	     0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
	     };  
    
    /**
     * 
     * �����࣬
     * ����lunarHolidays��solarHolidays
     * 
     */
    private class Holiday {
    	int month;
    	int day;
        private String name;

        public Holiday(int month, int day, String name) {
          this.month = month;
          this.day = day;
          this.name = name;
        }
        
        public int getDay() {
          return day;
        }

        public int getMonth() {
          return month;
        }

        public String getName() {
          return name;
        }
      }
    
    /* all the lunarHolidays */
    private final Holiday[] lunarHolidays = new Holiday[] {
    	      new Holiday(1, 1, "����"), new Holiday(1, 15, "Ԫ����"), new Holiday(5, 5, "�����"),
    	      new Holiday(7, 7, "��Ϧ��"), new Holiday(7, 15, "��Ԫ��"), new Holiday(8, 15, "�����"),
    	      new Holiday(9, 9, "������"), new Holiday(12, 8, "���˽�"), 
    	  };

    /* all the solarHolidays */
    private final Holiday[] solarHolidays = new Holiday[] {
    	      new Holiday(1, 1, "Ԫ��"), new Holiday(2, 14, "���˽�"), new Holiday(3, 8, "��Ů��"),
    	      new Holiday(3, 12, "ֲ����"),  new Holiday(4, 1, "���˽�"), new Holiday(5, 1, "�Ͷ���"), 
    	      new Holiday(5, 4, "�����"), new Holiday(6, 1, "��ͯ��"), new Holiday(6, 26, "������"), 
    	      new Holiday(7, 1, "������"),new Holiday(8, 1, "������"),  new Holiday(9, 10, "��ʦ��"),
    	      new Holiday(10, 1, "�����"), new Holiday(12, 24, "ƽ��ҹ"), new Holiday(12, 25, "ʥ����"),
    	  };
    
    /**
     * �õ�ũ��y���������
     *
     * @param y
     * @return total_days_of_lunar
     */
    final private static int yearDays(int y) {  
        int i, sum = 348;  
        for (i = 0x8000; i > 0x8; i >>= 1) {  
            if ((lunarInfo[y - 1900] & i) != 0) sum += 1;  
        }  
        return (sum + leapDays(y));  
    }  
  
    /**
     * �õ�ũ�� y�����µ����� 
     *
     * @param y
     * @return leap_days_of_lunar
     */
    final private static int leapDays(int y) {  
        if (leapMonth(y) != 0) {  
            if ((lunarInfo[y - 1900] & 0x10000) != 0)  
                return 30;  
            else  
                return 29;  
        } else  
            return 0;  
    }  
  
    /**
     * �õ�ũ�� y�����µ����� 
     *
     * @param y
     * @return which_month_is_leap/0
     * 
     */
    final private static int leapMonth(int y) {  
        return (int) (lunarInfo[y - 1900] & 0xf);  
    }  
  
    /**
     * �õ�ũ�� y�����µ����� 
     *
     * @param y,m
     * @return days_of_month
     * 
     */
    final private static int monthDays(int y, int m) {  
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)  
            return 29;  
        else  
            return 30;  
    }  
  
    /**
     * �õ�ũ�� y����Ф
     *
     * @param year
     * @return ��Ф
     * 
     */
    final public String animalsYear() {  
        final String[] Animals = new String[]{"��", "ţ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��"};  
        return Animals[(year - 4) % 12];  
    }  
  
    //====== ���� ���յ�offset ���ظ�֧, 0=����  
    /**
     * �õ�ũ�� y���֧
     *
     * @param num (����1864������)
     * @return ��Ф
     * 
     */
    final private static String cyclicalm(int num) { 
        final String[] Gan = new String[]{"��", "��", "��", "��", "��", "��", "��", "��", "��", "��"};  
        final String[] Zhi = new String[]{"��", "��", "��", "î", "��", "��", "��", "δ", "��", "��", "��", "��"};  
        return (Gan[num % 10] + Zhi[num % 12]);  
    }  
    final public String cyclical() {  
        int num = year - 1900 + 36;  
        return (cyclicalm(num));  
    }  
  
    /** 
     * ����y��m��d�ն�Ӧ��ũ��. 
     * yearCyl3:ũ������1864������� ? 
     * monCyl4:��1900��1��31������,������ 
     * dayCyl5:��1900��1��31����������,�ټ�40 ? 
     * 
     * @param cal 
     * @return 
     */  
    public Lunar(Calendar cal) {  
    	int iYear,iMonth;
        int yearCyl, monCyl, dayCyl;
        int leapMonth = 0;  
        Date baseDate = null;  
        try {  
            baseDate = chineseDateFormat.parse("1900��1��31��");  
        } catch (ParseException e) {  
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.  
        }  
  
        //�����1900��1��31����������  
        int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);  
        dayCyl = offset + 40; 
        monCyl = 14;  
  
        //��offset��ȥÿũ���������  
        //i���ս����ũ�������  
        //offset�ǵ���ĵڼ��� 
        int daysOfYear = 0;  
        for (iYear = 1900; iYear <= 2100 && offset > 0; iYear++) {  
            daysOfYear = yearDays(iYear);  
            offset -= daysOfYear;  
            monCyl += 12;  
        }  
        if (offset < 0) {  
            offset += daysOfYear;  
            iYear--;  
            monCyl -= 12;  
        }  
        //ũ�����  
        year = iYear;  
  
        yearCyl = iYear - 1864;  
        leapMonth = leapMonth(iYear); //���ĸ���,1-12  
        leap = false;  
  
        //�õ��������offset,�����ȥÿ�£�ũ��������������������Ǳ��µĵڼ���  
        iMonth = 0;
        daysOfMonth = 0;  
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {  
        	//����  
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {  
                --iMonth;  
                leap = true;  
                daysOfMonth = leapDays(year);  
            } else  
                daysOfMonth = monthDays(year, iMonth);  
  
            offset -= daysOfMonth;  
            //�������  
            if (leap && iMonth == (leapMonth + 1)) leap = false;  
            if (!leap) monCyl++;  
        }  
        //offsetΪ0ʱ�����Ҹղż�����·������£�ҪУ��  
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {  
            if (leap) {  
                leap = false;  
            } else {  
                leap = true;  
                --iMonth;  
                --monCyl;  
            }  
        }  
        //offsetС��0ʱ��ҲҪУ��  
        if (offset < 0) {  
            offset += daysOfMonth;  
            --iMonth;  
            --monCyl;  
        }  
        month = iMonth;  
        day = offset + 1;  
    }  
    
    public static String getChinaDayString(int day) {  
        String chineseTen[] = {"��", "ʮ", "إ", "ئ"};  
        int n = day % 10 == 0 ? 9 : day % 10 - 1;  
        if (day > 30)  
            return "";  
        if (day == 10)  
            return "��ʮ";  
        else  
            return chineseTen[day / 10] + chineseNumber[n];  
    }  
    
    public String GetDate() {  
        return cyclical() + animalsYear()+"��"+(leap ? "��" : "") + chineseNumber[month - 1] + "��" + getChinaDayString(day); 
    } 
    //��ȡĳһ���ũ��
    public String toString2() {  
        return getChinaDayString(day); 
    } 
    
    /**
     * Get solar holiday if existed.
     *
     * @param leap
     * @param month
     * @param day
     * @param dayOfMonth
     * @return Lunar holiday, null if not existed
     * 
     */
    public String getLunarHoliday() {
        if (leap) {
          return null;
        }
        if (month == 12 && day == daysOfMonth) {
          return "��Ϧ";
        }

        for (Holiday holiday : lunarHolidays) {
          if (holiday.getMonth() == month  && holiday.getDay() == day) {
            return holiday.getName();
          }
        }

        return null;
      }

      /**
       * Get solar holiday if existed.
       *
       * @return solar holiday, null if not existed
       */
      public String getSolarHoliday(int y,int z) {
        for (Holiday holiday : solarHolidays) {
          if (holiday.getMonth() == y && holiday.getDay() == z) {
            return holiday.getName();
          }
        }
        return null;
      }
      
    public static String GetCompleteDate() {  
     Calendar cal = Calendar.getInstance();
     Lunar lunar = new Lunar(cal);  
     String CompleteDate = lunar.GetDate();
     return CompleteDate;
    }
    
    public static String GetLunarOfOneDay(int x,int y,int z) {
    	Calendar NowDay = Calendar.getInstance();
    	NowDay.set(x,y-1,z);
    	Lunar LunarOfOneDay = new Lunar(NowDay);
    	String s = LunarOfOneDay.toString2() ;
        if(s.equals("��һ")) s = (LunarOfOneDay.leap ? "��" : "")
                + LunarOfOneDay.chineseNumber[LunarOfOneDay.month - 1] + "��";
        String s2 = LunarOfOneDay.getSolarHoliday(y,z);
        String s3 = LunarOfOneDay.getLunarHoliday();
        if(s3 != null )return s3;
        else if(s2!=null)return s2;
        else return s;
    }
}
