package es.uca.iw.biwan.views.avisoLegal;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("./themes/biwan/avisoLegal.css")
@Route("aviso-legal")
@PageTitle("Aviso Legal")
@AnonymousAllowed
public class AvisoLegalView extends VerticalLayout{

    public AvisoLegalView() {

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
        VerticalLayout layoutAvisoLegal = new VerticalLayout();
        VerticalLayout layoutTextoAvisoLegal = new VerticalLayout();
        H1 Titulo = new H1("Aviso Legal");
        H3 CondicionesGeneralesUso = new H3("CONDICIONES GENERALES DE USO");
        H5 TextoCondGenUso = new H5("Este sitio web (en adelante, \"el Sitio\") es propiedad de BIWAN, S.A. (en adelante," +
                " \"BIWAN\"). El acceso y uso del Sitio están sujetos a las siguientes condiciones de uso y a todas las " +
                "leyes y regulaciones aplicables. Al acceder y utilizar el Sitio, usted acepta estas condiciones de uso " +
                "sin modificaciones. Si no está de acuerdo con estas condiciones de uso, no debe utilizar el Sitio.");
        H3 ResponsabilidadGarantia = new H3("RESPONSABILIDAD Y GARANTÍA DE GRUPO BIWAN");
        H5 TextoRespGar1 = new H5("BIWAN se reserva el derecho de modificar estas condiciones de uso en cualquier momento y " +
                "sin previo aviso. Su uso continuado del Sitio después de la publicación de cualquier modificación de " +
                "estas condiciones de uso constituirá su aceptación de dichas modificaciones.");
        H5 TextoRespGar2 = new H5("El contenido del Sitio se proporciona \"tal cual\" sin garantía de ningún tipo, " +
                "ya sea expresa o implícita. BIWAN no garantiza la exactitud, confiabilidad o integridad de cualquier " +
                "información, texto, gráficos, enlaces u otros elementos contenidos en el Sitio. BIWAN no será " +
                "responsable de ningún error u omisión en el contenido del Sitio.");
        H5 TextoRespGar3 = new H5("El uso del Sitio y del contenido del mismo es bajo su propia responsabilidad. " +
                "BIWAN no será responsable de ningún daño o perjuicio de cualquier tipo derivado del uso o la " +
                "imposibilidad de uso del Sitio o de cualquier sitio web vinculado a él.");
        H5 TextoRespGar4 = new H5("Este Sitio puede contener enlaces a sitios web de terceros. Estos enlaces se " +
                "proporcionan únicamente como una conveniencia y no implican la aprobación por parte de BIWAN de los " +
                "sitios web de terceros ni una responsabilidad por el contenido de dichos sitios web. BIWAN no controla " +
                "ni es responsable del contenido de los sitios web de terceros. El uso de cualquier sitio web de " +
                "terceros es bajo su propia responsabilidad.");
        H5 TextoRespGar5 = new H5("BIWAN se reserva el derecho de denegar el acceso al Sitio en cualquier momento " +
                "y sin previo aviso.");
        H3 PropiedadIndustrialIntelectual = new H3("PROPIEDAD INDUSTRIAL E INTELECTUAL");
        H5 TextoPropIndInt = new H5("El Sitio y todo el contenido, incluyendo texto, gráficos, imágenes y software, son " +
                "propiedad de BIWAN o de sus proveedores de contenido y están protegidos por las leyes de propiedad " +
                "intelectual de España y otras leyes y tratados internacionales. El contenido del Sitio no puede ser " +
                "copiado, reproducido, distribuido, transmitido, exhibido, publicado ni utilizado de ninguna otra forma " +
                "sin el consentimiento previo por escrito de BIWAN.");
        H3 LegislacionAplicable = new H3("LEGISLACIÓN APLICABLE");
        H5 TextoLegApli = new H5("Las presentes condiciones generales se regirán por la legislación española.");
        H3 InformacionProteccionDatos = new H3("INFORMACIÓN SOBRE PROTECCIÓN DE DATOS");
        H5 TextoInfProtDat1 = new H5("BIWAN se compromete a proteger la privacidad y la seguridad de sus clientes y a cumplir" +
                " con todas las leyes y regulaciones aplicables en materia de protección de datos personales.");
        H5 TextoInfProtDat2 = new H5("BIWAN recopila y procesa ciertos datos personales de sus clientes con el fin " +
                "de proporcionar servicios financieros y realizar actividades comerciales. Estos datos pueden incluir " +
                "nombre, dirección, número de identificación, información de contacto y datos financieros. BIWAN " +
                "también puede recopilar y procesar datos personales de otras fuentes, como agencias de informes " +
                "crediticios y otras entidades financieras.");
        H5 TextoInfProtDat3 = new H5("BIWAN solo utiliza los datos personales de sus clientes de acuerdo con los propósitos " +
                "para los que se han recopilado y en conformidad con las leyes y regulaciones aplicables. BIWAN también toma " +
                "medidas de seguridad razonables para proteger los datos personales de sus clientes contra el uso no autorizado," +
                " la pérdida, el acceso no autorizado, la divulgación, la alteración y la destrucción.");
        H5 TextoInfProtDat4 = new H5("Los clientes de BIWAN tienen ciertos derechos en relación con sus datos personales, " +
                "como el derecho a acceder a sus datos, solicitar una copia de los mismos, solicitar su rectificación o " +
                "eliminación y oponerse al tratamiento de sus datos. Los clientes también tienen el derecho a presentar una " +
                "queja ante la autoridad de protección de datos competente si creen que BIWAN ha violado sus derechos en " +
                "materia de protección de datos.");


        //ADD
        layoutTextoAvisoLegal.add(Titulo, CondicionesGeneralesUso, TextoCondGenUso, ResponsabilidadGarantia, TextoRespGar1, TextoRespGar2, TextoRespGar3, TextoRespGar4, TextoRespGar5, PropiedadIndustrialIntelectual, TextoPropIndInt, LegislacionAplicable, TextoLegApli, InformacionProteccionDatos, TextoInfProtDat1, TextoInfProtDat2, TextoInfProtDat3, TextoInfProtDat4);
        layoutAvisoLegal.add(layoutTextoAvisoLegal, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        layoutTextoAvisoLegal.addClassName("layoutTextoAvisoLegal");
        TextoCondGenUso.addClassName("texto");
        ResponsabilidadGarantia.addClassName("texto");
        TextoRespGar1.addClassName("texto");
        TextoRespGar2.addClassName("texto");
        TextoRespGar3.addClassName("texto");
        TextoRespGar4.addClassName("texto");
        TextoRespGar5.addClassName("texto");
        TextoPropIndInt.addClassName("texto");
        TextoLegApli.addClassName("texto");
        TextoInfProtDat1.addClassName("texto");
        TextoInfProtDat2.addClassName("texto");
        TextoInfProtDat3.addClassName("texto");
        TextoInfProtDat4.addClassName("texto");

        //ALIGNMENT
        layoutTextoAvisoLegal.setWidth("50%");
        layoutAvisoLegal.expand(layoutTextoAvisoLegal);
        layoutTextoAvisoLegal.setAlignItems(Alignment.START);
        layoutAvisoLegal.setAlignItems(Alignment.CENTER);
        layoutAvisoLegal.setSizeFull();

        add(layoutAvisoLegal);
    }
}
