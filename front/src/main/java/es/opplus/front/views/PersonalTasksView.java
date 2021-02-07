package es.opplus.front.views;

import es.opplus.front.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "personal-tasks", layout = MainView.class)
@PageTitle("Bandeja de entrada - Personal")
@CssImport("./styles/views/helloworld/hello-world-view.css")

public class PersonalTasksView extends HorizontalLayout {

    public PersonalTasksView() {
    }
}
