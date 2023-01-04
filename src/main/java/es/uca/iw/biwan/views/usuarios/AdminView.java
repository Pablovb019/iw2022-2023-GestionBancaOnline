package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@CssImport("./themes/biwan/paginaPrincipalAdmin.css")
@PageTitle("Página Principal Administrador")
@Route("pagina-principal-admin")
public class AdminView extends VerticalLayout {
    public AdminView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
            if (session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null){
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un administrador", "Aceptar", event -> {
                    if (session.getAttribute(Cliente.class) != null){
                        UI.getCurrent().navigate("pagina-principal-cliente");
                    }else if (session.getAttribute(Gestor.class) != null){
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    } else if (session.getAttribute(EncargadoComunicaciones.class) != null){
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    }
                });
                error.open();
            } else {
                //NEW
                VerticalLayout layoutGestor = new VerticalLayout();
                VerticalLayout layoutOperacionesAdmin = new VerticalLayout();
                layoutOperacionesAdmin.add(ConsultasAdmin());

                //ADD
                layoutGestor.add(HeaderUsuarioLogueadoView.Header(), layoutOperacionesAdmin, FooterView.Footer());

                //ALIGNMENT
                layoutGestor.expand(layoutOperacionesAdmin);
                layoutGestor.setAlignItems(Alignment.CENTER);
                layoutGestor.setSizeFull();

                add(layoutGestor);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    private VerticalLayout ConsultasAdmin() {

        //NEW
        VerticalLayout layoutVerAdminPrincipal = new VerticalLayout();
        VerticalLayout layoutVerAdminTabla = new VerticalLayout();
        HorizontalLayout layoutComponenteTabla = new HorizontalLayout();
        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Administrador.class).getNombre();
        H1 Titulo = new H1("Bienvenido Administrador: " + nombre);
        Anchor CrearGestor = new Anchor("crear-gestor", "Crear Gestor");
        Anchor CrearEncargadoComunicacion = new Anchor("crear-encargado-comunicacion", "Crear Encargado Comunicación");



        //ADD CLASS
        Titulo.addClassName("Titulo");
        CrearGestor.addClassNames("SeparacionAdmin", "BotonAdmin");
        CrearEncargadoComunicacion.addClassNames("SeparacionAdmin", "BotonAdmin");
        layoutComponenteTabla.addClassName("layoutGestionAdmin");
        layoutVerAdminPrincipal.addClassName("layoutVerAdminPrincipal");


        //ADD
        layoutComponenteTabla.add(CrearGestor, CrearEncargadoComunicacion);
        layoutVerAdminTabla.add(layoutComponenteTabla);
        layoutVerAdminPrincipal.add(Titulo, layoutVerAdminTabla);

        return  layoutVerAdminPrincipal;
    }
}
