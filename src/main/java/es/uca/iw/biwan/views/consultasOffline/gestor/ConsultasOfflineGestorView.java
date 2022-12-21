package es.uca.iw.biwan.views.consultasOffline.gestor;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.consultasOnline.gestor.ConsultasOnlineGestorView;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@CssImport("./themes/biwan/consultasOfflineGestor.css")
@PageTitle("Consultas Offline")
@Route("consultas-offline-gestor")
public class ConsultasOfflineGestorView extends VerticalLayout {

    public ConsultasOfflineGestorView() {

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

    private VerticalLayout ConsultasOffline() {

        //NEW
        VerticalLayout layoutConsultasOfflinePrincipal = new VerticalLayout();
        HorizontalLayout TituloVolverLayout = new HorizontalLayout();
        Anchor VolverAnchor = new Anchor("pagina-principal-gestor", "Volver");
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
        layoutHorConsultasOffline.add(ConsultasOnlineGestorView.ListaMensajesConsulta());
        layoutConsultasOfflinePrincipal.add(TituloVolverLayout, layoutHorConsultasOffline);

        return layoutConsultasOfflinePrincipal;
    }
}
