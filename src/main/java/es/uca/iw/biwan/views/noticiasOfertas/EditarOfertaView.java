package es.uca.iw.biwan.views.noticiasOfertas;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@Route("editar-oferta-encargado")
@PageTitle("Editar Oferta")
@CssImport("/themes/biwan/editarOferta.css")
public class EditarOfertaView extends VerticalLayout {
    private static TextField titulo = new TextField("Título");
    private static TextArea descripcion = new TextArea("Descripción");
    private Button guardar = new Button("Guardar");
    private Button atras = new Button("Atrás");

    // Setter para coger la informacion de depende que oferta hayamos seleccionado para editar
    public static void setTituloDescripcion(String title, String description) {
        titulo.setValue(title);
        descripcion.setValue(description);
    }

    public EditarOfertaView(){
        add(HeaderUsuarioLogueadoView.Header());
        add(crearTitulo());
        add(crearEditarOferta());
        add(FooterView.Footer());
    }

    private Component crearTitulo() {
        var titulo = new H2("Editar Oferta");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(FlexComponent.Alignment.CENTER);
        titulo.addClassName("titleEditar");
        return vlTitulo;
    }

    private Component crearEditarOferta() {
        titulo.setValue(titulo.getValue());
        descripcion.setValue(descripcion.getValue());
        titulo.setMinWidth("700px");
        descripcion.setMinHeight("700px");
        descripcion.setMinHeight("198px");
        FormLayout flForm = new FormLayout();
        Component botones = crearBotones();
        flForm.add(titulo, descripcion, botones);
        flForm.setColspan(titulo, 2);
        flForm.setColspan(descripcion, 2);
        flForm.setColspan(botones, 2);
        flForm.addClassName("formEditar");
        return flForm;
    }

    private Component crearBotones() {
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
        return vlButtons;
    }
}
