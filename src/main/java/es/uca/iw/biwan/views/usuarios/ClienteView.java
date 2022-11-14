package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderClienteView;

@Route("pagina-principal-cliente")
@PageTitle("Página Principal Cliente")
public class ClienteView extends VerticalLayout {

    public ClienteView(){
        add(HeaderClienteView.Header());
        add(FooterView.Footer());
    }
}