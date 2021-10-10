package com.example.myspringbootplayground.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

// https://newbedev.com/spring-server-connection-timeout-not-working
// https://miensol.pl/spring-mvc-request-timeout/

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TimeoutFilter extends OncePerRequestFilter {
    private static final ScheduledExecutorService timeoutsPool = Executors.newScheduledThreadPool(10);
    private final long limitTimeOut = 10L;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        AtomicBoolean completed = new AtomicBoolean(false);
        Thread requestHandlingThread = Thread.currentThread();
        ScheduledFuture timeout = timeoutsPool.schedule((Runnable)(new Runnable() {
            public final void run() {
                if (completed.compareAndSet(false, true)) {
                    requestHandlingThread.interrupt();
                }

            }
        }), limitTimeOut, TimeUnit.SECONDS);

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            timeout.cancel(false);
        } catch(Exception e) {
            httpServletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "exceed " + limitTimeOut + " seconds");
        } finally {
            completed.set(true);
        }
    }
}
