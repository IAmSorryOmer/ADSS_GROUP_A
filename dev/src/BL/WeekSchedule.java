package BL;

import DAL.DDay;
import DAL.Mapper;
import Entities.Pair;

import java.time.LocalDate;
import java.util.HashMap;

import static BL.StoreController.current_date;

public class WeekSchedule {
    private Day[] days;

    public WeekSchedule() {
        this.days = new Day[7];
    }
    public void initializeDays(int month, int day,int store_num, Mapper mapper)
    {
        LocalDate localDate = LocalDate.of(2020,month,day);// LocalDate.of(2020,6,21);
        current_date = localDate;

//        LocalDate dt = LocalDate.now();
//        dt.getDayOfMonth();
//        System.out.println(dt.getDayOfWeek().getValue());


        for(int i = 0; i < 7; i++)
        {
            LocalDate tmp = localDate.plusDays(i);//LocalDate.of(localDate.getYear(),localDate.getMonth().getValue(),localDate.getDayOfMonth() + i);

            days[i] = new Day(tmp);
         //   days[i] = new Day("" + (day+i) + "." + month);
            mapper.saveDay(days[i].getDate().toString(),store_num,i,0);
            mapper.saveAssignments_To_Shifts(days[i].getDate().toString(),"shift manager",0,i,0,0,store_num);
            mapper.saveAssignments_To_Shifts(days[i].getDate().toString(),"shift manager",1,i,0,0,store_num);
            mapper.saveRoleInShift("shift manager",days[i].getDate().toString(),"morning",store_num,1);
            mapper.saveRoleInShift("shift manager",days[i].getDate().toString(),"evening",store_num,1);
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

    public void passDay(Mapper mapper,LocalDate curr, int store_num) {

        // @TODO לשנות את השמה למשמרת שתפעל לפי היום שכרגע נמצא במיקום ה 0 לא להניח שזה ראשון
        //@TODO לשנות את יצירת התאריכים לימים לפונקציה של נריה
        //@TODO עובדים יכולים רק פעם אחת לשים משמרות לתמיד

        mapper.setAssignments_History(days[0].getDate().toString());// Makes all the assignments at this date to be history
        mapper.setDay_History(days[0].getDate().toString()); // Makes the Day history
        days[6] = new Day(days[5].getDate().plusDays(1));
        int newDay = ((StoreController.Day_In_Week+5)%7)+1;
        mapper.saveDay(days[6].getDate().toString(), store_num, newDay, 0);
        for (int i=0; i < 6; i++) //SHIFT RIGHT DAYS
        {
            days[i] = days[i+1];
        }

    }

    public int isStorageInDay(LocalDate lcl){
        boolean foundBoth=false;
        int morOrEve=-1;
        int id1=0;
        int id2=0;
        LocalDate d=null;
        for(int i=0;i<7&&!foundBoth;++i) {
            HashMap<String, Integer[]> m = days[i].getMorning();


            if (days[i].getDate().equals(lcl)) {
                for (String role : m.keySet()
                ) {


                    if (role.equals("storage")) {
                        for (int id : m.get(role)
                        ) {
                            if (id != 0) {
                                return 0;
                            }
                        }
                    }

                }



                for (String role : m.keySet()
                ) {


                    if (role.equals("storage")) {
                        for (int id : m.get(role)
                        ) {
                            if (id != 0) {
                                return 1;
                            }
                        }
                    }

                }


            }

        }
        return -1;
    }


    //RETURN PAIR OF INTEGER ARRAY [MorningOrEvening(0 morning 1 evening), id of driver], and the date of the delivery
    public Pair<Integer[],LocalDate> findDateForOrder(){
        boolean foundBoth=false;
        int morOrEve=-1;
        int id1=0;
        int id2=0;
        LocalDate d=null;
        for(int i=0;i<7&&!foundBoth;++i){
            HashMap<String,Integer[]> m=days[i].getMorning();
            boolean foundDriver=false;
            boolean foundStorage=false;


            for ( String role:m.keySet()
                 ) {
                if(role.equals("driver")){
                    for (int id: m.get(role)
                         ) {
                        if(id!=0){
                            id1=id;
                            foundDriver=true;
                        }
                    }
                }

                if(role.equals("storage")){
                    for (int id: m.get(role)
                    ) {
                        if(id!=0){
                            id2=id;
                            foundStorage=true;
                        }
                    }
                }

            }
            if(foundDriver&&foundStorage){
                foundBoth=true;
                morOrEve=0;
                d=days[i].getDate();

            }
            foundDriver=false;
            foundStorage=false;

        }

        for(int i=0;i<7&&!foundBoth;++i){
            HashMap<String,Integer[]> m=days[i].getEvening();
            boolean foundDriver=false;
            boolean foundStorage=false;


            for ( String role:m.keySet()
            ) {
                if(role.equals("driver")){
                    for (int id: m.get(role)
                    ) {
                        if(id!=0){
                            id1=id;
                            foundDriver=true;
                        }
                    }
                }

                if(role.equals("storage")){
                    for (int id: m.get(role)
                    ) {
                        if(id!=0){
                            id2=id;
                            foundStorage=true;
                        }
                    }
                }

            }
            if(foundDriver&&foundStorage){
                foundBoth=true;
                morOrEve=1;
                d=days[i].getDate();
            }

        }
        if(!foundBoth){
            return null;
        }
        Integer[] zura={morOrEve,id1};
        return new Pair(zura,d);
    }

}
