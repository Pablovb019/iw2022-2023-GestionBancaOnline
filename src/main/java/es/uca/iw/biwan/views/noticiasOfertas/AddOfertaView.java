package es.uca.iw.biwan.views.noticiasOfertas;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.AnuncioService;
import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@Route("add-oferta-encargado")
@PageTitle("Añadir Oferta")
@CssImport("./themes/biwan/addOferta.css")
public class AddOfertaView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;
    private TextField titulo = new TextField("Título");
    private TextArea descripcion = new TextArea("Descripción");
    private DatePicker fechaFin = new DatePicker("Fecha fin de la oferta");
    private Button guardar = new Button("Guardar");
    private Button atras = new Button("Atrás");

    public AddOfertaView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("ENCARGADO_COMUNICACIONES")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un encargado de comunicaciones", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                add(HeaderUsuarioLogueadoView.Header());
                add(crearTitulo());
                add(crearAñadirOferta());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private Component crearTitulo() {
        var titulo = new H2("Añadir Oferta");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(Alignment.CENTER);
        titulo.addClassName("colorTitulo");
        return vlTitulo;
    }

    private Component crearAñadirOferta() {
        Binder<Anuncio> binderOferta = new Binder<>(Anuncio.class);

        titulo.setMinWidth("700px");
        descripcion.setMinHeight("700px");
        descripcion.setMinHeight("198px");
        FormLayout flForm = new FormLayout();

        binderOferta.forField(titulo)
                .asRequired("El título es obligatorio")
                .bind(Anuncio::getTitulo, Anuncio::setTitulo);

        binderOferta.forField(descripcion)
                .asRequired("La descripción es obligatoria")
                .bind(Anuncio::getCuerpo, Anuncio::setCuerpo);

        binderOferta.forField(fechaFin)
                .asRequired("La descripción es obligatoria")
                .withValidator(fecha -> fecha.isAfter(LocalDate.now()), "La fecha debe ser posterior a la actual")
                .bind(Anuncio::getFechaFin, Anuncio::setFechaFin);

        Component botones = crearBotones(binderOferta);
        flForm.add(titulo, descripcion, fechaFin, botones);
        flForm.setColspan(titulo, 2);
        flForm.setColspan(descripcion, 2);
        flForm.setColspan(fechaFin, 2);
        flForm.setColspan(botones, 2);
        flForm.addClassName("Form");
        return flForm;
    }

    private Component crearBotones(Binder<Anuncio> binderOferta) {
        guardar.addClassName("guardar");
        atras.addClassName("atras");
        HorizontalLayout hlButtons = new HorizontalLayout();
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlButtons.add(guardar);
        hlButtons.add(atras);
        VerticalLayout vlButtons = new VerticalLayout(hlButtons);
        vlButtons.setAlignItems(Alignment.CENTER);
        vlButtons.addClassName("Buttons");

        // Evento para volver a la pagina principal
        atras.getElement().addEventListener("click", e -> {
            UI.getCurrent().navigate("pagina-principal-encargado");
        });

        //Añadir oferta
        guardar.addClickShortcut(Key.ENTER);
        guardar.addClickListener(event -> {
            if (binderOferta.validate().isOk()) {
                Oferta oferta = new Oferta();
                oferta.setTipo(TipoAnuncio.OFERTA);
                oferta.setUUID(UUID.randomUUID());
                oferta.setFechaInicio(LocalDate.now());
                oferta.setFechaFin(fechaFin.getValue());
                oferta.setTitulo(titulo.getValue());
                oferta.setCuerpo(descripcion.getValue());
                CreateRequest(oferta);
            }
        });
        return vlButtons;
    }

    private void CreateRequest(Anuncio anuncio) {
        try {
            anuncioService.save(anuncio);
            ConfirmDialog confirmRequest = new ConfirmDialog("Añadida Oferta", "Oferta añadida correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("/pagina-principal-encargado");
            });
            confirmRequest.open();
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }
}
