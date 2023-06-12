package at.spengergasse.company1.views.employees;

import at.spengergasse.company1.model.Company;
import at.spengergasse.company1.model.CompanyException;
import at.spengergasse.company1.model.Employee;
import at.spengergasse.company1.util.UIFactory;
import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route(value = "employee-form", layout = MainLayout.class)
public class EmployeeForm extends VerticalLayout {

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private EmailField email = new EmailField("Email");
    private TextField phone = new TextField("Phone");
    private DatePicker dateOfBirth = new DatePicker("Birthday");
    private ComboBox<Employee.Role> role = new ComboBox<>("Role");
    private TextField salary = new TextField("Salary");
    private Checkbox subcontractor = new Checkbox("Subcontractor");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private Employee employee;

    private Binder<Employee> binder = new Binder<>(Employee.class);

    public EmployeeForm() {
        initComponents();
        initActions();

        // vorruebergehend zum Entwickeln
        try {
            employee = Company.getInstance().getEmployee(0);
        } catch (CompanyException e) {
            Notification.show("Error on loading employee");
        }

        binder.bindInstanceFields(this);
        binder.readBean(this.employee);
    }

    private void initComponents() {
        FormLayout layout = new FormLayout();
        role.setItems(Employee.Role.values());
        layout.add(firstName, lastName, email,
                phone, dateOfBirth, role, salary, subcontractor);
        add(layout);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(save, cancel);
        add(buttonLayout);
    }


    private void initActions() {
        save.addClickListener(e -> saveClicked());
        cancel.addClickListener(e -> cancelClicked());
    }


    private void saveClicked() {
        try {
            binder.writeBean(this.employee);
        } catch (Exception e) {
            UIFactory.error("Error", e);
        }
    }

    private void cancelClicked() {
    }
}
