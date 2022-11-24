package es.uca.iw.biwan.views.consultaOnline;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.domain.usuario.Cliente;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderClienteView;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@CssImport("/themes/biwan/consultasOnline.css")
@PageTitle("Consultas Online")
@Route("consultas-online")
public class ConsultasOnlineView extends VerticalLayout {

    public ConsultasOnlineView() {

        //NEW
        VerticalLayout layoutConsultaOnline = new VerticalLayout();
        VerticalLayout layoutConsultas = new VerticalLayout();

        //ADD CLASS NAME
        layoutConsultas.addClassName("layoutConsultas");

        //ADD
        layoutConsultas.add(ConsultasOnline());
        layoutConsultaOnline.add(HeaderClienteView.Header(), layoutConsultas, FooterView.Footer());

        //ALIGNMENT
        layoutConsultaOnline.expand(layoutConsultas);
        layoutConsultaOnline.setSizeFull();

        add(layoutConsultaOnline);
    }

    private VerticalLayout ConsultasOnline() {

        //NEW
        VerticalLayout layoutConsultasOnlinePrincipal = new VerticalLayout();
        HorizontalLayout TituloVolverLayout = new HorizontalLayout();
        Anchor VolverAnchor = new Anchor("pagina-principal-gestor", "Volver");
        Icon VolverIcon = new Icon(VaadinIcon.ARROW_BACKWARD);
        H1 titulo = new H1("Consultas Online");
        HorizontalLayout layoutHorConsultasOnline = new HorizontalLayout();
        VerticalLayout layoutVerConsultasOnlineIzda = new VerticalLayout();
        H4 NombreClienteConsultas = new H4("Nombre Cliente Seleccionado");
        Grid<Cliente> Consultas = new Grid<>();

        //ADD CLASS NAME
        VolverAnchor.addClassName("VolverAnchor");
        TituloVolverLayout.addClassName("TituloVolverLayout");
        VolverIcon.addClassName("VolverIcon");
        titulo.addClassName("Titulo");
        layoutHorConsultasOnline.addClassName("layoutHorConsultasOnline");
        layoutVerConsultasOnlineIzda.addClassName("layoutVerConsultasOnlineIzda");
        NombreClienteConsultas.addClassName("NombreClienteConsultas");
        Consultas.addClassName("Consultas");

        //ALIGNMENT
        TituloVolverLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        //ADD
        VolverAnchor.add(VolverIcon);
        TituloVolverLayout.add(titulo, VolverAnchor);
        layoutVerConsultasOnlineIzda.add(NombreClienteConsultas, Consultas);
        layoutHorConsultasOnline.add(layoutVerConsultasOnlineIzda, ListaMensajesConsulta());
        layoutConsultasOnlinePrincipal.add(TituloVolverLayout, layoutHorConsultasOnline);

        return layoutConsultasOnlinePrincipal;
    }

    public static VerticalLayout ListaMensajesConsulta() {
        MessageList list = new MessageList();
        MessageInput input = new MessageInput();
        input.addSubmitListener(submitEvent -> {
            MessageListItem newMessage = new MessageListItem(
                    submitEvent.getValue(), Instant.now(), "Gestor");
            newMessage.setUserColorIndex(3);
            List<MessageListItem> items = new ArrayList<>(list.getItems());
            items.add(newMessage);
            list.setItems(items);
        });

        //Cliente cliente = DataService.getPeople(1).get(0);
        MessageListItem message1 = new MessageListItem("Tengo una consulta.",
                LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC), "Matt Mambo");
        message1.setUserColorIndex(1);
        /*MessageListItem message2 = new MessageListItem(
                "Using your talent, hobby or profession in a way that makes you contribute with something good to this world is truly the way to go.",
                LocalDateTime.now().minusMinutes(55).toInstant(ZoneOffset.UTC),
                "Linsey Listy");
        message2.setUserColorIndex(2);*/
        list.setItems(message1);

        list.addClassName("list");
        VerticalLayout chatLayout = new VerticalLayout(list, input);
        chatLayout.addClassName("chatLayout");
        chatLayout.expand(list);

        return chatLayout;
    }
}
