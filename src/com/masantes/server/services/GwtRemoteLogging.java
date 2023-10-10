package com.masantes.server.services;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GwtRemoteLogging extends RemoteServiceServlet implements RemoteLoggingService 
{
	private static final long serialVersionUID = 7924637955070632729L;
	
	/** The Constant logger. */
    public static final Logger log = Logger.getLogger(GwtRemoteLogging.class.getSimpleName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * Logs a Log Record which has been serialized using GWT RPC on the server.
     * 
     * @return either an error message, or null if logging is successful.
     */
    public final String logOnServer(LogRecord lr) 
    {
        try 
        {
            if (lr.getLevel().equals(Level.SEVERE)) {
            	log.log(Level.SEVERE, lr.getLoggerName()+": " + lr.getMessage(), lr.getThrown());
            } 
            else if (lr.getLevel().equals(Level.INFO)) {
            	log.log(Level.INFO, lr.getLoggerName()+": " +lr.getMessage(), lr.getThrown());
            } 
            else if (lr.getLevel().equals(Level.WARNING)) {
            	log.log(Level.WARNING, lr.getLoggerName()+": " +lr.getMessage(), lr.getThrown());
            }
            else if (lr.getLevel().equals(Level.FINE)) {
                log.log(Level.FINE, lr.getLoggerName()+": " +lr.getMessage(), lr.getThrown());
            } 
            else {
                log.log(Level.FINER, lr.getLoggerName()+": " + lr.getMessage(), lr.getThrown());
            }
        } 
        catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return "Remote logging failed, check stack trace for details.";
        }
        return null;
    }
}
       

