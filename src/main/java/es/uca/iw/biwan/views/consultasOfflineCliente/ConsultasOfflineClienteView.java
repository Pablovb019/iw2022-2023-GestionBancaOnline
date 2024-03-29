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
import es.uca.iw.biwan.domain.consulta.Offline;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.tipoConsulta.TipoConsulta;
import es.uca.iw.biwan.domain.usuarios.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@CssImport("./themes/biwan/consultasOfflineGestor.css")
@PageTitle("Consultas Offline")
@Route("consultas-offline-cliente")
public class ConsultasOfflineClienteView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConsultaService consultaService;

    private ArrayList<Offline> mensajesClienteGestor;

    private Cliente cliente;

    public ConsultasOfflineClienteView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
            if (session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Aceptar", event -> {
                    if (session.getAttribute(Gestor.class) != null){
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    }else if (session.getAttribute(EncargadoComunicaciones.class) != null){
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    } else if (session.getAttribute(Administrador.class) != null){
                        UI.getCurrent().navigate("pagina-principal-admin");
                    }
                });
                error.open();
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
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
        TituloVolverLayout.addClassName("TituloVolverLayoutOffline");
        VolverIcon.addClassName("VolverIcon");
        titulo.addClassName("TituloConsultasOffline");
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
        cliente = session.getAttribute(Cliente.class);

        ButtonSubmit.addClickShortcut(Key.ENTER);
        ButtonSubmit.addClickListener(submitEvent -> {
            if (!CajaMensaje.getValue().equals("")) {
                MessageListItem newMessage = new MessageListItem(CajaMensaje.getValue(), Instant.now(), cliente.getNombre());
                newMessage.setUserColorIndex(3);
                List<MessageListItem> items = new ArrayList<>(list.getItems());
                items.add(newMessage);
                list.setItems(items);
                Offline consulta = new Offline();
                consulta.setUUID(UUID.randomUUID());
                consulta.setFecha(LocalDateTime.now());
                consulta.setTipo(TipoConsulta.OFFLINE.toString());
                consulta.setTexto(CajaMensaje.getValue());
                consulta.setAutor(session.getAttribute(Cliente.class).getUUID());
                consulta.setCliente(session.getAttribute(Cliente.class));
                ArrayList<Usuario> gestores = usuarioService.findUsuarioByRol(Role.GESTOR.toString());
                for (Usuario gestor : gestores) {
                    if (gestor.getUUID().equals(cliente.getGestor_id())) {
                        consulta.setGestor(gestor);
                        break;
                    }
                }
                CreateRequest(consulta);
                CajaMensaje.setValue("");
            }
        });

        list.addClassName("list");
        MensajeSubmit.expand(CajaMensaje);
        MensajeSubmit.add(CajaMensaje, ButtonSubmit);
        VerticalLayout chatLayout = new VerticalLayout(list, MensajeSubmit);
        MensajeSubmit.addClassName("MensajeSubmit");
        ButtonSubmit.addClassName("ButtonSubmit");
        chatLayout.addClassName("chatLayout");
        chatLayout.expand(list);

        // Obtenemos los mensajes
        mensajesClienteGestor = consultaService.findMensajesClienteGestorOffline(TipoConsulta.OFFLINE.toString(), cliente.getUUID(), cliente.getGestor_id());
        ArrayList<Offline> mensajesOrdenados = new ArrayList<>();
        for (Offline mensaje : mensajesClienteGestor) {
            if (mensajesOrdenados.size() == 0) {
                mensajesOrdenados.add(mensaje);
            } else {
                for (int i = 0; i < mensajesOrdenados.size(); i++) {
                    if (mensaje.getFecha().isBefore(mensajesOrdenados.get(i).getFecha())) {
                        mensajesOrdenados.add(i, mensaje);
                        break;
                    } else if (i == mensajesOrdenados.size() - 1) {
                        mensajesOrdenados.add(mensaje);
                        break;
                    }
                }
            }
        }

        for (Offline mensaje : mensajesOrdenados) {
            if (mensaje.getAutor().equals(cliente.getUUID())) {
                MessageListItem newMessage = new MessageListItem(mensaje.getTexto(), mensaje.getFecha().toInstant(ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now())), cliente.getNombre());
                newMessage.setUserColorIndex(3);
                List<MessageListItem> items = new ArrayList<>(list.getItems());
                items.add(newMessage);
                list.setItems(items);
            } else {
                ArrayList<Usuario> gestores = usuarioService.findUsuarioByRol(Role.GESTOR.toString());
                for (Usuario gestor : gestores) {
                    if (mensaje.getAutor().equals(gestor.getUUID())) {
                        MessageListItem newMessage = new MessageListItem(mensaje.getTexto(), mensaje.getFecha().toInstant(ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now())), gestor.getNombre());
                        newMessage.setUserColorIndex(1);
                        List<MessageListItem> items = new ArrayList<>(list.getItems());
                        items.add(newMessage);
                        list.setItems(items);
                    }
                }
            }
        }

        return chatLayout;
    }

    private void CreateRequest(Offline consulta) {
        try {
            consultaService.saveOffline(consulta);
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }

    @PostConstruct
    public void init() {
        try{
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
        } catch (Exception ignored) {}
    }

    @Scheduled(fixedRate = 3000)
    public void reload() {
        ArrayList<Offline> mensajes = consultaService.findMensajesClienteGestorOffline(TipoConsulta.OFFLINE.toString(), cliente.getUUID(), cliente.getGestor_id());
        boolean hayNuevos = false;
        if (mensajesClienteGestor.size() != mensajes.size()) {
            for (Offline mensaje : mensajes) {
                if (mensaje.getAutor().equals(cliente.getGestor_id())) {
                    hayNuevos = true;
                    break;
                }
            }
        }

        if (hayNuevos) {
            getUI().ifPresent(ui -> ui.access(() -> {
                ui.getPage().reload();
            }));
        }
    }
}
