import org.joda.time.Period;
import org.junit.Test;

public class TestTime {

	@Test
	public void testTime() {
		System.out.println("bug "+new Period(0,22,56,33,32,123,3445,31321));
		new Period(0,22,56,33,32,123,3445,31321);
	}
}
