package BL;

import DAL.DRole_ID;
import DAL.Mapper;

import java.util.HashMap;
import java.util.List;


public class Day {

    private HashMap<String,Integer[]> morning; // string - Role ,  Integer[] = employeeIDs
    private HashMap<String,Integer[]> evening;
    private String date;
    public Day()
    {
        morning = new HashMap<>();
        evening = new HashMap<>();

        morning.put("shift manager", new Integer[1]);
        morning.get("shift manager")[0] = 0;
        evening.put("shift manager", new Integer[1]);
        evening.get("shift manager")[0] = 0;
    }
    public void load(int store_num, Mapper mapper) {
        List<DRole_ID> dRole_ids1 = mapper.loadRole_ID(date,store_num,0);
        makeMorning(dRole_ids1);

        List<DRole_ID> dRole_ids2 = mapper.loadRole_ID(date,store_num,1);
        makeEvening(dRole_ids2);
    }

    private void makeEvening(List<DRole_ID> dRole_ids2) {

    }

    private void makeMorning( List<DRole_ID> dRole_ids)
    {

    }

    public HashMap<String,Integer[]> getEvening() {
        return evening;
    }

    public HashMap<String,Integer[]> getMorning() {
        return morning;
    }

    public String enterJobToShift(String dayPart, String role, int amount)
    {
        String result="";

        Integer[] roleEmployeesIds = new Integer[amount];
        for (int i = 0; i < amount; i++) {
            roleEmployeesIds[i] = 0;
        }

        if (dayPart.equals("morning")) {
            if (morning.keySet().contains(role))
                result = "role already exists in this shift";
            else {
                morning.put(role, roleEmployeesIds);
                result = "success";
            }
        }
        if (dayPart.equals("evening")) {
            if (evening.keySet().contains(role))
                result = "role already exists in this shift";
            else {
                evening.put(role, roleEmployeesIds);
                result = "success";
            }
        }

        return result;
    }

    public String enterEmployeeToShift(String dayPart, int employeeID, String role)
    {
        String result="";
        if (dayPart.equals("morning"))
        {
           result = enterEmployeeToShift2(morning,employeeID,role);
        }
        if (dayPart.equals("evening"))
        {
            result = enterEmployeeToShift2(evening,employeeID,role);
        }

        return result;
    }

    public String enterEmployeeToShift2(HashMap<String,Integer[]> dayPart ,  int employeeID, String role)
    {
        String result = "";
        if (!role.equals("shift manager"))
        {
            if (dayPart.get("shift manager")[0] == 0)
                return "you must assign shift manger to the shift before other roles";
        }
        for (String role2 : dayPart.keySet())
        {
            if (isContains(dayPart.get(role2),employeeID))
                return  "the employee already assigned to this shift";
        }

        if(dayPart.keySet().contains(role))
        {
            boolean hasPlace = false;
            for (int i=0; i < dayPart.get(role).length && !hasPlace; i ++)
            {
                if (dayPart.get(role)[i] == 0) {
                    dayPart.get(role)[i] = employeeID;
                    hasPlace = true;
                }
            }

            if (!hasPlace)
            {
                result = "the role is full of employees";
            }
            else
            {
                result = "success";
            }
        }
        else
        {
            result = "the role doesn't exist in the shift";
        }
        return  result;
    }

    public Boolean isContains(Integer [] arr, int id)
    {
        for (int i =0; i <arr.length; i++) {
            if (arr[i] == id)
                return true;
        }
        return false;
    }


}
