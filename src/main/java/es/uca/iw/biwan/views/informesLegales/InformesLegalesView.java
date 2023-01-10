package es.uca.iw.biwan.views.informesLegales;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.headers.HeaderView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


@CssImport("./themes/biwan/informesLegales.css")
@Route("informes-legales")
@PageTitle("Informes Legales")
@AnonymousAllowed
public class InformesLegalesView  extends VerticalLayout {

    public InformesLegalesView(){

        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null
                || session.getAttribute(Gestor.class) != null
                || session.getAttribute(EncargadoComunicaciones.class) != null
                || session.getAttribute(Administrador.class) != null) {
            // Si hay un usuario logueado, mostrar la vista de usuario logueado
            add(HeaderUsuarioLogueadoView.Header());
        } else {
            // Si no hay un usuario logueado, mostrar la vista de usuario no logueado
            add(HeaderView.Header());
        }

        //NEW
        VerticalLayout layoutInformesLegales = new VerticalLayout();
        VerticalLayout layoutArchivosInformesLegales = new VerticalLayout();
        H1 Titulo = new H1("Informes Legales");
        H4 ArchivosDescargables = new H4("ARCHIVOS DESCARGABLES:");
        File InformeAnual = new File("var/app/files/Informe_Anual.pdf");

        //Files
        StreamResource resourceInformeAnual = new StreamResource("Informe Anual.pdf", () -> {
            try {
                return new FileInputStream(InformeAnual);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        File InformeDeCumplimiento = new File("var/app/files/Informe_De_Cumplimiento.pdf");
        StreamResource resourceInformeDeCumplimiento = new StreamResource("Informe de Cumplimiento.pdf", () -> {
            try {
                return new FileInputStream(InformeDeCumplimiento);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        File InformeDeGestionDeRiesgos = new File("var/app/files/Informe_De_Gestion_De_Riesgos.pdf");
        StreamResource resourceInformeDeGestionDeRiesgos = new StreamResource("Informe De Gestión De Riesgos.pdf", () -> {
            try {
                return new FileInputStream(InformeDeGestionDeRiesgos);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        File InformeDeGobiernoCorporativo = new File("var/app/files/Informe_De_Gobierno_Corporativo.pdf");
        StreamResource resourceInformeDeGobiernoCorporativo = new StreamResource("Informe De Gobierno Corporativo.pdf", () -> {
            try {
                return new FileInputStream(InformeDeGobiernoCorporativo);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        File InformeDeSostenibilidad = new File("var/app/files/files/Informe_De_Sostenibilidad.pdf");
        StreamResource resourceInformeDeSostenibilidad = new StreamResource("Informe De Sostenibilidad.pdf", () -> {
            try {
                return new FileInputStream(InformeDeSostenibilidad);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        ///NEWS
        Anchor DescargaInformeAnual = new Anchor(resourceInformeAnual, "Informe Anual");
        Icon IconInformeAnual = new Icon(VaadinIcon.FILE_TEXT_O);
        Anchor DescargaInformeCumplimiento = new Anchor(resourceInformeDeCumplimiento, "Informe de Cumplimiento");
        Icon IconInformeInformeCumplimiento = new Icon(VaadinIcon.FILE_TEXT_O);
        Anchor DescargaInformeGestionRiesgos = new Anchor(resourceInformeDeGestionDeRiesgos, "Informe de Gestión de Riesgos");
        Icon IconInformeGestionRiesgos = new Icon(VaadinIcon.FILE_TEXT_O);
        Anchor DescargaInformeGobiernoCorporativo = new Anchor(resourceInformeDeGobiernoCorporativo, "Informe de Gobierno Corporativo");
        Icon IconInformeGobiernoCorporativo = new Icon(VaadinIcon.FILE_TEXT_O);
        Anchor DescargaInformeSostenibilidad = new Anchor(resourceInformeDeSostenibilidad, "Informe de Sostenibilidad");
        Icon IconInformeSostenibilidad = new Icon(VaadinIcon.FILE_TEXT_O);

        //ADD
        DescargaInformeAnual.addComponentAsFirst(IconInformeAnual);
        DescargaInformeCumplimiento.addComponentAsFirst(IconInformeInformeCumplimiento);
        DescargaInformeGestionRiesgos.addComponentAsFirst(IconInformeGestionRiesgos);
        DescargaInformeGobiernoCorporativo.addComponentAsFirst(IconInformeGobiernoCorporativo);
        DescargaInformeSostenibilidad.addComponentAsFirst(IconInformeSostenibilidad);
        layoutArchivosInformesLegales.add(Titulo, ArchivosDescargables, DescargaInformeAnual, DescargaInformeCumplimiento,
                DescargaInformeGestionRiesgos, DescargaInformeGobiernoCorporativo, DescargaInformeSostenibilidad);
        layoutInformesLegales.add(layoutArchivosInformesLegales, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        DescargaInformeAnual.addClassNames("Descarga", "SubrayadoAlPasarRaton");
        DescargaInformeCumplimiento.addClassNames("Descarga", "SubrayadoAlPasarRaton");
        DescargaInformeGestionRiesgos.addClassNames("Descarga", "SubrayadoAlPasarRaton");
        DescargaInformeGobiernoCorporativo.addClassNames("Descarga", "SubrayadoAlPasarRaton");
        DescargaInformeSostenibilidad.addClassNames("Descarga", "SubrayadoAlPasarRaton");
        layoutArchivosInformesLegales.addClassName("layoutArchivosInformesLegales");

        //ALIGNMENT
        layoutArchivosInformesLegales.setWidth("50%");
        layoutInformesLegales.expand(layoutArchivosInformesLegales);
        layoutArchivosInformesLegales.setAlignItems(Alignment.START);
        layoutInformesLegales.setAlignItems(Alignment.CENTER);
        layoutInformesLegales.setSizeFull();

        add(layoutInformesLegales);
    }

}
