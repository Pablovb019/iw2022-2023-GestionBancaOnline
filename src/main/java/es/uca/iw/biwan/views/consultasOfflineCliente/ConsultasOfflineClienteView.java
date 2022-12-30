package es.uca.iw.biwan.views.consultasOfflineCliente;


import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.ConsultaService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.tipoConsulta.TipoConsulta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CssImport("./themes/biwan/consultasOfflineGestor.css")
@PageTitle("Consultas Offline")
@Route("consultas-offline-cliente")
public class ConsultasOfflineClienteView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConsultaService consultaService;

    public ConsultasOfflineClienteView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null) {
            if (!session.getAttribute(Cliente.class).getRol().contentEquals("CLIENTE")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                //NEW
                VerticalLayout layoutConsultaOffline = new VerticalLayout();
                VerticalLayout layoutConsultas = new VerticalLayout();

                //ADD CLASS NAME
                layoutConsultas.addClassName("layoutConsultas");

                //ADD
                layoutConsultas.add(ConsultasOffline());
                layoutConsultaOffline.add(HeaderUsuarioLogueadoView.Header(), layoutConsultas, FooterView.Footer());

                //ALIGNMENT
                layoutConsultaOffline.expand(layoutConsultas);
                layoutConsultaOffline.setSizeFull();

                add(layoutConsultaOffline);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private VerticalLayout ConsultasOffline() {

        //NEW
        VerticalLayout layoutConsultasOfflinePrincipal = new VerticalLayout();
        HorizontalLayout TituloVolverLayout = new HorizontalLayout();
        Anchor VolverAnchor = new Anchor("pagina-principal-cliente", "Volver");
        Icon VolverIcon = new Icon(VaadinIcon.ARROW_BACKWARD);
        H1 titulo = new H1("Consultas Offline");
        HorizontalLayout layoutHorConsultasOffline = new HorizontalLayout();

        //ADD CLASS NAME
        VolverAnchor.addClassName("VolverAnchor");
        TituloVolverLayout.addClassName("TituloVolverLayout");
        VolverIcon.addClassName("VolverIcon");
        titulo.addClassName("Titulo");
        layoutHorConsultasOffline.addClassName("layoutHorConsultasOffline");
        layoutConsultasOfflinePrincipal.addClassName("layoutConsultasOfflinePrincipal");

        //ALIGNMENT
        TituloVolverLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        //ADD
        VolverAnchor.add(VolverIcon);
        TituloVolverLayout.add(titulo, VolverAnchor);
        layoutHorConsultasOffline.add(ListaMensajesConsulta());
        layoutConsultasOfflinePrincipal.add(TituloVolverLayout, layoutHorConsultasOffline);

        return layoutConsultasOfflinePrincipal;
    }

    public VerticalLayout ListaMensajesConsulta() {
        HorizontalLayout MensajeSubmit = new HorizontalLayout();
        MessageList list = new MessageList();
        TextField CajaMensaje = new TextField("", "Mensaje");
        Button ButtonSubmit = new Button("Enviar");

        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre();

        ButtonSubmit.addClickShortcut(Key.ENTER);
        ButtonSubmit.addClickListener(submitEvent -> {
            if(!CajaMensaje.getValue().equals("")) {
                MessageListItem newMessage = new MessageListItem(CajaMensaje.getValue(), Instant.now(), nombre);
                newMessage.setUserColorIndex(3);
                List<MessageListItem> items = new ArrayList<>(list.getItems());
                items.add(newMessage);
                list.setItems(items);
                Consulta consulta = new Consulta();
                consulta.setUUID(UUID.randomUUID());
                consulta.setFecha(LocalDateTime.now());
                consulta.setTipo(TipoConsulta.OFFLINE.toString());
                consulta.setTexto(CajaMensaje.getValue());
                consulta.setAutor(session.getAttribute(Usuario.class).getUUID());
                consulta.setCliente(session.getAttribute(Usuario.class));
                ArrayList<Usuario> gestores = usuarioService.findUsuarioByRol(Role.GESTOR.toString());
                for(Usuario gestor : gestores) {
                    if(gestor.getUUID().equals(session.getAttribute(Usuario.class).getGestor_id())) {
                        consulta.setGestor(gestor);
                        break;
                    }
                }
                CreateRequest(consulta);
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

    private void CreateRequest(Consulta consulta) {
        try {
            consultaService.save(consulta);
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }
}
