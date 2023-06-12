package at.spengergasse.company1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    private Company company;

    @BeforeEach
    void setUp() {
        try {
            // company = new Company("Spenger Inc.", "Spengergasse 20, 1050 Wien");
            Company.clearInstance();
            company = Company.getInstance();
            if (company.getStaff().size() == 0) {
                company.setCompanyName("Spenger Inc.");
                company.setCompanyAddress("Spengergasse 20, 1050 Wien");

                Employee e1 = new Employee(1001l, "Donald", "Knuth", "knuth@spengergasse.at", "+43 1 54615 101",
                        LocalDate.of(1938, 1, 10), Employee.Role.DEVELOPER, 12000, false);
                company.hire(e1);

                Employee e2 = new Employee(1002l, "Bjarne", "Stroustrup", "stroustrup@spengergasse.at", "+43 1 54615 102",
                        LocalDate.of(1950, 12, 30), Employee.Role.MANAGER, 13000, false);
                company.hire(e2);

                Employee e3 = new Employee(1003l, "Dennis", "Ritchie", "ritchie@spengergasse.at", "+43 1 54615 113",
                        LocalDate.of(1941, 9, 9), Employee.Role.DEVELOPER, 9000, true);
                company.hire(e3);

                Employee e4 = new Employee(1004l, "Linus", "Torwalds", "torwalds@spengergasse.at", "+43 1 54615 121",
                        LocalDate.of(1969, 12, 28), Employee.Role.MANAGER, 7000, false);
                company.hire(e4);

                Employee e5 = new Employee(1005l, "Martin", "Fowler", "fowler@spengergasse.at", "+43 1 54615 127",
                        LocalDate.of(1963, 12, 18), Employee.Role.ANALYST, 11000, false);
                company.hire(e5);
            }
            company.print();

        } catch (CompanyException e) {
            fail("Exception should not be thrown! ");
        }
    }

    @Test
    void hire() {
        Employee e6 = null;
        try {
            assertEquals(5, company.numberOfEmployees());
            e6 = new Employee(1006l, "James", "Gosling", "ggosling@spengergasse.at", "+43 1 54615 227",
                    LocalDate.of(1955, 5, 19), Employee.Role.ANALYST, 10500, true);
            company.hire(e6);
            assertEquals(6, company.numberOfEmployees());
        } catch (CompanyException e) {
            fail("Exception should not be thrown! ");
        }

        boolean exceptionThrown = false;
        try {
            company.hire(null);
        } catch (CompanyException e) {
            exceptionThrown = true;
        }
        assertEquals(6, company.numberOfEmployees());
        assertEquals(true, exceptionThrown);

        exceptionThrown = false;
        try {
            assertEquals(6, company.numberOfEmployees());
            company.hire(e6); // we try to hire e6 again -> exception should be thrown
            fail("Exception not thrown as expected. ");
        } catch (CompanyException e) {
            exceptionThrown = true;
        }
        assertEquals(6, company.numberOfEmployees());
        assertEquals(true, exceptionThrown);

    }

    @Test
    void fireName() {
        try {

            Employee e6 = new Employee(1006l, "James", "Gosling", "gosling@spengergasse.at", "+43 1 54615 227",
                    LocalDate.of(1955, 5, 19), Employee.Role.ANALYST, 10500, true);
            company.hire(e6);
            Employee e7 = new Employee(1007l, "James", "Gosling", "gosling@spengergasse.at", "+43 1 54615 227",
                    LocalDate.of(1955, 5, 20), Employee.Role.ANALYST, 10500, true);
            company.hire(e7);
            // ATTENTION: the day of birth has been changed, so equals does not account these two employes to be equal.
            assertEquals(7, company.numberOfEmployees());

            company.print();

            company.fire("James", "Gosling");
            assertEquals(5, company.numberOfEmployees());

            company.print();

        } catch (CompanyException e) {
            fail("Exception should not be thrown! ");
        }
    }

    @Test
    void getNumberOfManagers() {
        assertEquals(2, company.numberOfManagers());
    }

    @Test
    void employeesSortedLastName() {
        List<Employee> e1 = company.employeesSortedLastName();
        System.out.println("Sorted last name increasing: ");
        e1.forEach(e -> System.out.println(e));
        System.out.println();
    }

    @Test
    void employeesSortedSalaryDecreasing() {
        List<Employee> e2 = company.employeesSortedSalaryDecreasing();
        System.out.println("Sorted salary decreasing: ");
        e2.forEach(e -> System.out.println(e));
        System.out.println();
    }

    @Test
    void employeesSortedSalaryDecreasingWithSalaryFilter() {
        List<Employee> e3 = company.employeesSortedSalaryDecreasing(10000);
        System.out.println("Sorted salary decreasing, filtered for minSalary > 10000: ");
        e3.forEach(e -> System.out.println(e));
        System.out.println();
    }

    @Test
    void increaseSalaryByPercentage() {
        try {
            company.increaseSalaryByPercent(10);
            company.print();
        } catch (CompanyException e) {
            fail("Exception should not be thrown! ");
        }

    }

    @org.junit.jupiter.api.Test
    void serialization() {
        final String filename = "company.ser";
        try {
            company.serialize(filename);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
            fail("Exception during serializing object. ");
        }

        try {
            System.out.println("Company before serialization to file: ");
            System.out.println(company);
            Company c2 = Company.deserialize(filename);

            System.out.println("Company after de-serialization (from file): ");
            System.out.println(c2);

            assertEquals(company.numberOfEmployees(), c2.numberOfEmployees());

            List<Employee> l1 = company.getStaff();
            List<Employee> l2 = c2.getStaff();

            // assertIterableEquals(l1, l2);
            // This would not work, as objects might be
            // different w.r.t. their id. Furthermore
            // the equals operation on Employee is only defined
            // for first/last name and date of birth

            assertEquals(l1.size(), l2.size());
            System.out.println("Lists are of equal size!");
            for (int i=0; i<l1.size(); i++) {
                Employee e1 = l1.get(i);
                Employee e2 = l2.get(i);

                assert(e1.getFirstName().equals(e2.getFirstName()));
                assert(e1.getLastName().equals(e2.getLastName()));
                assert(e1.getEmail().equals(e2.getEmail()));
                assert(e1.getPhone().equals(e2.getPhone()));
                assert(e1.getDateOfBirth().equals(e2.getDateOfBirth()));
                assert(e1.getRole().equals(e2.getRole()));
                assert(e1.getSalary() == e2.getSalary());
                assert(e1.isSubcontractor() == e2.isSubcontractor());
                System.out.println(e1 + " EQUALS " + e2);

            }

        } catch (CompanyException e) {
            System.out.println(e.getMessage());
            fail("Exception occured during de-serialization ");
        }


    }

    /**
     * Currently this testcase checks the correct behavour in the case of valid files. The correct treatment
     * of error cases needs to be verified with further test cases.
     */
    @org.junit.jupiter.api.Test
    void fileHandling() {
        System.out.println(company.toCsvString());
        final String filename = "company.csv";
        try {
            company.writeToFile(filename);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
            fail("Error while writing to file. ");
        }

        try {
            System.out.println("Company before writing to file: ");
            System.out.println(company);

            Company c2 = Company.readFromFile(filename);

            System.out.println("Company after reading from file: ");
            System.out.println(c2);

            List<Employee> l1 = company.getStaff();
            List<Employee> l2 = c2.getStaff();

            // assertIterableEquals(l1, l2);
            // This would not work, as objects might be
            // different w.r.t. their id. Furthermore
            // the equals operation on Employee is only defined
            // for first/last name and date of birth

            assertEquals(l1.size(), l2.size());
            System.out.println("Lists are of equal size!");
            for (int i=0; i<l1.size(); i++) {
                Employee e1 = l1.get(i);
                Employee e2 = l2.get(i);
                assert(e1.getId().equals(e2.getId()));
                assert(e1.getFirstName().equals(e2.getFirstName()));
                assert(e1.getLastName().equals(e2.getLastName()));
                assert(e1.getEmail().equals(e2.getEmail()));
                assert(e1.getPhone().equals(e2.getPhone()));
                assert(e1.getDateOfBirth().equals(e2.getDateOfBirth()));
                assert(e1.getRole().equals(e2.getRole()));
                assert(e1.getSalary() == e2.getSalary());
                assert(e1.isSubcontractor() == e2.isSubcontractor());
                System.out.println(e1 + " EQUALS " + e2);

            }


        } catch (CompanyException e) {
            System.out.println(e.getMessage());
            fail("Error in testcase filehandling. ");
        }
    }

}