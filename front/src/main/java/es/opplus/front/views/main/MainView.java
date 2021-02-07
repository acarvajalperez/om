package es.opplus.front.views.main;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.Registration;
import es.opplus.front.services.Broadcaster;
import es.opplus.front.services.notifications.BrowserNotifications;
import es.opplus.front.views.HelloWorldView;
import es.opplus.front.views.OperationsView;
import es.opplus.front.views.PenddingTasksView;
import es.opplus.front.views.PersonalTasksView;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/views/main/main-view.css")
@PWA(name = "Operation Manager", shortName = "OM", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;

    VerticalLayout messages = new VerticalLayout();
    Registration broadcasterRegistration;

    // Creating the UI shown separately

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        super.onAttach(attachEvent);

        // Ch
        //BrowserNotifications.extend(UI.getCurrent()).askForPermission();

        // Enable Push Notifications
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(newMessage -> {
            ui.access(() -> messages.add(new Span(newMessage)));
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {

        // Disable Push notifications
        broadcasterRegistration.remove();
        broadcasterRegistration = null;

        super.onDetach(detachEvent);
    }

    public MainView() {
        menu = new Tabs();
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        createMenu();
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(new Avatar("Antonio Carvajal"));
        return layout;
    }

    private Component createDrawerContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "OM logo"));
        logoLayout.add(new H1("OM"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private void createMenu() {
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        menu.setId("tabs");
        menu.add(createMenuItems());
        menu.addSelectedChangeListener(listener -> {
            Tab tab = listener.getSelectedTab();
            if (tab != null)
                UI.getCurrent().navigate(ComponentUtil.getData(tab, Class.class));
        });
    }

    private Component[] createMenuItems() {
        return new Tab[]{
                createTab(FontAwesome.Solid.INBOX.create(), "Pendientes", new Label("132"), PenddingTasksView.class),
                createTab(FontAwesome.Solid.INBOX.create(), "Personal", new Label("132"), PersonalTasksView.class),
                createTab(FontAwesome.Solid.CHART_AREA.create(), "Informes", new Label("132"), HelloWorldView.class),
                createTab(FontAwesome.Solid.INBOX.create(), "Operaciones", new Label("132"), OperationsView.class)
        };
    }

    private static Tab createTab(IronIcon icon, String text, Component sufix, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();

        icon.setSize("28px");

        Label tabText = new Label(text);
        tabText.setWidthFull();

        tab.add(icon, tabText, sufix);
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        Optional<Tab> tab = getTabForComponent(getContent());
        if (tab.isPresent())
            menu.setSelectedTab(tab.get());
        else
            menu.setSelectedTab(null);

        viewTitle.setText(getCurrentPageTitle());
        BrowserNotifications.extend(UI.getCurrent()).askForPermission();
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
