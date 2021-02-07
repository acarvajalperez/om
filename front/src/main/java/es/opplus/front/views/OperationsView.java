package es.opplus.front.views;

import es.opplus.front.views.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "operations", layout = MainView.class)
@PageTitle("Operaciones")
@CssImport("./styles/views/helloworld/hello-world-view.css")

public class OperationsView extends HorizontalLayout {

    public OperationsView() {
        add(new Button("Tarea", listener -> {
            UI.getCurrent().navigate(TaskView.class);
        }));
    }
}
