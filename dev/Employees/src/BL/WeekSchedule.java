package BL;

import DAL.DDay;
import DAL.Mapper;

public class WeekSchedule {
    private Day[] days;

    public WeekSchedule() {
        this.days = new Day[7];

    }
    public void initializeDays(int month, int day,int store_num, Mapper mapper)
    {
        for(int i = 0; i < 7; i++)
        {
            days[i] = new Day("" + month + "." + (day+i));
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
        day=((day+i)%31);
        return ""+day+"/"+month;
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



}
