package com.egg.biblio.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author CanoFrancisco
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("/admins")
public class AdminControler {

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

}
