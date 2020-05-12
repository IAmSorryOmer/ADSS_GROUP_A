package BL;

import DAL.DDay;
import DAL.Mapper;

public class WeekSchedule {
    private Day[] days;

    public WeekSchedule() {
        this.days = new Day[7];

    }
    public void initializeDays()
    {
        for(int i = 0; i < 7; i++)
        {
            days[i] = new Day();
        }
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
            days[i].load(store_num,mapper);
        }

    }



}
