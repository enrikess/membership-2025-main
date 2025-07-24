package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class InicioController extends BaseController {

    private final ConfiguracionWebService configuracionWebService;
    private final ProductoDestacadoWebService productoDestacadoWebService;
    private final ProductoNovedosoWebService productoNovedosoWebService;
    private final ProductoVisitadoWebService productoVisitadoWebService;
    private final ParticipanteWebService participanteWebService;

    @GetMapping
    public String home(Model model, Participante participante) {

//        if (participante.getCatalogo().getIdCatalogo() == 5) {
//            return "redirect:/catalogo";
//        }

        if (participante.getCatalogo().getIdCatalogo() == 5 ||
                participante.getCatalogo().getIdCatalogo() == 6 ||
                participante.getCatalogo().getIdCatalogo() == 7) {
            return "redirect:/catalogo";
        }

        List<ConfiguracionBanner> bannerInicio = configuracionWebService.listarBanner(
                ConstantesCommon.ZERO_VALUE,
                participante.getCatalogo().getIdCatalogo(),
                UtilEnum.ESTADO.ACTIVO.getCodigo(),
                UtilEnum.TIPO_BANNER.BANNER_HOME.getValue()
        );

        LocalDate date = LocalDate.now();
        Integer anio = date.getYear();

        model.addAttribute("bannerInicio", bannerInicio);
        model.addAttribute("anio", anio);
        model.addAttribute("popup", Util.getSession().getAttribute(ConstantesSesion.SESSION_POPUP_INICIO) != null);
        model.addAttribute("topbroker", participanteWebService.topBrokerListar(participante.getIdParticipante(), participante.getCatalogo().getIdCatalogo()));

        Util.getSession().removeAttribute(ConstantesSesion.SESSION_POPUP_INICIO);


        model.addAttribute("destacados", productoDestacadoWebService.listarProductoDestacadoWeb(
                        new FiltroProductoDestacado()
                                .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );

        model.addAttribute("novedades", productoNovedosoWebService.listarProductoNovedosoWeb(
                        new FiltroProductoNovedoso()
                                .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );

        model.addAttribute("visitados", productoVisitadoWebService.listarProductoVisitadoWeb(
                        new FiltroProductoVisitado()
                                .setIdCatalogo(participante.getCatalogo().getIdCatalogo())
                )
        );

        return ConstantesWebView.VIEW_INICIO;
    }

}
