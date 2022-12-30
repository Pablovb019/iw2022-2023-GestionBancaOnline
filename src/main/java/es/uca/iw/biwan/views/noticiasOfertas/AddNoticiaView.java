package es.uca.iw.biwan.views.noticiasOfertas;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@Route("add-noticia-encargado")
@PageTitle("Añadir Noticia")
@CssImport("./themes/biwan/addNoticia.css")
public class AddNoticiaView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;
    private TextField titulo = new TextField("Título");
    private TextArea descripcion = new TextArea("Descripción");
    private Button guardar = new Button("Guardar");
    private Button atras = new Button("Atrás");

    public AddNoticiaView(){
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
                add(crearAñadirNoticia());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private Component crearTitulo() {
        var titulo = new H2("Añadir Noticia");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(Alignment.CENTER);
        titulo.addClassName("tituloColor");
        return vlTitulo;
    }

    private Component crearAñadirNoticia() {
        Binder<Anuncio> binderNoticia = new Binder<>(Anuncio.class);

        titulo.setMinWidth("700px");
        descripcion.setMinHeight("700px");
        descripcion.setMinHeight("198px");
        FormLayout flForm = new FormLayout();

        binderNoticia.forField(titulo)
                .asRequired("El título es obligatorio")
                .withValidator(titulo -> titulo.length() <= 128, "El titulo debe tener un tamaño menor a 128 caracteres")
                .bind(Anuncio::getTitulo, Anuncio::setTitulo);

        binderNoticia.forField(descripcion)
                .asRequired("La descripción es obligatoria")
                .withValidator(descripcion -> descripcion.length() <= 2048, "La descripción debe tener un tamaño menor a 2048 caracteres")
                .bind(Anuncio::getCuerpo, Anuncio::setCuerpo);

        Component botones = crearBotones(binderNoticia);

        flForm.add(titulo, descripcion, botones);
        flForm.setColspan(titulo, 2);
        flForm.setColspan(descripcion, 2);
        flForm.setColspan(botones, 2);
        flForm.addClassName("flForm");
        return flForm;
    }

    private Component crearBotones(Binder<Anuncio> binderNoticia) {
        guardar.addClassName("guardarButton");
        atras.addClassName("atrasButton");
        HorizontalLayout hlButtons = new HorizontalLayout();
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlButtons.add(guardar);
        hlButtons.add(atras);
        VerticalLayout vlButtons = new VerticalLayout(hlButtons);
        vlButtons.setAlignItems(Alignment.CENTER);
        vlButtons.addClassName("vlButtons");

        // Evento para volver a la pagina principal
        atras.getElement().addEventListener("click", e -> {
            UI.getCurrent().navigate("pagina-principal-encargado");
        });

        // Añadir noticia
        guardar.addClickShortcut(Key.ENTER);
        guardar.addClickListener(event -> {
            if (binderNoticia.validate().isOk()) {
                Noticia noticia = new Noticia();
                noticia.setTipo(TipoAnuncio.NOTICIA);
                noticia.setUUID(UUID.randomUUID());
                noticia.setFechaInicio(LocalDate.now());
                noticia.setFechaFin(null);
                noticia.setTitulo(titulo.getValue());
                noticia.setCuerpo(descripcion.getValue());
                CreateRequest(noticia);
            }
        });
        return vlButtons;
    }

    private void CreateRequest(Anuncio anuncio) {
        try {
            anuncioService.save(anuncio);
            ConfirmDialog confirmRequest = new ConfirmDialog("Añadida Noticia", "Noticia añadida correctamente", "Aceptar", event1 -> {
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
