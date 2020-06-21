package BL;

import DAL.DDay;
import DAL.Mapper;

import java.time.LocalDate;

public class WeekSchedule {
    private Day[] days;

    public WeekSchedule() {
        this.days = new Day[7];

    }
    public void initializeDays(int month, int day,int store_num, Mapper mapper)
    {
        LocalDate localDate =  LocalDate.of(2020,6,21);
        StoreController.current_date = localDate
        mapper.saveCurrent_Date();

//        LocalDate dt = LocalDate.now();
//        dt.getDayOfMonth();
//        System.out.println(dt.getDayOfWeek().getValue());


        for(int i = 0; i < 7; i++)
        {
            LocalDate tmp = localDate.plusDays(i);//LocalDate.of(localDate.getYear(),localDate.getMonth().getValue(),localDate.getDayOfMonth() + i);

            days[i] = new Day(tmp.toString());
         //   days[i] = new Day("" + (day+i) + "." + month);
            mapper.saveDay(days[i].getDate(),store_num,i,0);
            mapper.saveAssignments_To_Shifts(days[i].getDate(),"shift manager",0,i,0,0,store_num);
            mapper.saveAssignments_To_Shifts(days[i].getDate(),"shift manager",1,i,0,0,store_num);
            mapper.saveRoleInShift("shift manager",days[i].getDate(),"morning",store_num,1);
            mapper.saveRoleInShift("shift manager",days[i].getDate(),"evening",store_num,1);
        }

    }
    public static String makeDate(int day, int month,int i){
        if(day+i>30){
            month++;
            i=i+1;
        }
        day=((day+i)%30);
        if (day == 0)
        {
            day = 30;
        }
        return ""+day+"."+month;
    }
    public Day getNextDay(String date)
    {
        for (int i =0; i < 7; i++)
        {
            if (days[i].getDate().equals(date))
            {
                if (i < 6)
                    return days[i+1];
            }
        }
        return null;
    }

    public boolean returnIfDayIsLastInTheWeek(String date)
    {
        if (days[6].getDate().equals(date))
            return true;
        else
            return false;
    }
    public Day[] getDays() {
        return days;
    }

    public void load(int store_num, Mapper mapper)
    {
        DDay Ddays [] = mapper.loadDays(store_num);
        for(int i = 0; i < 7; i++)
        {
            days[i] = new Day(Ddays[i]);
            days[i].load(i,store_num,mapper);
        }

    }
    public Day getDayByDate(String date)
    {
        for (int i =0; i <7; i++) {
            if (days[i].getDate().equals(date))
                return days[i];
        }

        return null;
    }


    //-----------------

    public void passDay(Mapper mapper) {

        // @TODO לשנות את השמה למשמרת שתפעל לפי היום שכרגע נמצא במיקום ה 0 לא להניח שזה ראשון
        //@TODO לשנות את יצירת התאריכים לימים לפונקציה של נריה
        //@TODO עובדים יכולים רק פעם אחת לשים משמרות לתמיד

        mapper.setAssignments_History(days[0].getDate());// Makes all the assignments at this date to be history
        mapper.setDay_History(days[0].getDate()); // Makes the Day history
        for (int i=0; i < 5; i++) //SHIFT RIGHT DAYS
        {
            days[i] = days[i+1];
        }
        days[7] = new Day();
    }

}
