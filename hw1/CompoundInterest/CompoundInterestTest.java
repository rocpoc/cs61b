import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        int y1 = CompoundInterest.numYears(2017);
        int y2 = CompoundInterest.numYears(2020);

        assertEquals(2, y1);
        assertEquals(5, y2);

    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;

        double f1 = CompoundInterest.futureValue(10, 12, 2017);
        double f2 = CompoundInterest.futureValue(300, 15, 2024);
        double f3 = CompoundInterest.futureValue(10, 12, 2010);

        assertEquals(f1, 12.54, tolerance);
        assertEquals(f2, 1055.36, tolerance);
        assertEquals(f3, 5.67, tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;

        double fr1 = CompoundInterest.futureValueReal(10, 12, 2017, 3);
        double fr2 = CompoundInterest.futureValueReal(300, 15, 2024, 5);
        double fr3 = CompoundInterest.futureValueReal(500, 7.5, 2025, 2.75);

        assertEquals(fr1, 11.802, tolerance);
        assertEquals(fr2, 665.14, tolerance);
        assertEquals(fr3, 779.74, tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
       
        Double TS1 = CompoundInterest.totalSavings(5000, 2017, 10);
        Double TS2 = CompoundInterest.totalSavings(10000, 2020, 10);
        Double TS3 = CompoundInterest.totalSavings(100, 2030, 10); 
        assertEquals(TS1, 16550, tolerance);
        assertEquals(TS2, 77156.1, tolerance);
        assertEquals(TS3, 3594.97, tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        Double TSR1 = CompoundInterest.totalSavingsReal(5000, 2017, 10, 3);
        Double TSR2 = CompoundInterest.totalSavingsReal(10000, 2020, 10,3 );
        Double TSR3 = CompoundInterest.totalSavingsReal(100, 2030, 10, 4);
        assertEquals(TSR1, 15571.89, tolerance);
        assertEquals(TSR2, 66256.56, tolerance);
        assertEquals(TSR3, 1948.78, tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
