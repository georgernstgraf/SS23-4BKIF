package at.spengergasse.company1.views.matrix;

import at.spengergasse.company1.components.MatrixButton;
import at.spengergasse.company1.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("MatrixView")
@Route(value = "mv", layout = MainLayout.class)
public class MatrixView extends VerticalLayout {

    private static final int N = 15;

    private TextField text = new TextField();

    public MatrixView() {
        initUI();
    }

    private void initUI() {
        VerticalLayout vl = new VerticalLayout();
        // vl.setPadding(false);
        vl.setSpacing(false);

        for (int i=0; i<N; i++) {
            HorizontalLayout hl = new HorizontalLayout();
            // hl.setPadding(false);
            // hl.setSpacing(false);
            for (int j=0; j<N; j++) {
                MatrixButton btn = new MatrixButton(i, j);
                btn.addClickListener(e -> btnClicked(btn));
                hl.add(btn);
                if (i == j) {
                    btn.setEnabled(false);
                }
            }
            vl.add(hl);
        }
        add(vl);
        text.setEnabled(false);
        text.setWidthFull();
        add(text);
    }

    private void btnClicked(MatrixButton btn) {
        // System.out.println("Clicked:" + btn);
        text.setValue(btn.toString());
    }


}
