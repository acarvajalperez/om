package es.opplus.front.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.opplus.front.services.ClientService;
import es.opplus.front.services.notifications.BrowserNotifications;
import es.opplus.front.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Route(value = "", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./styles/views/helloworld/hello-world-view.css")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public HelloWorldView(@Autowired ClientService clientService) {
        addClassName("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        Button askPermission = new Button("Ask Permission");
        add(name, sayHello, askPermission);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
            //kafkaTemplate.send("es.opplus.front.message1", "message");
        });

        askPermission.addClickListener(listener -> {
            BrowserNotifications.extend(UI.getCurrent()).showNotificationWithBody("Esto es una prueba", "Este es el cuerpo", "images/logo.png");
            List<String> months = clientService.getMonths();
            months.forEach(s -> add(new Paragraph(s)));
        });
    }
}
