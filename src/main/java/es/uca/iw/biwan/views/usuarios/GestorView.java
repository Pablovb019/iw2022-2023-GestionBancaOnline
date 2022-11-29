package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderClienteView;

@CssImport("/themes/biwan/paginaPrincipalGestor.css")
@PageTitle("Página Principal Gestor")
@Route("pagina-principal-gestor")
public class GestorView extends VerticalLayout {

    public GestorView(){

        //NEW
        VerticalLayout layoutGestor = new VerticalLayout();
        VerticalLayout layoutGestionConsultas = new VerticalLayout();
        layoutGestionConsultas.add(GestorConsultas());

        /*Grid<Cliente> grid = new Grid<>(Cliente.class, false);
        //grid.addColumn(Cliente::getNombre);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        add(grid);*/

        //ADD
        layoutGestor.add(HeaderClienteView.Header(), layoutGestionConsultas, FooterView.Footer());

        //ALIGNMENT
        layoutGestor.expand(layoutGestionConsultas);
        layoutGestor.setAlignItems(Alignment.CENTER);
        layoutGestor.setSizeFull();

        add(layoutGestor);
    }

    private VerticalLayout GestorConsultas() {

        //NEW
        VerticalLayout layoutVerGestorPrincipal = new VerticalLayout();
        VerticalLayout layoutVerGestorTabla = new VerticalLayout();
        HorizontalLayout layoutComponenteTabla = new HorizontalLayout();
        H1 Titulo = new H1("Bienvenido Gestor");
        Anchor NombreCliente = new Anchor("", "Jose Antonio Alonso de la Huerta");
        Anchor CuentasYTarjetasButton = new Anchor("", "Cuentas y tarjetas");
        Anchor ConsultaOnlineButton = new Anchor("consultas-online", "Consulta Online");
        Anchor ConsultaOfflineButton = new Anchor("consultas-offline", "Consulta Offline");
        Span counterOnline = new Span("1");
        Span counterOffline = new Span("3");



        //ADD CLASS
        Titulo.addClassName("Titulo");
        NombreCliente.addClassNames("NombreClienteAnchor", "Separacion");
        CuentasYTarjetasButton.addClassNames("Separacion", "AnchorButton");
        ConsultaOnlineButton.addClassNames("Separacion", "AnchorConsultaOnlineOffline");
        ConsultaOfflineButton.addClassNames("Separacion", "AnchorConsultaOnlineOffline");
        layoutVerGestorPrincipal.addClassName("layoutVerGestor");
        layoutComponenteTabla.addClassName("layoutGestionCliente");
        counterOnline.addClassName("counter");
        counterOffline.addClassName("counter");

        //ALIGNMENT
        layoutComponenteTabla.expand(NombreCliente);
        layoutComponenteTabla.setJustifyContentMode(JustifyContentMode.BETWEEN);

        //ADD
        ConsultaOnlineButton.add(counterOnline);
        ConsultaOfflineButton.add(counterOffline);
        layoutComponenteTabla.add(NombreCliente, CuentasYTarjetasButton, ConsultaOnlineButton, ConsultaOfflineButton);
        layoutVerGestorTabla.add(layoutComponenteTabla);
        layoutVerGestorPrincipal.add(Titulo, layoutComponenteTabla);

        return  layoutVerGestorPrincipal;
    }



}
