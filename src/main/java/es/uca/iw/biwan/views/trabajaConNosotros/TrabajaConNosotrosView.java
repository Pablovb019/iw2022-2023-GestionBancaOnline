package es.uca.iw.biwan.views.trabajaConNosotros;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("./themes/biwan/trabajaConNosotros.css")
@Route("trabaja-con-nosotros")
@PageTitle("Trabaja con nosotros")
@AnonymousAllowed
public class TrabajaConNosotrosView extends VerticalLayout{

    public TrabajaConNosotrosView() {

        //NEW
        VerticalLayout layoutTrabajaConNosotros = new VerticalLayout();
        VerticalLayout layoutTextoTrabajaConNosotros = new VerticalLayout();
        H1 Titulo = new H1("Trabaja con nosotros");
        H5 Bienvenida = new H5("¡Bienvenido a la página de empleo de BIWAN! Si estás interesado en formar parte de" +
                " nuestro equipo, estás en el lugar adecuado.");
        H5 TextoBienvenida1 = new H5("En BIWAN valoramos a nuestros empleados y ofrecemos un entorno de trabajo" +
                " inclusivo y respetuoso. Contamos con una amplia variedad de puestos de trabajo y estamos siempre" +
                " buscando personas talentosas y comprometidas para unirse a nuestro equipo.");
        HorizontalLayout layoutQueOfrecemos = new HorizontalLayout();
        H4 Primero = new H4("1.");
        H4 QueOfrecemos = new H4("¿Qué ofrecemos a nuestros empleados?");
        H5 TextoQueOfrecemos = new H5("En BIWAN ofrecemos a nuestros empleados una serie de beneficios para mejorar" +
                " su bienestar y su calidad de vida. Algunos de estos beneficios son:");
        H5 TextoQueOfrecemos1 = new H5("• Plan de formación y desarrollo: Contamos con un plan de formación y " +
                "desarrollo que te permitirá adquirir nuevas habilidades y conocimientos para tu carrera profesional.");
        H5 TextoQueOfrecemos2 = new H5("• Plan de salud: Ofrecemos a nuestros empleados un plan de salud que incluye" +
                " cobertura médica y dental, así como acceso a servicios de bienestar y prevención de enfermedades.");
        H5 TextoQueOfrecemos3 = new H5("• Plan de beneficios: Además, nuestros empleados pueden acceder a un plan de" +
                " beneficios que incluye descuentos en productos y servicios de BIWAN y de nuestras empresas asociadas," +
                " así como en eventos y actividades culturales.");
        H5 TextoQueOfrecemos4 = new H5("• Horarios flexibles: Contamos con horarios flexibles y políticas de" +
                " teletrabajo para adaptarnos a las necesidades de nuestros empleados y mejorar su equilibrio entre la" +
                " vida laboral y personal.");
        HorizontalLayout layoutQueEstamosBuscando = new HorizontalLayout();
        H4 Segundo = new H4("2.");
        H4 QueEstamosBuscando = new H4("¿Qué estamos buscando?");
        H5 TextoQueEstamosBuscando = new H5("Estamos buscando personas comprometidas y proactivas, con una actitud" +
                " positiva y un fuerte sentido de la responsabilidad. Si tienes experiencia en el sector financiero y" +
                " tienes una formación académica en economía, finanzas o carreras similares, ¡podrías ser el candidato" +
                " perfecto para nosotros!");
        HorizontalLayout layoutComoSolicitarTrabajo = new HorizontalLayout();
        H4 Tercero = new H4("3.");
        H4 ComoSolicitarTrabajo = new H4("¿Cómo puedo solicitar un puesto de trabajo?");
        H5 TextoComoSolicitarTrabajo = new H5("Si estás interesado en unirte a nuestro equipo, puedes enviarnos tu" +
                " currículum a través de nuestro correo \"biwan@gmail.com\". Asegúrate de incluir una carta de motivación" +
                " y una descripción detallada de tus habilidades y experiencia relevantes. Si tu perfil encaja con alguna" +
                " de nuestras vacantes, nos pondremos en contacto contigo para programar una entrevista. ¡Esperamos " +
                "recibir tu candidatura pronto!");

        //ADD
        layoutQueOfrecemos.add(Primero, QueOfrecemos);
        layoutQueEstamosBuscando.add(Segundo, QueEstamosBuscando);
        layoutComoSolicitarTrabajo.add(Tercero, ComoSolicitarTrabajo);
        layoutTextoTrabajaConNosotros.add(Titulo, Bienvenida, TextoBienvenida1, layoutQueOfrecemos, TextoQueOfrecemos, TextoQueOfrecemos1, TextoQueOfrecemos2, TextoQueOfrecemos3, TextoQueOfrecemos4, layoutQueEstamosBuscando, TextoQueEstamosBuscando, layoutComoSolicitarTrabajo, TextoComoSolicitarTrabajo);
        layoutTrabajaConNosotros.add(HeaderView.Header(), layoutTextoTrabajaConNosotros, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        Primero.addClassName("Numero");
        Segundo.addClassName("Numero");
        Tercero.addClassName("Numero");
        QueOfrecemos.addClassName("PreguntaTrabajaConNosotros");
        QueEstamosBuscando.addClassName("PreguntaTrabajaConNosotros");
        ComoSolicitarTrabajo.addClassName("PreguntaTrabajaConNosotros");
        Bienvenida.addClassName("textoSinMargenTrabajaConNosotros");
        TextoBienvenida1.addClassName("textoSinMargenTrabajaConNosotros");
        TextoQueOfrecemos.addClassName("textoTrabajaConNosotros");
        TextoQueOfrecemos1.addClassName("textoTrabajaConNosotrosDobleMargen");
        TextoQueOfrecemos2.addClassName("textoTrabajaConNosotrosDobleMargen");
        TextoQueOfrecemos3.addClassName("textoTrabajaConNosotrosDobleMargen");
        TextoQueOfrecemos4.addClassName("textoTrabajaConNosotrosDobleMargen");
        TextoQueEstamosBuscando.addClassName("textoTrabajaConNosotros");
        TextoComoSolicitarTrabajo.addClassName("textoTrabajaConNosotros");
        layoutTextoTrabajaConNosotros.addClassName("layoutTextoTrabajaConNosotros");

        //ALIGNMENT
        layoutTextoTrabajaConNosotros.setWidth("50%");
        layoutTrabajaConNosotros.expand(layoutTextoTrabajaConNosotros);
        layoutTextoTrabajaConNosotros.setAlignItems(FlexComponent.Alignment.START);
        layoutTrabajaConNosotros.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutTrabajaConNosotros.setSizeFull();

        add(layoutTrabajaConNosotros);
    }
}
