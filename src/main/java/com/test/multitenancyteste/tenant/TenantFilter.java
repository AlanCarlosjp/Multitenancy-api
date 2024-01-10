package com.test.multitenancyteste.tenant;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@Order(1)
public class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String tenantName = req.getHeader("X-TenantID");
        TenantContext.setCurrentTenant(tenantName);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("Erro no X-TenantID");
        } finally {
            TenantContext.setCurrentTenant("");
        }

    }
}
