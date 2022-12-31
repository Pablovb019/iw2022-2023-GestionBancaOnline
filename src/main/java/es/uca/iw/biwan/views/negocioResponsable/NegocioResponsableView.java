package es.uca.iw.biwan.views.negocioResponsable;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("./themes/biwan/negocioResponsable.css")
@Route("negocio-responsable")
@PageTitle("Negocio Responsable")
@AnonymousAllowed
public class NegocioResponsableView extends VerticalLayout{

    public NegocioResponsableView() {

        //NEW
        VerticalLayout layoutNegocioResponsable = new VerticalLayout();
        VerticalLayout layoutTextoNegocioResponsable = new VerticalLayout();
        H1 Titulo = new H1("Negocio Responsable");
        H3 InformeNegocioResponsable = new H3("INFORME DE NEGOCIO RESPONSABLE DE BIWAN");
        H5 Introduccion = new H5("En BIWAN, entendemos que la responsabilidad empresarial va más allá de cumplir " +
                "con las leyes y regulaciones aplicables. Creemos que es nuestra responsabilidad contribuir positivamente" +
                " a la sociedad y al medio ambiente en el que operamos.");
        H5 Hitos = new H5("Durante el último año, hemos implementado varias iniciativas de negocio responsable en" +
                " nuestras operaciones diarias. A continuación, presentamos algunos de los principales hitos alcanzados" +
                " durante este período:");
        H5 Hito1 = new H5("\t• Reducción del consumo de energía: Hemos instalado equipos de alta eficiencia energética" +
                " en todas nuestras sucursales y oficinas centrales, lo que nos ha permitido reducir nuestro consumo de" +
                " energía en un 15%.");
        H5 Hito2 = new H5("\t• Financiación sostenible: Hemos aumentado nuestra cartera de préstamos sostenibles en un" +
                " 20%, apoyando proyectos que tienen un impacto positivo en la sociedad y el medio ambiente, como " +
                "proyectos de energía renovable y agricultura sostenible.");
        H5 Hito3 = new H5("\t• Reciclaje y gestión de residuos: Hemos implementado un sistema de reciclaje y gestión de" +
                " residuos en todas nuestras sucursales, lo que nos ha permitido reducir nuestras emisiones de gases de" +
                " efecto invernadero en un 10%.");
        H5 Hito4 = new H5("\t" + "• Programas de responsabilidad social: Hemos establecido alianzas con organizaciones sin" +
                " fines de lucro para apoyar programas de educación financiera y emprendimiento en comunidades desfavorecidas. Además, hemos establecido un programa de voluntariado en el que nuestros empleados pueden dedicar su tiempo y esfuerzo a proyectos de responsabilidad social.");
        H5 Final = new H5("En BIWAN, estamos comprometidos con la sostenibilidad y la responsabilidad empresarial" +
                " a largo plazo. Continuaremos trabajando para mejorar nuestro impacto en la sociedad y el medio ambiente y para ofrecer a nuestros clientes productos y servicios responsables.");

        //ADD
        layoutTextoNegocioResponsable.add(Titulo, InformeNegocioResponsable, Introduccion, Hitos, Hito1, Hito2, Hito3, Hito4, Final);
        layoutNegocioResponsable.add(HeaderView.Header(), layoutTextoNegocioResponsable, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        layoutTextoNegocioResponsable.addClassName("layoutTextoNegocioResponsable");
        Introduccion.addClassName("texto");
        Hitos.addClassName("texto");
        Hito1.addClassNames("texto", "tab");
        Hito2.addClassNames("texto", "tab");
        Hito3.addClassNames("texto", "tab");
        Hito4.addClassNames("texto", "tab");
        Final.addClassNames("texto");

        //ALIGNMENT
        layoutTextoNegocioResponsable.setWidth("50%");
        layoutNegocioResponsable.expand(layoutTextoNegocioResponsable);
        layoutTextoNegocioResponsable.setAlignItems(FlexComponent.Alignment.START);
        layoutNegocioResponsable.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutNegocioResponsable.setSizeFull();

        add(layoutNegocioResponsable);
    }
}
