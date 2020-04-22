package BL;

public class WeekSchedule {
    private Day[] days;

    public WeekSchedule() {
        this.days = new Day[7];
        for(int i = 0; i < 7; i++)
        {
            days[i] = new Day();
        }
    }

    public Day[] getDays() {
        return days;
    }
}
