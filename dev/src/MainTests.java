import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTests {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TESTS.class);
        System.out.println("test run :" + result.getRunCount());
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
