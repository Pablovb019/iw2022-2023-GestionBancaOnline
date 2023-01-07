package es.uca.iw.biwan.views.consultasOnlineCliente;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInputI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.ConsultaService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.consulta.Online;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.tipoConsulta.TipoConsulta;
import es.uca.iw.biwan.domain.usuarios.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@CssImport("./themes/biwan/consultasOnlineGestor.css")
@PageTitle("Consultas Online")
@Route("consultas-online-cliente")
public class ConsultasOnlineClienteView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ConsultaService consultaService;
    private UserInfo userInfo;

    public ConsultasOnlineClienteView() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
            if (session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Aceptar", event -> {
                    if (session.getAttribute(Gestor.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    } else if (session.getAttribute(EncargadoComunicaciones.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    } else if (session.getAttribute(Administrador.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-admin");
                    }
                });
                error.open();
            } else {
                Component enterLayout = createEnterLayout();
                add(HeaderUsuarioLogueadoView.Header(), enterLayout, FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    public VerticalLayout createEnterLayout() {
        VerticalLayout enterlayout = new VerticalLayout();
        VaadinSession session = VaadinSession.getCurrent();
        Cliente cliente = session.getAttribute(Cliente.class);
        H1 titulo = new H1("Consultas Online");
        Button enter = new Button("Enviar solicitud de consulta online");
        Button salir = new Button("Volver a la página principal");

        enter.addClickListener(event -> {
            userInfo = new UserInfo(cliente.getEmail(), cliente.getNombre());
            Component chatLayout = ConsultasOnline();
            ConsultasOnlineClienteView.this.replace(enterlayout, chatLayout);
        });

        salir.addClickListener(e -> {
            if (session.getAttribute(Cliente.class) != null) {
                UI.getCurrent().navigate("pagina-principal-cliente");
            } else {
                ConfirmDialog error = new ConfirmDialog("Error", "El usuario no esta logueado", "Aceptar", null);
                error.open();
                UI.getCurrent().navigate("");
            }
        });

        salir.addClassName("salirButton");
        enter.addClassName("enterButton");
        titulo.addClassName("tituloConsultasOnline");
        enterlayout.add(titulo, enter, salir);
        enterlayout.addClassName("enterLayout");
        enterlayout.setAlignItems(Alignment.CENTER);
        enterlayout.setHeightFull();
        return enterlayout;
    }

    private VerticalLayout ConsultasOnline() {
        //NEW
        VerticalLayout layoutConsultasOnlinePrincipal = new VerticalLayout();
        HorizontalLayout TituloVolverLayout = new HorizontalLayout();
        Anchor VolverAnchor = new Anchor("pagina-principal-cliente", "Volver");
        Icon VolverIcon = new Icon(VaadinIcon.ARROW_BACKWARD);
        H1 titulo = new H1("Consultas Online");
        HorizontalLayout layoutHorConsultasOnline = new HorizontalLayout();

        //ADD CLASS NAME
        VolverAnchor.addClassName("VolverAnchor");
        TituloVolverLayout.addClassName("TituloVolverLayout");
        VolverIcon.addClassName("VolverIcon");
        titulo.addClassName("TituloConsultasOnline");
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

    public VerticalLayout ListaMensajesConsulta() {
        VerticalLayout chatLayout = new VerticalLayout();

        VaadinSession session = VaadinSession.getCurrent();
        Cliente cliente = session.getAttribute(Cliente.class);

        Online consulta = new Online();
        consulta.setCliente(cliente);
        consulta.setTipo(TipoConsulta.ONLINE.toString());
        consulta.setUUID(UUID.randomUUID());
        consulta.setSala(UUID.randomUUID());
        consulta.setFecha(LocalDateTime.now());
        ArrayList<Usuario> gestores = usuarioService.findUsuarioByRol(Role.GESTOR.toString());
        for (Usuario gestor : gestores) {
            if (gestor.getUUID().equals(cliente.getGestor_id())) {
                consulta.setGestor(gestor);
                break;
            }
        }
        CreateRequest(consulta);

        CollaborationAvatarGroup avatars = new CollaborationAvatarGroup(userInfo, consulta.getSala().toString());

        CollaborationMessageList list = new CollaborationMessageList(userInfo, consulta.getSala().toString());
        CollaborationMessageInput input = new CollaborationMessageInput(list);
        input.setI18n(new MessageInputI18n().setSend("Enviar").setMessage("Mensaje"));

        list.addClassName("list");
        chatLayout.addClassName("chatLayout");
        HorizontalLayout conectados = new HorizontalLayout();
        H3 conectadosH3 = new H3("Conectados: ");
        conectadosH3.addClassName("conectadosH3");
        conectados.add(conectadosH3, avatars);
        conectados.addClassName("conectados");
        chatLayout.add(conectados, list, input);
        chatLayout.expand(list);
        chatLayout.setSizeFull();

        return chatLayout;
    }

    private void CreateRequest(Online consulta) {
        try {
            consultaService.saveOnline(consulta);
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }
}
