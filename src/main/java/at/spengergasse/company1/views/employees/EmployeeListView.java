package at.spengergasse.company1.views.employees;


import at.spengergasse.company1.model.Company;
import at.spengergasse.company1.model.Employee;
import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

@Route(value = "employee-list", layout = MainLayout.class)
public class EmployeeListView extends VerticalLayout {

    private Grid<Employee> grid;

    public EmployeeListView() {
        initComponents();
    }

    private void initComponents() {
        grid = new Grid<>(Employee.class, false);
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("role").setAutoWidth(true);
        grid.addColumn("salary").setAutoWidth(true);
        grid.addColumn("subcontractor").setAutoWidth(true);
        add(grid);
        grid.setItems(Company.getInstance().getStaff());
    }
}
