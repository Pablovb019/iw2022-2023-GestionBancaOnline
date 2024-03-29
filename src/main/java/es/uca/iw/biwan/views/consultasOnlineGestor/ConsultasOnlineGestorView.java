package es.uca.iw.biwan.views.consultasOnlineGestor;

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
import es.uca.iw.biwan.domain.usuarios.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.util.UUID;

@CssImport("./themes/biwan/consultasOnlineGestor.css")
@PageTitle("Consultas Online")
@Route("consultas-online-gestor")
public class ConsultasOnlineGestorView extends VerticalLayout {

    private UserInfo userInfo;

    public static Cliente cliente;

    public static UUID idSala;

    public ConsultasOnlineGestorView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
            if (session.getAttribute(Cliente.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un gestor", "Aceptar", event -> {
                    if (session.getAttribute(Cliente.class) != null){
                        UI.getCurrent().navigate("pagina-principal-cliente");
                    }else if (session.getAttribute(EncargadoComunicaciones.class) != null){
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    } else if (session.getAttribute(Administrador.class) != null){
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
        Gestor gestor = session.getAttribute(Gestor.class);
        H1 titulo = new H1("Consultas Online");
        Button enter = new Button("Entrar a la consulta online");
        Button salir = new Button("Volver a la página principal");

        enter.addClickListener(event -> {
            userInfo = new UserInfo(gestor.getEmail(), gestor.getNombre());
            Component chatLayout = ConsultasOnline();
            ConsultasOnlineGestorView.this.replace(enterlayout, chatLayout);
        });

        salir.addClickListener(e -> {
            if(session.getAttribute(Gestor.class) != null) { UI.getCurrent().navigate("pagina-principal-gestor"); }
            else {
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
        Anchor VolverAnchor = new Anchor("pagina-principal-gestor", "Volver");
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
        CollaborationAvatarGroup avatars = new CollaborationAvatarGroup(userInfo, idSala.toString());

        CollaborationMessageList list = new CollaborationMessageList(userInfo, idSala.toString());
        CollaborationMessageInput input = new CollaborationMessageInput(list);
        input.setI18n(new MessageInputI18n().setSend("Enviar").setMessage("Mensaje"));

        list.addClassName("list");
        chatLayout.addClassName("chatLayout");
        HorizontalLayout conectados = new HorizontalLayout();
        H3 conectadosH3 = new H3("Conectados: ");
        conectadosH3.addClassName("conectadosH3");
        conectados.add(conectadosH3 , avatars);
        conectados.addClassName("conectados");
        chatLayout.add(conectados, list, input);
        chatLayout.expand(list);
        chatLayout.setSizeFull();

        return chatLayout;
    }

}
