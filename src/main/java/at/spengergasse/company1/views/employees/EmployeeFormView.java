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
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import org.w3c.dom.events.UIEvent;

@PageTitle("Employees Form")
@Route(value="employees-form", layout = MainLayout.class)
public class EmployeeFormView extends VerticalLayout implements HasUrlParameter<Long> {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email address");
    private TextField phone = new TextField("Phone number");
    private DatePicker dateOfBirth = new DatePicker("Birthday");
    private ComboBox<Employee.Role> role = new ComboBox<>("Role");
    private TextField salary = new TextField("Salary");
    private Checkbox subcontractor = new Checkbox("Subcontractor");
    
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Employee employee;

    private Binder<Employee> binder = new Binder<>(Employee.class);

    EmployeeFormView() {
        initUI();
        initActions();
    }


    private void initUI() {
        add(new H3("Personal information"));
        FormLayout form = new FormLayout();
        role.setItems(Employee.Role.values());
        form.add(firstName, lastName, dateOfBirth, phone, email, role, salary, subcontractor);
        add(form);
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(cancel, save);
        add(hl);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long empId) {
        System.out.println("PARAMETER: " + empId);
        loadEmployee(empId);
    }

    private void loadEmployee(Long empId) {
        try {
            if (empId != null) {
                employee = Company.getInstance().getEmployee(empId);
                save.setText("Save");
            } else {
                employee = new Employee();
                save.setText("Create");
            }
            System.out.println(employee);

            binder.bindInstanceFields(this);
            binder.readBean(employee);

        } catch (CompanyException e) {
            UIFactory.error("Could not load employee.", e);
        }
    }

    private void initActions() {
        cancel.addClickListener(e -> cancelAction());
        save.addClickListener(e -> saveAction());
    }

    private void saveAction() {
        try {
            binder.writeBean(this.employee);
            System.out.println("Employee: " + this.employee);
            if (this.employee.getId() == null) { // create new employee
                Company.getInstance().hire(this.employee);
                UIFactory.showNotification("Employee created and hired!");
            } else { // edit existing employee
                UIFactory.showNotification("Employee updated!");
            }
        } catch (ValidationException e) {
            UIFactory.error("Validation error:", e);
        } catch (Exception e) {
            UIFactory.error("Error: ", e);
        }

    }

    private void cancelAction() {
    }


}
