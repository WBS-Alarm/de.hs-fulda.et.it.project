import de.hsfulda.et.wbs.security.Password;
import org.junit.jupiter.api.Test;

public class passwordTest {
    @Test
    void test()
    {
        System.out.println(Password.hashPassword("testpassword"));
    }
}
