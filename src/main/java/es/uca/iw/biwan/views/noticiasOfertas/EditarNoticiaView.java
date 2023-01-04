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
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
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
import es.uca.iw.biwan.domain.usuarios.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@Route("editar-noticia-encargado")
@PageTitle("Editar Noticia")
@CssImport("./themes/biwan/editarNoticia.css")
public class EditarNoticiaView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;
    private static Noticia newNoticia;
    private static TextField titulo = new TextField("Título");
    private static TextArea descripcion = new TextArea("Descripción");
    private Button guardar = new Button("Guardar");
    private Button atras = new Button("Atrás");

    // Setter para coger la informacion de depende que noticia hayamos seleccionado para editar
    public static void setTituloDescripcion(Noticia noticia) {
        newNoticia = noticia;
        titulo.setValue(noticia.getTitulo());
        descripcion.setValue(noticia.getCuerpo());
    }

    public EditarNoticiaView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
            if (session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(Administrador.class) != null){
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un encargado de comunicación", "Aceptar", event -> {
                    if (session.getAttribute(Cliente.class) != null){
                        UI.getCurrent().navigate("pagina-principal-cliente");
                    }else if (session.getAttribute(Gestor.class) != null){
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    } else if (session.getAttribute(Administrador.class) != null){
                        UI.getCurrent().navigate("pagina-principal-admin");
                    }
                });
                error.open();
            } else {
                add(HeaderUsuarioLogueadoView.Header());
                add(crearTitulo());
                add(crearEditarNoticia());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    private Component crearTitulo() {
        var titulo = new H2("Editar Noticia");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(Alignment.CENTER);
        titulo.addClassName("title");
        return vlTitulo;
    }

    private Component crearEditarNoticia() {
        Binder<Anuncio> binderNoticia = new Binder<>(Anuncio.class);

        titulo.setValue(titulo.getValue());
        descripcion.setValue(descripcion.getValue());

        titulo.setMinWidth("700px");
        descripcion.setMinHeight("700px");
        descripcion.setMinHeight("198px");
        descripcion.addClassName("descripcion");
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
        flForm.addClassName("form");
        return flForm;
    }

    private Component crearBotones(Binder<Anuncio> binderNoticia) {
        guardar.addClassName("save");
        atras.addClassName("back");
        HorizontalLayout hlButtons = new HorizontalLayout();
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlButtons.add(guardar);
        hlButtons.add(atras);
        VerticalLayout vlButtons = new VerticalLayout(hlButtons);
        vlButtons.setAlignItems(Alignment.CENTER);
        vlButtons.addClassName("buttons");

        // Evento para volver a la pagina principal
        atras.getElement().addEventListener("click", e -> {
            UI.getCurrent().navigate("pagina-principal-encargado");
        });

        // Editar noticia
        guardar.addClickShortcut(Key.ENTER);
        guardar.addClickListener(event -> {
            if (binderNoticia.validate().isOk()) {
                Noticia noticia = new Noticia();
                noticia.setTipo(TipoAnuncio.NOTICIA);
                noticia.setUUID(newNoticia.getUUID());
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
            anuncioService.update(anuncio);
            ConfirmDialog confirmRequest = new ConfirmDialog("Editada Noticia", "Noticia editada correctamente", "Aceptar", event1 -> {
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
