package at.spengergasse.company1.views.about;

import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    private static final String TEXT_DECIMAL = "Decimal";
    private static final String TEXT_HEXADECIMAL = "Hexadecimal";

    private static final String TEXT_BINARY = "Binary";

    private final TextField op1 = new TextField("Operand 1");
    private final TextField op2 = new TextField("Operand 2");
    private final Button btn = new Button("Calculate");

    private final Select<String> operation = new Select<>("Operation");
    private final TextField res = new TextField("Result");

    private RadioButtonGroup<String> radioGroup;


    public AboutView() {
        initUI();
        initActions();
    }

    private void initUI() {
        setSpacing(false);
        res.setEnabled(false);
        VerticalLayout ops = new VerticalLayout();
        operation.setItems("+", "-", "*", "/", "%");
        operation.setValue("+");


        ops.add(op1, op2);

        radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Radix");
        radioGroup.setItems(TEXT_DECIMAL, TEXT_BINARY, TEXT_HEXADECIMAL);
        radioGroup.setValue(TEXT_DECIMAL);

        add(radioGroup, ops, operation, btn, res);
    }

    private void initActions() {
        radioGroup.addValueChangeListener(e -> change());
        btn.addClickListener(e -> calculate());
    }

    private void change() {

    }

    private void calculate() {
        try {

            int radix = 10;
            if (radioGroup.getValue().equals(TEXT_BINARY)) {
                radix = 2;
            } else if (radioGroup.getValue().equals(TEXT_HEXADECIMAL)) {
                radix = 16;
            }
            int v1 = Integer.parseInt(op1.getValue(), radix);
            int v2 = Integer.parseInt(op2.getValue(), radix);

            int result = 0;

            String currentOp = operation.getValue();
            switch (currentOp) {
                case "+": result = v1 + v2; break;
                case "-": result = v1 - v2; break;
                case "*": result = v1 * v2; break;
                case "/": result = v1 / v2; break;
                case "%": result = v1 % v2; break;
                default: Notification.show("Invalid operation");
            }

            String strResult = String.valueOf(result);
            if (radix == 2) {
                strResult = Integer.toBinaryString(result);
            } else if (radix == 16) {
                strResult = Integer.toHexString(result);
            }
            res.setValue(strResult);
        } catch (NumberFormatException e) {
            Notification.show("Cannot convert one of the operands to a number of the given radix. " + e.getMessage());
            res.setValue("NaN");
        }
    }


}
