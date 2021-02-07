package es.opplus.front.views;

import es.opplus.front.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "task", layout = MainView.class)
@PageTitle("Tarea")
@CssImport("./styles/views/helloworld/hello-world-view.css")

public class TaskView extends HorizontalLayout {

    public TaskView() {
    }
}
