package com.masantes.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.masantes.client.activityMappers.MainContentActivityMapper;
import com.masantes.client.clientFactory.ClientFactory;
import com.masantes.client.clientFactory.ClientFactoryImp;
import com.masantes.client.gin.MyGinJector;
import com.masantes.client.places.HomePlace;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Masantes implements EntryPoint 
{
	public static final Logger log = Logger.getLogger(Masantes.class.getName());
	final Place defaultPlace = new HomePlace();
	
	/*  L A Y O U T  */
	private HTMLPanel mainContent;
	
	/*  E V E N T	T H I N G S  */
	interface MyEventBinder extends EventBinder<Masantes> {};
	final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
	
	/*  E S S E N T I A L S  */
	private ClientFactory clientFactory = GWT.create(ClientFactoryImp.class); 
	private PlaceHistoryHandler placeHistoryHandler;
	private final MyGinJector inyector = GWT.create(MyGinJector.class);
		
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		establishGlobalExceptionHandler();
		onModuleLoadSafe();
	}
	
	public void onModuleLoadSafe()
	{
		EventBus eventBus = clientFactory.getEventBus();
		eventBinder.bindEventHandlers(this, eventBus);
		final MyPlaceController placeController = clientFactory.getPlaceController();
		final PlaceHistoryMapper historyMapper = clientFactory.getPlaceHistoryMapper();		
		
		/* Creamos el Layout de la aplicacion */
		mainContent = new HTMLPanel("");
        mainContent.setStylePrimaryName("mainContent");
			
		AcceptsOneWidget mainContentDisplay = new AcceptsOneWidget() {
			public void setWidget(IsWidget activityWidget) {
				Widget widget = Widget.asWidgetOrNull(activityWidget);
				if(widget!=null){
					mainContent.clear();
					mainContent.add(widget);					
				}
			}
		};
		
		MainContentActivityMapper mainContentMapper= new MainContentActivityMapper(clientFactory);
		ActivityManager mainContentActivityManager = new ActivityManager(mainContentMapper, eventBus);
		mainContentActivityManager.setDisplay(mainContentDisplay);
		
		/* Iniciamos el mecanismo de control del historial de navegación */
        placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
        placeHistoryHandler.register(placeController, eventBus, defaultPlace);
        placeHistoryHandler.handleCurrentHistory();

        /* Añadimos el layout al panel principal */
       RootPanel.get().add(clientFactory.getMenuView().asWidget());
       RootPanel.get().add(mainContent);
	}
	
	private void establishGlobalExceptionHandler()
	{
	    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() 
	    {
			@Override
			public void onUncaughtException(Throwable arg0) 
			{
				/* Se procesa el mensaje de error */
				String msg = Utils.getMessage(arg0);
				
			    if(GWT.isClient()) 
			    	GWT.log(msg); /* En modo desarollo se imprime el mensaje de error en consola */
			    else 
			    	log.log(Level.SEVERE, msg); /* En modo producción se envía el mensaje de error al servidor */
			}
		});
	}
}


