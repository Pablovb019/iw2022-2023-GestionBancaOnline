package es.uca.iw.biwan.views.noticiasOfertas;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@Route("add-noticia-encargado")
@PageTitle("Añadir Noticia")
@CssImport("/themes/biwan/addNoticia.css")
public class AddNoticiaView extends VerticalLayout {
    private TextField titulo = new TextField("Título");
    private TextArea descripcion = new TextArea("Descripción");
    private Button guardar = new Button("Guardar");
    private Button atras = new Button("Atrás");

    public AddNoticiaView(){
        add(HeaderUsuarioLogueadoView.Header());
        add(crearTitulo());
        add(crearAñadirNoticia());
        add(FooterView.Footer());
    }

    private Component crearTitulo() {
        var titulo = new H2("Añadir Noticia");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(Alignment.CENTER);
        titulo.addClassName("tituloColor");
        return vlTitulo;
    }

    private Component crearAñadirNoticia() {
        titulo.setMinWidth("700px");
        descripcion.setMinHeight("700px");
        descripcion.setMinHeight("198px");
        FormLayout flForm = new FormLayout();
        Component botones = crearBotones();
        flForm.add(titulo, descripcion, botones);
        flForm.setColspan(titulo, 2);
        flForm.setColspan(descripcion, 2);
        flForm.setColspan(botones, 2);
        flForm.addClassName("flForm");
        return flForm;
    }

    private Component crearBotones() {
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
        return vlButtons;
    }
}