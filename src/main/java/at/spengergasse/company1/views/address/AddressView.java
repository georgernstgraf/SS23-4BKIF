package at.spengergasse.company1.views.address;

import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.nio.Buffer;

@PageTitle("AddressView")
@Route(value="address", layout = MainLayout.class)
public class AddressView extends VerticalLayout {
    private final TextField street = new TextField("Street");
    private final TextField number = new TextField("Number");
    private final TextField zip = new TextField("ZIP Code");
    private final TextField city = new TextField("City");

    private final Button btn = new Button("Go!");
    public AddressView() {
        initUI();
        initActions();
    }

    private void initUI() {
        add(street, number, zip, city, btn);
    }
    private void initActions() {
        btn.addClickListener(e -> showAddress());
    }

    private void showAddress() {
        Dialog d = new Dialog();
        d.add(new TextField(street.getValue() + " " + number.getValue() + ", " + zip.getValue() + " " + city.getValue()));
        d.open();
    }

}
