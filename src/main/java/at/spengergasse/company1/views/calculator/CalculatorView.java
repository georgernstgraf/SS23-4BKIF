package at.spengergasse.company1.views.calculator;

import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Calculator")
@Route(value = "calc", layout = MainLayout.class)
public class CalculatorView extends VerticalLayout {

    private TextField operand1 = new TextField("Operand 1");
    private TextField operand2 = new TextField("Operand 2");
    private Button btnCalc = new Button("Calculate");
    private TextField result = new TextField("Result");

    private Select<String> operation = new Select<>("+", "-", "*", "/");

    private int lastRadix = 10;

    RadioButtonGroup<String> radix = new RadioButtonGroup<>();

    public CalculatorView() {
        initComponents();
        initActions();
    }

    private void initComponents() {
        result.setEnabled(false);
        operation.setValue("+");
        radix.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radix.setLabel("Radix");
        radix.setItems("Decimal", "Binary", "Hexadecimal");
        radix.setValue("Decimal");
        add(operand1, operand2, operation, radix, btnCalc, result);
    }

    private void initActions() {
        btnCalc.addClickListener(e -> calc());
        radix.addValueChangeListener(e -> radixChanged());
    }

    private void radixChanged() {
        System.out.println("Radix Changed!");
        int a, b, res;

        // quick and dirty hack
        if (operand1.getValue() == null || operand1.getValue().isEmpty() || operand1.getValue().isBlank()) {
            operand1.setValue("0");
        }

        if (operand2.getValue() == null || operand2.getValue().isBlank() || operand2.getValue().isEmpty()) {
            operand2.setValue("0");
        }

        if (result.getValue() == null || result.getValue().isBlank() || result.getValue().isEmpty()) {
            result.setValue("0");
        }


        try {
            a = Integer.parseInt(operand1.getValue(), lastRadix);
            b = Integer.parseInt(operand2.getValue(), lastRadix);
            res = Integer.parseInt(result.getValue(), lastRadix);

            System.out.println("a: " + a + ", b: " + b + ", res: " + res + ", radix value: " + radix.getValue());
            int rad = 10;
            if (radix.getValue().equals("Binary")) {
                rad = 2;
            } else if (radix.getValue().equals("Hexadecimal")) {
                rad = 16;
            }
            System.out.println("rad: " + rad);
            if (rad == 10) {
                operand1.setValue(String.valueOf(a));
                operand2.setValue(String.valueOf(b));
                result.setValue(String.valueOf(res));
            } else if (rad == 2) {
                operand1.setValue(Integer.toBinaryString(a));
                operand2.setValue(Integer.toBinaryString(b));
                result.setValue(Integer.toBinaryString(res));
            } else if (rad == 16) {
                operand1.setValue(Integer.toHexString(a));
                operand2.setValue(Integer.toHexString(b));
                result.setValue(Integer.toHexString(res));
            }
        } catch (NumberFormatException e) {
            Notification.show("Error: " + e.getMessage());
        }

        // etwas unschoen, da duplizierter Code.
        // In diesem Fall aber notwendig, da oben Exception
        // auftreten kann.
        int rad = 10;
        if (radix.getValue().equals("Binary")) {
            rad = 2;
        } else if (radix.getValue().equals("Hexadecimal")) {
            rad = 16;
        }
        // Wichtig: in dieser Methode muss "lastRadix" unbedingt
        // gesetzt werden!
        lastRadix = rad;
    }

    private void calc() {
        try {
            int rad = 10;
            if (radix.getValue().equals("Binary")) {
                rad = 2;
            } else if (radix.getValue().equals("Hexadecimal")) {
                rad = 16;
            }
            int a = Integer.parseInt(operand1.getValue(), rad);
            int b = Integer.parseInt(operand2.getValue(), rad);
            int res = 0;
            if (operation.getValue().equals("+")) {
                res = a + b;
            } else if (operation.getValue().equals("-")) {
                res = a - b;
            } else if (operation.getValue().equals("*")) {
                res = a * b;
            } else if (operation.getValue().equals("/")) {
                res = a / b;
            }

            String strResult = "";
            if (rad == 10) {
                strResult = String.valueOf(res);
            } else if (rad == 2) {
                strResult = Integer.toBinaryString(res);
            } else if (rad == 16) {
                strResult = Integer.toHexString(res);
            }
            result.setValue(strResult);
        } catch (NumberFormatException e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

}
