package at.spengergasse.company1.views.employees;

import at.spengergasse.company1.model.Company;
import at.spengergasse.company1.model.CompanyException;
import at.spengergasse.company1.model.Employee;
import at.spengergasse.company1.util.UIFactory;
import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import at.spengergasse.company1.views.employees.EmployeeFormView;

@PageTitle("Employees (List)")
@Route(value = "employees-list", layout = MainLayout.class)
public class EmployeesList extends Div {

    private Grid<Employee> grid;

    public EmployeesList() {
        initUI();
    }

    private void initUI() {
        grid = new Grid<>(Employee.class, false);

        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("role").setAutoWidth(true);
        grid.addColumn("salary").setAutoWidth(true);
        grid.addColumn("subcontractor").setAutoWidth(true);

        grid.addComponentColumn(
                employee -> {
                    HorizontalLayout actionLayout = new HorizontalLayout();
                    Button editButton = new Button(new Icon(VaadinIcon.EDIT));
                    editButton.addThemeVariants(ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_TERTIARY_INLINE,
                            ButtonVariant.LUMO_SUCCESS);
                    editButton.addClickListener(e -> edit(employee));
                    actionLayout.add(editButton);
                    Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
                    deleteButton.addThemeVariants(ButtonVariant.LUMO_SMALL,
                            ButtonVariant.LUMO_TERTIARY_INLINE,
                            ButtonVariant.LUMO_ERROR);
                    actionLayout.add(deleteButton);
                    deleteButton.addClickListener(e -> delete(employee));
                    return actionLayout;
                }
        ).setHeader("Fire")
                .setWidth("100px")
                .setTextAlign(ColumnTextAlign.END)
                .setFrozenToEnd(true)
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        add(grid);
        refreshGrid();
    }

    private void edit(Employee employee) {
       Long id = employee.getId();
       getUI().ifPresent(ui -> ui.navigate(at.spengergasse.company1.views.employees.EmployeeFormView.class, id));
    }

    private void delete(Employee employee) {
        Notification.show("Employee to delete: " + employee);
        try {
            Company.getInstance().fire(employee);
            // Company.getInstance().print();
            UIFactory.showNotification("Employee " + employee + " fired from the company.");
            refreshGrid();
        } catch (CompanyException e) {
            UIFactory.showNotification("Error: " + e.getMessage());
        }
    }

    private void refreshGrid() {
        grid.setItems(Company.getInstance().getStaff());
    }

    public static class EmployeeFormView {
    }
}
