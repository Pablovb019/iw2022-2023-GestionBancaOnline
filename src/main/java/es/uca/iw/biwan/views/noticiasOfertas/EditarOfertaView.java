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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route("editar-oferta-encargado")
@PageTitle("Editar Oferta")
@CssImport("./themes/biwan/editarOferta.css")
public class EditarOfertaView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;
    private static Oferta newOferta;
    private static TextField titulo = new TextField("Título");
    private static TextArea descripcion = new TextArea("Descripción");
    private static DatePicker fechaFin = new DatePicker("Fecha fin de la oferta");
    private Button guardar = new Button("Guardar");
    private Button atras = new Button("Atrás");

    // Setter para coger la informacion de depende que oferta hayamos seleccionado para editar
    public static void setTituloDescripcion(Oferta oferta) {
        newOferta = oferta;
        titulo.setValue(oferta.getTitulo());
        descripcion.setValue(oferta.getCuerpo());
        fechaFin.setValue(oferta.getFechaFin());
    }

    public EditarOfertaView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(EncargadoComunicaciones.class) != null) {
            if (!session.getAttribute(EncargadoComunicaciones.class).getRol().contentEquals("ENCARGADO_COMUNICACIONES")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un encargado de comunicaciones", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                add(HeaderUsuarioLogueadoView.Header());
                add(crearTitulo());
                add(crearEditarOferta());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private Component crearTitulo() {
        var titulo = new H2("Editar Oferta");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(FlexComponent.Alignment.CENTER);
        titulo.addClassName("titleEditar");
        return vlTitulo;
    }

    private Component crearEditarOferta() {
        Binder<Anuncio> binderOferta = new Binder<>(Anuncio.class);

        titulo.setValue(titulo.getValue());
        descripcion.setValue(descripcion.getValue());
        fechaFin.setValue(fechaFin.getValue());

        titulo.setMinWidth("700px");
        descripcion.setMinHeight("700px");
        descripcion.setMinHeight("198px");
        descripcion.addClassName("descripcion");
        FormLayout flForm = new FormLayout();

        binderOferta.forField(titulo)
                .asRequired("El título es obligatorio")
                .withValidator(titulo -> titulo.length() <= 128, "El titulo debe tener un tamaño menor a 128 caracteres")
                .bind(Anuncio::getTitulo, Anuncio::setTitulo);

        binderOferta.forField(descripcion)
                .asRequired("La descripción es obligatoria")
                .withValidator(descripcion -> descripcion.length() <= 2048, "La descripción debe tener un tamaño menor a 2048 caracteres")
                .bind(Anuncio::getCuerpo, Anuncio::setCuerpo);

        binderOferta.forField(fechaFin)
                .asRequired("La fecha fin de la oferta es obligatoria")
                .withValidator(fecha -> fecha.isAfter(LocalDate.now()), "La fecha debe ser posterior a la actual")
                .bind(Anuncio::getFechaFin, Anuncio::setFechaFin);

        Component botones = crearBotones(binderOferta);

        flForm.add(titulo, descripcion, fechaFin, botones);
        flForm.setColspan(titulo, 2);
        flForm.setColspan(descripcion, 2);
        flForm.setColspan(botones, 2);
        flForm.addClassName("formEditar");
        return flForm;
    }

    private Component crearBotones(Binder<Anuncio> binderOferta) {
        guardar.addClassName("saveEditar");
        atras.addClassName("backEditar");
        HorizontalLayout hlButtons = new HorizontalLayout();
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlButtons.add(guardar);
        hlButtons.add(atras);
        VerticalLayout vlButtons = new VerticalLayout(hlButtons);
        vlButtons.setAlignItems(FlexComponent.Alignment.CENTER);
        vlButtons.addClassName("buttonsEditar");

        // Evento para volver a la pagina principal
        atras.getElement().addEventListener("click", e -> {
            UI.getCurrent().navigate("pagina-principal-encargado");
        });

        // Editar oferta
        guardar.addClickShortcut(Key.ENTER);
        guardar.addClickListener(event -> {
            if (binderOferta.validate().isOk()) {
                Noticia noticia = new Noticia();
                noticia.setTipo(TipoAnuncio.OFERTA);
                noticia.setUUID(newOferta.getUUID());
                noticia.setFechaInicio(LocalDate.now());
                noticia.setFechaFin(fechaFin.getValue());
                noticia.setTitulo(titulo.getValue());
                noticia.setCuerpo(descripcion.getValue());
                CreateRequest(noticia);
            }
        });
        return vlButtons;
    }

    private void CreateRequest(Anuncio anuncio) {
        try {
            anuncioService.update(anuncio);
            ConfirmDialog confirmRequest = new ConfirmDialog("Editada Oferta", "Oferta editada correctamente", "Aceptar", event1 -> {
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
