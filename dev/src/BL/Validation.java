package BL;

public class Validation {
    public static String checkAddingNewEmployee(String[] jobs)
    {
        String returnValue = "";
        if (jobs.length == 0)
            returnValue += "No jobs entered!";
        else
            returnValue = "success";
        return returnValue;
    }


    public static String checkCabaleShifts(String[] capable_shifts, int dayNum, String dayPart,String[] capable_roles, String role ) {
        String returnValue = "";
        if (capable_shifts[dayNum-1].contains(dayPart))
        {
            boolean found = false;
            for (String r: capable_roles) {
                if (r.equals(role))
                    found = true;
            }
            if (!found)
            {
                returnValue = "the employee capable roles dont include this role";
            }
            else
            {
                returnValue = "success";
            }
        }
        else
            returnValue = "the shift is not in the capable shifts by the employee";
        return returnValue;
    }
    public static String checkSelectedShifts(int [] shifts_numbers)
    {
        for (int i = 0; i < shifts_numbers.length; i++)
        {
            if (shifts_numbers[i] < 0 || shifts_numbers[i] > 14)
                return "number not in range";
        }
        return "success";
    }

    public static License checkLicense(String license)
    {
        if (license.equals("C"))
        {
            return License.C;
        }
        if (license.equals("C1New"))
        {
            return License.C1New;
        }
        if (license.equals("C1Old"))
        {
            return License.C1Old;
        }
        if (license.equals("CPlusE"))
        {
            return License.CPlusE;
        }
        return License.NoLicense;
    }

}
