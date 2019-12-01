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
	
	//年为阳历，月日均为农历
    private int year;  
    private int month;  
    private int day;
    private int daysOfMonth;
    private boolean leap;
    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"}; 
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");  
    
    /**
     * 
     * 1 - 4:表示有无闰年，有的话，为闰月的月份，没有为0
     * 
     * 5 - 16：除了闰月之外其他月份是大月还是小月，1为30天，0为29天
     *         注意：从一月到12月对应的是第16位至第5位
     *         
     * 17 - 20：表示闰月是大月还是小月（代表闰几月
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
     * 假日类，
     * 包含lunarHolidays和solarHolidays
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
    	      new Holiday(1, 1, "春节"), new Holiday(1, 15, "元宵节"), new Holiday(5, 5, "端午节"),
    	      new Holiday(7, 7, "七夕节"), new Holiday(7, 15, "中元节"), new Holiday(8, 15, "中秋节"),
    	      new Holiday(9, 9, "重阳节"), new Holiday(12, 8, "腊八节"), 
    	  };

    /* all the solarHolidays */
    private final Holiday[] solarHolidays = new Holiday[] {
    	      new Holiday(1, 1, "元旦"), new Holiday(2, 14, "情人节"), new Holiday(3, 8, "妇女节"),
    	      new Holiday(3, 12, "植树节"),  new Holiday(4, 1, "愚人节"), new Holiday(5, 1, "劳动节"), 
    	      new Holiday(5, 4, "青年节"), new Holiday(6, 1, "儿童节"), new Holiday(6, 26, "禁毒日"), 
    	      new Holiday(7, 1, "建党节"),new Holiday(8, 1, "建军节"),  new Holiday(9, 10, "教师节"),
    	      new Holiday(10, 1, "国庆节"), new Holiday(12, 24, "平安夜"), new Holiday(12, 25, "圣诞节"),
    	  };
    
    /**
     * 得到农历y年的总天数
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
     * 得到农历 y年闰月的天数 
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
     * 得到农历 y年闰月的天数 
     *
     * @param y
     * @return which_month_is_leap/0
     * 
     */
    final private static int leapMonth(int y) {  
        return (int) (lunarInfo[y - 1900] & 0xf);  
    }  
  
    /**
     * 得到农历 y年闰月的天数 
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
     * 得到农历 y年生肖
     *
     * @param year
     * @return 生肖
     * 
     */
    final public String animalsYear() {  
        final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};  
        return Animals[(year - 4) % 12];  
    }  
  
    //====== 传入 月日的offset 传回干支, 0=甲子  
    /**
     * 得到农历 y年干支
     *
     * @param num (距离1864年年数)
     * @return 生肖
     * 
     */
    final private static String cyclicalm(int num) { 
        final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};  
        final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};  
        return (Gan[num % 10] + Zhi[num % 12]);  
    }  
    final public String cyclical() {  
        int num = year - 1900 + 36;  
        return (cyclicalm(num));  
    }  
  
    /** 
     * 传出y年m月d日对应的农历. 
     * yearCyl3:农历年与1864的相差数 ? 
     * monCyl4:从1900年1月31日以来,闰月数 
     * dayCyl5:与1900年1月31日相差的天数,再加40 ? 
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
            baseDate = chineseDateFormat.parse("1900年1月31日");  
        } catch (ParseException e) {  
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.  
        }  
  
        //求出和1900年1月31日相差的天数  
        int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);  
        dayCyl = offset + 40; 
        monCyl = 14;  
  
        //用offset减去每农历年的天数  
        //i最终结果是农历的年份  
        //offset是当年的第几天 
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
        //农历年份  
        year = iYear;  
  
        yearCyl = iYear - 1864;  
        leapMonth = leapMonth(iYear); //闰哪个月,1-12  
        leap = false;  
  
        //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天  
        iMonth = 0;
        daysOfMonth = 0;  
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {  
        	//闰月  
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {  
                --iMonth;  
                leap = true;  
                daysOfMonth = leapDays(year);  
            } else  
                daysOfMonth = monthDays(year, iMonth);  
  
            offset -= daysOfMonth;  
            //解除闰月  
            if (leap && iMonth == (leapMonth + 1)) leap = false;  
            if (!leap) monCyl++;  
        }  
        //offset为0时，并且刚才计算的月份是闰月，要校正  
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {  
            if (leap) {  
                leap = false;  
            } else {  
                leap = true;  
                --iMonth;  
                --monCyl;  
            }  
        }  
        //offset小于0时，也要校正  
        if (offset < 0) {  
            offset += daysOfMonth;  
            --iMonth;  
            --monCyl;  
        }  
        month = iMonth;  
        day = offset + 1;  
    }  
    
    public static String getChinaDayString(int day) {  
        String chineseTen[] = {"初", "十", "廿", "卅"};  
        int n = day % 10 == 0 ? 9 : day % 10 - 1;  
        if (day > 30)  
            return "";  
        if (day == 10)  
            return "初十";  
        else  
            return chineseTen[day / 10] + chineseNumber[n];  
    }  
    
    public String GetDate() {  
        return cyclical() + animalsYear()+"年"+(leap ? "闰" : "") + chineseNumber[month - 1] + "月" + getChinaDayString(day); 
    } 
    //获取某一天的农历
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
          return "除夕";
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
        if(s.equals("初一")) s = (LunarOfOneDay.leap ? "闰" : "")
                + LunarOfOneDay.chineseNumber[LunarOfOneDay.month - 1] + "月";
        String s2 = LunarOfOneDay.getSolarHoliday(y,z);
        String s3 = LunarOfOneDay.getLunarHoliday();
        if(s3 != null )return s3;
        else if(s2!=null)return s2;
        else return s;
    }
}
