package org.ferris.forward.console.retry;

import java.io.Serializable;
import static java.lang.String.format;
import static java.lang.Thread.sleep;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import static javax.interceptor.Interceptor.Priority.APPLICATION;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Interceptor
@ExceptionRetry
@Priority(APPLICATION)
public class ExceptionRetryInterceptor implements Serializable {

    private static final long serialVersionUID = 6479164641981641654L;
 
    @Inject
    protected Logger log;
    
    @AroundInvoke
     public Object retryIfExceptionCaught(InvocationContext ctx) throws Exception {
         Exception caught = null;
         for (int i=1, imax=4; i<=imax; i++) {
             try {
                 return ctx.proceed();
             } catch (Exception e) {
                 caught = e;
                 log.warn(format("Exception caught on attempt %d of %d", i, imax), e);
             }
             try {
                 sleep(1000 * 5);
             } catch (InterruptedException e) {}
         }
         throw caught;
     }
}
