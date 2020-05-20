package BL;

import DAL.DDay;
import DAL.DRole_ID;
import DAL.DRole_Needed;
import DAL.Mapper;

import java.util.HashMap;
import java.util.List;


public class Day {

    private HashMap<String,Integer[]> morning; // string - Role ,  Integer[] = employeeIDs
    private HashMap<String,Integer[]> evening;
    private String date;
    public Day(String date)
    {
        morning = new HashMap<>();
        evening = new HashMap<>();
        this.date = date;
        morning.put("shift manager", new Integer[1]);
        morning.get("shift manager")[0] = 0;
        evening.put("shift manager", new Integer[1]);
        evening.get("shift manager")[0] = 0;
    }
    public Day(DDay dDay)
    {
        morning = new HashMap<>();
        evening = new HashMap<>();
        date = dDay.getDate();
    }
    public void load(int dayNum,int store_num, Mapper mapper) {

        List<DRole_ID> dRole_ids1 = mapper.loadRole_ID(dayNum,store_num,0);
        List<DRole_Needed> dRole_needed1 = mapper.loadRole_NumNeeded(date,store_num,"morning");
        makeMorning(dRole_ids1,dRole_needed1);

        List<DRole_ID> dRole_ids2 = mapper.loadRole_ID(dayNum,store_num,1);
        List<DRole_Needed> dRole_needed2 = mapper.loadRole_NumNeeded(date,store_num,"evening");
        makeEvening(dRole_ids2,dRole_needed2);
    }

    public String getDate() {
        return date;
    }

    private void makeEvening(List<DRole_ID> dRole_ids,List<DRole_Needed> dRole_needed) {
        for (DRole_Needed roleNeeded : dRole_needed)
        {
            Integer [] ids = new Integer[roleNeeded.getNumOfNeeded()];
            for (int i=0; i<ids.length; i++)
                ids[i] = 0;

            DRole_ID dRole_id =null;
            for (DRole_ID role_id : dRole_ids)
            {
                if(role_id.getRole().equals(roleNeeded.getRole())) {
                    dRole_id = role_id;
                    break;
                }
            }
            if (dRole_id != null) {
                for (int i = 0; i < dRole_id.getId().size(); i++) {
                    ids[i] = dRole_id.getId().get(i);
                }
            }
            evening.put(roleNeeded.getRole(),ids);
        }

    }

    private void makeMorning( List<DRole_ID> dRole_ids,List<DRole_Needed> dRole_needed)
    {
        for (DRole_Needed roleNeeded : dRole_needed)
        {
            Integer [] ids = new Integer[roleNeeded.getNumOfNeeded()];
            for (int i=0; i<ids.length; i++)
                ids[i] = 0;
            DRole_ID dRole_id =null;
            for (DRole_ID role_id : dRole_ids)
            {
                if(role_id.getRole().equals(roleNeeded.getRole())) {
                    dRole_id = role_id;
                    break;
                }
            }
            if (dRole_id != null) {
                for (int i = 0; i < dRole_id.getId().size(); i++) {
                    ids[i] = dRole_id.getId().get(i);
                }
            }
            morning.put(roleNeeded.getRole(),ids);
        }
    }

    public HashMap<String,Integer[]> getEvening() {
        return evening;
    }

    public HashMap<String,Integer[]> getMorning() {
        return morning;
    }

    public boolean is_Assigned_To_Role(String role,String dayPart)
    {
        if (dayPart.equals("morning"))
        {
            if (morning.containsKey(role))
            {
                for (int i =0; i < morning.get(role).length; i ++)
                {
                    if (morning.get(role)[i] != 0)
                        return true;
                }
                return false;
            }
            return false;
        }
        if (dayPart.equals("evening"))
        {
            if (evening.containsKey(role))
            {
                for (int i =0; i < evening.get(role).length; i ++)
                {
                    if (evening.get(role)[i] != 0)
                        return true;
                }
                return false;
            }
            return false;
        }
        return false;

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
