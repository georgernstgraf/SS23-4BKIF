package at.spengergasse.company1.views.employees;

import at.spengergasse.company1.model.Employee;
import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Employees Form")
@Route(value="employes-form", layout = MainLayout.class)
public class EmployeeFormView extends VerticalLayout {

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


    EmployeeFormView() {
        initUI();
        initActions();
    }

    private void initUI() {
        add(new H3("Personal information"));
        FormLayout form = new FormLayout();
        form.add(firstName, lastName, dateOfBirth, phone, email, role, salary, subcontractor);
        add(form);
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(cancel, save);
        add(hl);
    }


    private void initActions() {
        cancel.addClickListener(e -> cancelAction());
        save.addClickListener(e -> saveAction());
    }

    private void saveAction() {
    }

    private void cancelAction() {
    }

}
