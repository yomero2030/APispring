package org.springframework.samples.petclinic.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
    
    @GetMapping("/owner/")
    public String welcome_owner() {
        return "welcome_owner";
    }

    // handle 403 page
    @Component
    public static class MyAccessDeniedHandler implements AccessDeniedHandler {

        private static Logger logger = LoggerFactory.getLogger(MyAccessDeniedHandler.class);

        @Override
        public void handle(HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse,
                AccessDeniedException e) throws IOException, ServletException {

            Authentication auth
                    = SecurityContextHolder.getContext().getAuthentication();
            
            System.out.println(auth.toString());

            if (auth != null) {
                logger.info("User '" + auth.getName()
                        + "' attempted to access the protected URL: "
                        + httpServletRequest.getRequestURI());
            }

            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");

        }
    }
}
