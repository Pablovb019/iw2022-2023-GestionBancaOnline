package es.uca.iw.biwan.views.masInformacion;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("./themes/biwan/masInformacion.css")
@Route("mas-informacion")
@PageTitle("Más Información")
@AnonymousAllowed
public class MasInformacionView extends VerticalLayout {

    public MasInformacionView() {

        //NEW
        VerticalLayout layoutMasInformacion = new VerticalLayout();
        VerticalLayout layoutTextoMasInformacion = new VerticalLayout();
        H1 Titulo = new H1("Más Información");
        HorizontalLayout layoutSobreBIWAN = new HorizontalLayout();
        H4 Primero = new H4("1.");
        H4 SobreBIWAN = new H4("Sobre BIWAN:");
        H5 TextoSobreBIWAN = new H5("BIWAN es un banco nuevo que ofrece una amplia gama de productos y servicios financieros a particulares y empresas. Fundado hace 1 año, BIWAN se compromete con la excelencia y la innovación. Nuestra misión es ayudar a nuestros clientes a alcanzar sus metas financieras y a hacer crecer sus negocios de manera sostenible. En BIWAN valoramos la integridad, la transparencia y el respeto hacia nuestros clientes, empleados y comunidades.");
        HorizontalLayout layoutProductosServicios = new HorizontalLayout();
        H4 Segundo = new H4("2.");
        H4 ProductosServicios = new H4("Productos y servicios:");
        H5 TextoProductosServicios = new H5("En BIWAN ofrecemos una amplia variedad de productos y servicios financieros para satisfacer las necesidades de nuestros clientes. Algunos de los productos y servicios que ofrecemos son:");
        H5 TextoProductosServicios1 = new H5("• Cuentas de ahorro: Ofrecemos cuentas de ahorro con diferentes tasas de interés y opciones de moneda para que nuestros clientes elijan la que más se ajuste a sus necesidades.");
        H5 TextoProductosServicios2 = new H5("• Tarjetas: Ofrecemos tarjetas con diferentes beneficios y programas de lealtad para que nuestros clientes elijan la que más se ajuste a sus necesidades.");
        H5 TextoProductosServicios3 = new H5("• Banca en línea: Ofrecemos una plataforma de banca en línea segura y fácil de usar para que nuestros clientes puedan realizar transacciones y acceder a sus cuentas desde cualquier lugar.");
        HorizontalLayout layoutSeguridadProteccionDatos = new HorizontalLayout();
        H4 Tercero = new H4("3.");
        H4 SeguridadProteccionDatos = new H4("Seguridad y protección de datos:");
        H5 TextoSeguridadProteccionDatos = new H5("En BIWAN entendemos la importancia de proteger la información y los datos de nuestros clientes. Por eso, implementamos medidas de seguridad de vanguardia para garantizar la confidencialidad y la integridad de la información de nuestros clientes. Algunas de las medidas que tomamos son:");
        H5 TextoSeguridadProteccionDatos1 = new H5("• Encriptación de datos: Todos los datos sensibles que manejamos, como números de cuenta y contraseñas, se encriptan para evitar que sean accedidos por terceros no autorizados. Esto significa que la información se convierte en un código que solo puede ser desencriptado por personas autorizadas con acceso a la clave de encriptación. De esta manera, aunque alguien intentara acceder a la información de manera no autorizada, no podría interpretarla ni utilizarla.");
        H5 TextoSeguridadProteccionDatos2 = new H5("• Monitoreo y detección de actividad sospechosa: Utilizamos tecnología avanzada de monitoreo y detección de actividad sospechosa para identificar y bloquear cualquier actividad no autorizada en las cuentas de nuestros clientes. Si detectamos algo sospechoso, tomamos medidas inmediatas para proteger la cuenta y notificamos al cliente de inmediato para que tome medidas de protección adicionales si es necesario.");
        HorizontalLayout layoutTarifasCargos = new HorizontalLayout();
        H4 Cuarto = new H4("4.");
        H4 TarifasCargos = new H4("Tarifas y cargos:");
        H5 TextoTarifasCargos = new H5("En BIWAN tratamos de ser transparentes y claros sobre las tarifas y cargos aplicables a nuestros productos y servicios. A continuación, te presentamos algunos ejemplos de las tarifas y cargos que podrían aplicarse a nuestros productos y servicios:");
        H5 TextoTarifasCargos1 = new H5("• Tarifa de mantenimiento de cuenta: Cobramos una tarifa mensual de mantenimiento de cuenta por el uso de nuestras cuentas de ahorro y corriente. Esta tarifa se aplica si el saldo de la cuenta es inferior a un determinado umbral durante el mes.");
        H5 TextoTarifasCargos2 = new H5("• Tarifa de retiro de efectivo: Cobramos una tarifa por cada retiro de efectivo que se realice en un cajero automático que no pertenezca a nuestra red. Esta tarifa se aplica para proteger a nuestros clientes de posibles fraudes y para cubrir los costos asociados con el procesamiento de retiros en cajeros automáticos externos.");
        H5 TextoTarifasCargos3 = new H5("• Tarifa de pago tardío: Cobramos una tarifa por cada pago tardío en nuestras tarjetas de crédito. Esta tarifa se aplica para proteger a nuestros clientes de posibles intereses compuestos y para cubrir los costos asociados con el procesamiento de pagos tardíos.");
        H5 TextoTarifasCargos4 = new H5("• Tarifa de cancelación de servicio: Cobramos una tarifa de cancelación de servicio a nuestros clientes que deciden cerrar su cuenta antes de un determinado plazo. Esta tarifa se aplica para cubrir los costos asociados con el procesamiento de la cancelación de la cuenta y para proteger a nuestros clientes de posibles penalizaciones por cancelación temprana.");
        H5 TextoTarifasCargosFinal = new H5("Es importante destacar que todas estas tarifas y cargos están sujetos a cambios y se pueden consultar en nuestro sitio web o en nuestra sección de preguntas frecuentes. También puedes ponerte en contacto con nuestro servicio de atención al cliente para obtener más información.");
        HorizontalLayout layoutAccesibilidad = new HorizontalLayout();
        H4 Quinto = new H4("5.");
        H4 Accesibilidad = new H4("Accesibilidad:");
        H5 TextoAccesibilidad = new H5("En BIWAN estamos comprometidos con hacer nuestros productos y servicios accesibles a todos, incluyendo a personas con discapacidad. Algunas de las medidas que tomamos para mejorar la accesibilidad de nuestros productos y servicios son:");
        H5 TextoAccesibilidad1 = new H5("• Sitio web accesible: Nuestro sitio web cumple con los estándares de accesibilidad web para garantizar que todos los usuarios puedan acceder a la información y las funcionalidades de manera fácil y conveniente. Utilizamos etiquetas semánticas y diseños adaptativos para hacer que nuestro sitio sea compatible con lectores de pantalla y dispositivos de asistencia.");
        H5 TextoAccesibilidad2 = new H5("• Asesoramiento personalizado: Contamos con un equipo de asesores especializados en atender a personas con discapacidad y brindarles un asesoramiento personalizado sobre nuestros productos y servicios. Nuestros asesores están disponibles para atender a nuestros clientes por teléfono o a través de nuestro servicio de chat en línea.");
        HorizontalLayout layoutSustentabilidad = new HorizontalLayout();
        H4 Sexto = new H4("6.");
        H4 Sustentabilidad = new H4("Sustentabilidad:");
        H5 TextoSustentabilidad = new H5("En BIWAN estamos comprometidos con promover prácticas de negocio responsable y sostenibles que minimicen nuestro impacto ambiental y contribuyan al desarrollo sostenible de nuestras comunidades. Algunas de las iniciativas que hemos llevado a cabo para lograr esto son:");
        H5 TextoSustentabilidad1 = new H5("• Eficiencia energética: Hemos implementado medidas para reducir el consumo de energía en nuestras sucursales, como la instalación de lámparas LED y el uso de sistemas de aire acondicionado y calefacción de alta eficiencia. También hemos incentivado el uso de medios de transporte sostenibles entre nuestros empleados.");
        H5 TextoSustentabilidad2 = new H5("• Reducción de residuos: Hemos implementado programas de reciclaje y reutilización de materiales en nuestras sucursales y oficinas para reducir la generación de residuos. También hemos adoptado prácticas de impresión sostenible, como el uso de papel reciclado y la impresión por ambas caras.");
        H5 TextoSustentabilidad3 = new H5("• Responsabilidad social: Hemos establecido alianzas con organizaciones sin fines de lucro y hemos apoyado proyectos de responsabilidad social en nuestras comunidades. Por ejemplo, hemos patrocinado campañas de recaudación de fondos para proyectos de construcción de escuelas y hemos participado en campañas de limpieza de playas y parques públicos.");
        H5 TextoSustentabilidad4 = new H5("• Financiamiento sostenible: Hemos establecido líneas de financiamiento especiales para empresas y proyectos que promuevan la sostenibilidad y el desarrollo sostenible, como proyectos de energías renovables o de conservación de la biodiversidad. También ofrecemos productos financieros sostenibles, como fondos de inversión que exclusivamente invierten en empresas con prácticas sostenibles.");
        HorizontalLayout layoutProteccionConsumidor = new HorizontalLayout();
        H4 Septimo = new H4("7.");
        H4 ProteccionConsumidor = new H4("Protección al consumidor:");
        H5 TextoProteccionConsumidor = new H5("En BIWAN entendemos que la protección al consumidor es esencial para fomentar la confianza y la lealtad de nuestros clientes. Por eso, tomamos medidas para proteger a nuestros clientes de prácticas engañosas o abusivas y para asegurarnos de que nuestros productos y servicios cumplen con los estándares de calidad y seguridad. Algunas de las medidas que tomamos para proteger a nuestros clientes son:");
        H5 TextoProteccionConsumidor1 = new H5("• Cláusulas de transparencia: Nuestros contratos y documentos incluyen cláusulas de transparencia que detallan de manera clara y concisa las condiciones y tarifas aplicables a nuestros productos y servicios. Esto permite a nuestros clientes tomar decisiones informadas y evitar sorpresas desagradables.");
        H5 TextoProteccionConsumidor2 = new H5("• Servicio de atención al cliente: Contamos con un servicio de atención al cliente disponible las 24 horas del día para atender a cualquier pregunta o problema que puedan tener nuestros clientes. Nuestros agentes están capacitados para brindar un servicio amable y eficiente y para resolver cualquier problema de manera rápida y satisfactoria.");
        H5 TextoProteccionConsumidor3 = new H5("• Protección contra el fraude: Utilizamos tecnología avanzada de detección de fraude para proteger a nuestros clientes de intentos de robo de identidad y de actividad fraudulenta en sus cuentas. Si detectamos algo sospechoso, tomamos medidas inmediatas para proteger la cuenta y notificamos al cliente de inmediato para que tome medidas de protección adicionales si es necesario.");
        H5 TextoProteccionConsumidor4 = new H5("• Garantías y devoluciones: Ofrecemos garantías y políticas de devolución en nuestros productos y servicios para proteger a nuestros clientes de problemas de calidad o de cumplimiento de las expectativas. Si un cliente no está satisfecho con un producto o servicio que ha adquirido, puede solicitar un reembolso o un cambio siempre y cuando cumpla con los términos y condiciones aplicables.");
        HorizontalLayout layoutContactos = new HorizontalLayout();
        H4 Octavo = new H4("8.");
        H4 Contactos = new H4("Contactos:");
        H5 TextoContactos = new H5("En BIWAN valoramos la comunicación y el diálogo con nuestros clientes y proveedores. Por eso, te presentamos a continuación algunos de los canales de contacto que tenemos disponibles para que puedas comunicarte con nosotros de manera fácil y conveniente:");
        H5 TextoContactos1 = new H5("• Chat en línea: Si prefieres comunicarte a través de internet, puedes utilizar nuestro servicio de chat en línea. Nuestro servicio de chat está disponible de lunes a viernes de 9:00 a 21:00 y sábados de 10:00 a 14:00.");
        H5 TextoContactos2 = new H5("• Correo electrónico: Si prefieres enviarnos un mensaje escrito, puedes utilizar nuestro correo electrónico \"biwan@gmail.com\"");

        //ADD
        layoutSobreBIWAN.add(Primero, SobreBIWAN);
        layoutProductosServicios.add(Segundo, ProductosServicios);
        layoutSeguridadProteccionDatos.add(Tercero, SeguridadProteccionDatos);
        layoutTarifasCargos.add(Cuarto, TarifasCargos);
        layoutAccesibilidad.add(Quinto, Accesibilidad);
        layoutSustentabilidad.add(Sexto, Sustentabilidad);
        layoutProteccionConsumidor.add(Septimo, ProteccionConsumidor);
        layoutContactos.add(Octavo, Contactos);
        layoutTextoMasInformacion.add(Titulo, layoutSobreBIWAN, TextoSobreBIWAN, layoutProductosServicios, TextoProductosServicios, TextoProductosServicios1, TextoProductosServicios2, TextoProductosServicios3, layoutSeguridadProteccionDatos, TextoSeguridadProteccionDatos, TextoSeguridadProteccionDatos1, TextoSeguridadProteccionDatos2, layoutTarifasCargos, TextoTarifasCargos, TextoTarifasCargos1, TextoTarifasCargos2, TextoTarifasCargos3, TextoTarifasCargos4, TextoTarifasCargosFinal, layoutAccesibilidad, TextoAccesibilidad, TextoAccesibilidad1, TextoAccesibilidad2, layoutSustentabilidad, TextoSustentabilidad, TextoSustentabilidad1, TextoSustentabilidad2, TextoSustentabilidad3, TextoSustentabilidad4, layoutProteccionConsumidor, TextoProteccionConsumidor, TextoProteccionConsumidor1, TextoProteccionConsumidor2, TextoProteccionConsumidor3, TextoProteccionConsumidor4, layoutContactos, TextoContactos, TextoContactos1, TextoContactos2);
        layoutMasInformacion.add(HeaderView.Header(), layoutTextoMasInformacion, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        Primero.addClassName("Numero");
        Segundo.addClassName("Numero");
        Tercero.addClassName("Numero");
        Cuarto.addClassName("Numero");
        Quinto.addClassName("Numero");
        Sexto.addClassName("Numero");
        Septimo.addClassName("Numero");
        Octavo.addClassName("Numero");
        SobreBIWAN.addClassName("SubtituloMasInformacion");
        ProductosServicios.addClassName("SubtituloMasInformacion");
        SeguridadProteccionDatos.addClassName("SubtituloMasInformacion");
        TarifasCargos.addClassName("SubtituloMasInformacion");
        Accesibilidad.addClassName("SubtituloMasInformacion");
        Sustentabilidad.addClassName("SubtituloMasInformacion");
        ProteccionConsumidor.addClassName("SubtituloMasInformacion");
        Contactos.addClassName("SubtituloMasInformacion");
        TextoSobreBIWAN.addClassNames("textoMasInformacion");
        TextoProductosServicios.addClassNames("textoMasInformacion");
        TextoProductosServicios1.addClassNames("textoMasInformacion", "tab");
        TextoProductosServicios2.addClassNames("textoMasInformacion", "tab");
        TextoProductosServicios3.addClassNames("textoMasInformacion", "tab");
        TextoSeguridadProteccionDatos.addClassNames("textoMasInformacion");
        TextoSeguridadProteccionDatos1.addClassNames("textoMasInformacion", "tab");
        TextoSeguridadProteccionDatos2.addClassNames("textoMasInformacion", "tab");
        TextoTarifasCargos.addClassNames("textoMasInformacion");
        TextoTarifasCargos1.addClassNames("textoMasInformacion", "tab");
        TextoTarifasCargos2.addClassNames("textoMasInformacion", "tab");
        TextoTarifasCargos3.addClassNames("textoMasInformacion", "tab");
        TextoTarifasCargos4.addClassNames("textoMasInformacion", "tab");
        TextoTarifasCargosFinal.addClassNames("textoMasInformacion");
        TextoAccesibilidad.addClassNames("textoMasInformacion");
        TextoAccesibilidad1.addClassNames("textoMasInformacion", "tab");
        TextoAccesibilidad2.addClassNames("textoMasInformacion", "tab");
        TextoSustentabilidad.addClassNames("textoMasInformacion");
        TextoSustentabilidad1.addClassNames("textoMasInformacion", "tab");
        TextoSustentabilidad2.addClassNames("textoMasInformacion", "tab");
        TextoSustentabilidad3.addClassNames("textoMasInformacion", "tab");
        TextoSustentabilidad4.addClassNames("textoMasInformacion", "tab");
        TextoProteccionConsumidor.addClassNames("textoMasInformacion");
        TextoProteccionConsumidor1.addClassNames("textoMasInformacion", "tab");
        TextoProteccionConsumidor2.addClassNames("textoMasInformacion", "tab");
        TextoProteccionConsumidor3.addClassNames("textoMasInformacion", "tab");
        TextoProteccionConsumidor4.addClassNames("textoMasInformacion", "tab");
        TextoContactos.addClassNames("textoMasInformacion");
        TextoContactos1.addClassNames("textoMasInformacion", "tab");
        TextoContactos2.addClassNames("textoMasInformacion", "tab");
        layoutTextoMasInformacion.addClassName("layoutTextoMasInformacion");

        //ALIGNMENT
        layoutTextoMasInformacion.setWidth("50%");
        layoutMasInformacion.expand(layoutTextoMasInformacion);
        layoutTextoMasInformacion.setAlignItems(Alignment.START);
        layoutMasInformacion.setAlignItems(Alignment.CENTER);
        layoutMasInformacion.setSizeFull();

        add(layoutMasInformacion);
    }
}
