package at.spengergasse.company1.components;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class MatrixButton extends Button {

    private int row, col;
    private boolean state;

    public MatrixButton(int row, int col) {
        this(row, col, false); // ctor chaining
    }

    public MatrixButton(int row, int col, boolean state)  {
        if (row < 0) throw new IllegalArgumentException("Invalid row provided for MatrixButton: " + row);
        if (col < 0) throw new IllegalArgumentException("Invalid column provided for MatrixButton: " + col);
        this.setIcon(new Icon(VaadinIcon.CIRCLE_THIN));
        // setWidth("1px");
        // setWidth(1, Unit.POINTS);
        // setMaxWidth(1, Unit.POINTS);
        addThemeVariants(ButtonVariant.LUMO_SMALL);
        this.row = row;
        this.col = col;
        this.state = state;

        addClickListener(e -> myClick());
    }

    public boolean getState() {
        return state;
    }

    public void myClick() {
        // System.out.println("Click");
        this.state = !this.state;
        if (this.state) {
            this.setIcon(new Icon(VaadinIcon.PLUS));
        } else {
            this.setIcon(new Icon(VaadinIcon.CIRCLE_THIN));
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public String toString() {
        return "MatrixButton{" +
                "row=" + row +
                ", col=" + col +
                ", state=" + state +
                '}';
    }
}
