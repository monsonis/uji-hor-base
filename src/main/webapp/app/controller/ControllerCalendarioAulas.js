Ext.define('HOR.controller.ControllerCalendarioAulas',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreAulasDetalle', 'StoreSemestreDetalles' ],
    refs : [
    {
        selector : 'selectorAulas',
        ref : 'selectorAulas'
    },
    {
        selector : 'panelCalendarioPorAula',
        ref : 'panelCalendarioPorAula'
    },
    {
        selector : 'panelCalendarioDetallePorAula',
        ref : 'panelCalendarioDetallePorAula'
    },
    {
        selector : 'panelCalendarioAulas selectorCalendarios',
        ref : 'selectorCalendarios'
    },
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
    },
    {
        selector : 'filtroAulas button[name=calendarioAulasDetalle]',
        ref : 'botonCalendarioDetalle'
    },
    {
        selector : 'filtroAulas button[name=calendarioAulasGenerica]',
        ref : 'botonCalendarioGenerica'
    }, 
    {
        selector : 'filtroAulas combobox[name=semestre]',
        ref : 'comboSemestre'
    },
    {
        selector : 'panelCalendarioAulas button[name=imprimir]',
        ref : 'botonImprimir'
    }],

    init : function()
    {
        this.control(
        {
            'selectorAulas button' :
            {
                click : function(button)
                {
                    this.getBotonImprimir().show();
                    this.getBotonCalendarioDetalle().show();
                    this.getBotonCalendarioGenerica().show();
                    
                    this.refreshEventsCalendarFromSelectorAulas(button);
                }
            },
            'panelCalendarioAulas selectorCalendarios checkbox' :
            {
                change : this.refreshEventsCalendarFromSelectorCalendariosOrBotones
            },
            'filtroAulas button[name=calendarioAulasDetalle]' :
            {
                click : function(button)
                {
                    if (!button.pressed)
                    {
                        button.toggle();
                    }
                    var otherButton = this.getBotonCalendarioGenerica();
                    if (otherButton.pressed)
                    {
                        otherButton.toggle();
                    }
                    this.refreshEventsCalendarFromSelectorCalendariosOrBotones();
                }
            },
            'filtroAulas button[name=calendarioAulasGenerica]' :
            {
                click : function(button)
                {
                    if (!button.pressed)
                    {
                        button.toggle();
                    }
                    var otherButton = this.getBotonCalendarioDetalle();
                    if (otherButton.pressed)
                    {
                        otherButton.toggle();
                    }
                    this.refreshEventsCalendarFromSelectorCalendariosOrBotones();
                }
            },
            'panelCalendarioAulas button[name=imprimir]' :
            {
                click : this.imprimirCalendario
            }
        });
    },

    refreshEventsCalendarGenerica : function(aulaId, panelTitulo, semestre, calendarios, panelPadre)
    {
        var eventos = Ext.create('HOR.store.StoreAulasGenerica');
        Extensible.calendar.data.EventModel.reconfigure();

        var params =
        {
            aulaId : aulaId,
            semestreId : semestre,
            calendariosIds : calendarios
        };
        eventos.getProxy().extraParams = params;

        panelPadre.add(
        {
            xtype : 'panelCalendarioPorAula',
            title : panelTitulo,
            eventStore : eventos,
            showMultiDayView : true,
            viewConfig :
            {
                viewStartHour : 8,
                viewEndHour : 22
            },
            listeners :
            {
                afterrender : function()
                {
                    eventos.load();
                }
            }
        });
    },

    refreshEventsCalendarDetalle : function(aulaId, panelTitulo, semestre, calendarios, panelPadre)
    {
        var eventos = Ext.create('HOR.store.StoreAulasDetalle');
        Extensible.calendar.data.EventModel.reconfigure();

        var inicio = this.getInicioSemestre();
        var fin = new Date();
        fin.setDate(inicio.getDate() + 7);

        var params =
        {
            aulaId : aulaId,
            semestreId : semestre,
            calendariosIds : calendarios,
            startDate : inicio,
            endDate : fin
        };
        eventos.getProxy().extraParams = params;

        var ref = this;
        panelPadre.add(
        {
            xtype : 'panelCalendarioDetallePorAula',
            title : panelTitulo,
            eventStore : eventos,
            showMultiDayView : true,
            viewConfig :
            {
                viewStartHour : 8,
                viewEndHour : 22
            },
            listeners :
            {
                afterrender : function()
                {
                    ref.getPanelCalendarioDetallePorAula().setStartDate(inicio);
                    eventos.load();
                }
            }
        });
    },

    refreshEventsCalendar : function(aulaId, aulaText)
    {
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

        var panelCalendario = this.getPanelCalendarioPorAula();
        if (!panelCalendario)
        {
            panelCalendario = this.getPanelCalendarioDetallePorAula();
        }

        if (aulaText)
        {
            panelTitulo = 'Ocupació Aula ' + aulaText + " Semestre " + semestre;
        }
        else
        {
            panelTitulo = panelCalendario.title;
        }

        var panelPadre = panelCalendario.up('panel');

        Ext.Array.each(Ext.ComponentQuery.query('panelCalendarioPorAula'), function(panel)
        {
            panel.destroy();
        });

        Ext.Array.each(Ext.ComponentQuery.query('panelCalendarioDetallePorAula'), function(panel)
        {
            panel.destroy();
        });

        if (this.getBotonCalendarioGenerica().pressed)
        {
            this.refreshEventsCalendarGenerica(aulaId, panelTitulo, semestre, calendarios, panelPadre);
        }
        else
        {
            this.refreshEventsCalendarDetalle(aulaId, panelTitulo, semestre, calendarios, panelPadre);
        }
    },

    refreshEventsCalendarFromSelectorAulas : function(button)
    {
        this.refreshEventsCalendar(button.aulaId, button.text);
    },

    refreshEventsCalendarFromSelectorCalendariosOrBotones : function()
    {
         var aulaId = this.getSelectedAulaId();
         if (aulaId){
            this.refreshEventsCalendar(aulaId);
         }

    },
    
    getSelectedAulaId : function()
    {
    	 if (this.getPanelCalendarioPorAula())
         {
             var store = this.getPanelCalendarioPorAula().store;
         }
         else if (this.getPanelCalendarioDetallePorAula())
         {
             var store = this.getPanelCalendarioDetallePorAula().store;
         }

         if (store && store.getProxy().extraParams['aulaId'] != null)
         {
             return store.getProxy().extraParams['aulaId'];
         } else {
        	 return null;
         }
    },

    getInicioSemestre : function()
    {
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var store = this.getStoreSemestreDetallesStore();

        for ( var i = 0; i < store.getCount(); i++)
        {
            var record = store.getAt(i);
            if (record.get('id') == semestre)
            {
                return record.get('fechaInicio');
            }
        }
        
        return new Date();
    },
    
    imprimirCalendario : function()
    {    	
        var aula = this.getSelectedAulaId();      
        var semestre = this.getComboSemestre().getValue();
        
        if (this.getBotonCalendarioGenerica().pressed){
        	window.open("http://www.uji.es/cocoon/xxxx/" + aula + "/" + semestre +  "/ocupacion-aula-generica.pdf");
        } else {
        	window.open("http://www.uji.es/cocoon/xxxx/" + aula + "/" + semestre +  "/ocupacion-aula-detalle.pdf");
        }

       
    }
});