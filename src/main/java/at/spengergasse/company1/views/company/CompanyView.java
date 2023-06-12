package at.spengergasse.company1.views.company;


import at.spengergasse.company1.model.Company;
import at.spengergasse.company1.model.CompanyException;
import at.spengergasse.company1.util.UIFactory;
import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jdk.jfr.Registered;
import org.springframework.aop.scope.ScopedProxyUtils;

import java.io.IOException;
import java.io.InputStream;

@PageTitle("Company")
@Route(value = "company", layout = MainLayout.class)
public class CompanyView extends VerticalLayout {

    private final TextField companyName = new TextField();
    private final TextField companyAddress = new TextField();

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final Button saveCompanyToFile = new Button("Save to file");

    private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private final Upload readCompanyFromFile;

    private final Binder<Company> binder = new Binder<>(Company.class);

    CompanyView() {
        readCompanyFromFile = new Upload(buffer);
        initUI();
        initActions();
        binder.bindInstanceFields(this);
        binder.readBean(Company.getInstance());
    }


    private void initUI() {
        add(companyName);
        add(companyAddress);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(saveButton, cancelButton);
        add(buttonLayout);


        VerticalLayout vl = new VerticalLayout();
        vl.add(saveCompanyToFile, readCompanyFromFile);
        Details details = new Details("Save/Load Company", vl);

        add(details);
    }

    private void initActions() {
        saveButton.addClickListener(e -> save());
        cancelButton.addClickListener(e -> cancel());
        saveCompanyToFile.addClickListener(e -> saveCompany());
        readCompanyFromFile.addSucceededListener(succeedEvent -> readCompany(succeedEvent));
    }

    private void readCompany(SucceededEvent succeededEvent) {
        if (succeededEvent != null) {
            String filename = succeededEvent.getFileName();
            try {
                InputStream is = buffer.getInputStream(filename);
                Company.loadFromFile(is);
            } catch (CompanyException e) {
                UIFactory.error("Error loading file", e);
            }
            UIFactory.showNotification("File Upload succeeded");
        } else {
            UIFactory.showError(new CompanyException("Fehler beim File-Upload"));
        }
    }

    private void saveCompany() {
    }


    private void save() {
        try {
            System.out.println("Saving company...");
            binder.writeBean(Company.getInstance());
            System.out.println("Company: " + Company.getInstance());
        } catch (Exception e) {
            UIFactory.error("Error on saving company", e);
        }
    }

    private void cancel() {
        binder.readBean(Company.getInstance());
    }
}
