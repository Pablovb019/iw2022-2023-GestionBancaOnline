package es.uca.iw.biwan.views.consultasOnlineGestor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@CssImport("./themes/biwan/consultasOnlineGestor.css")
@PageTitle("Consultas Online")
@Route("consultas-online-gestor")
public class ConsultasOnlineGestorView extends VerticalLayout {

    public ConsultasOnlineGestorView() {
        //NEW
        VerticalLayout layoutConsultaOnline = new VerticalLayout();
        VerticalLayout layoutConsultas = new VerticalLayout();

        //ADD CLASS NAME
        layoutConsultas.addClassName("layoutConsultas");

        //ADD
        layoutConsultas.add(ConsultasOnline());
        layoutConsultaOnline.add(HeaderUsuarioLogueadoView.Header(), layoutConsultas, FooterView.Footer());

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

        //ADD CLASS NAME
        VolverAnchor.addClassName("VolverAnchor");
        TituloVolverLayout.addClassName("TituloVolverLayout");
        VolverIcon.addClassName("VolverIcon");
        titulo.addClassName("Titulo");
        layoutHorConsultasOnline.addClassName("layoutHorConsultasOnline");
        layoutConsultasOnlinePrincipal.addClassName("layoutConsultasOnlinePrincipal");

        //ALIGNMENT
        TituloVolverLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        //ADD
        VolverAnchor.add(VolverIcon);
        TituloVolverLayout.add(titulo, VolverAnchor);
        layoutHorConsultasOnline.add(ListaMensajesConsulta());
        layoutConsultasOnlinePrincipal.add(TituloVolverLayout, layoutHorConsultasOnline);

        return layoutConsultasOnlinePrincipal;
    }

    public static VerticalLayout ListaMensajesConsulta() {
        HorizontalLayout MensajeSubmit = new HorizontalLayout();
        MessageList list = new MessageList();
        TextField CajaMensaje = new TextField("", "Mensaje");
        Button ButtonSubmit = new Button("Enviar");
        ButtonSubmit.addClickShortcut(Key.ENTER);
        ButtonSubmit.addClickListener(submitEvent -> {
            if(!CajaMensaje.getValue().equals("")) {
                MessageListItem newMessage = new MessageListItem(CajaMensaje.getValue(), Instant.now(), "Gestor");
                newMessage.setUserColorIndex(3);
                List<MessageListItem> items = new ArrayList<>(list.getItems());
                items.add(newMessage);
                list.setItems(items);
                CajaMensaje.setValue("");
            }
        });

        MessageListItem Presentacion = new MessageListItem("Escriba su consulta, le atenderé lo antes posible.",
                LocalDateTime.now().toInstant(ZoneOffset.UTC).minus(1, ChronoUnit.HOURS), "Gestor");
        Presentacion.setUserColorIndex(1);
        list.setItems(Presentacion);

        list.addClassName("list");
        MensajeSubmit.expand(CajaMensaje);
        MensajeSubmit.add(CajaMensaje, ButtonSubmit);
        VerticalLayout chatLayout = new VerticalLayout(list, MensajeSubmit);
        MensajeSubmit.addClassName("MensajeSubmit");
        ButtonSubmit.addClassName("ButtonSubmit");
        chatLayout.addClassName("chatLayout");
        chatLayout.expand(list);

        return chatLayout;
    }

}
