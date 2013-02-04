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
        selector : 'panelCalendarioAulas selectorCalendarios',
        ref : 'selectorCalendarios'
    },
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
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
                click : this.refreshEventsCalendarFromSelectorAulas
            },
            'panelCalendarioAulas selectorCalendarios checkbox' :
            {
                change : this.refreshEventsCalendarFromSelectorCalendarios
            },
            'panelCalendarioAulas button[name=imprimir]' :
            {
                click : this.imprimirCalendario
            }
        });
    },

    refreshEventsCalendar : function(aulaId, aulaText)
    {
        // var aulaId = button.aulaId;
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var calendarios = this.getSelectorCalendarios().getCalendarsSelected();    
        
        this.getBotonImprimir().show();


        var panelCalendario = this.getPanelCalendarioPorAula();
        
        if (aulaText)
        {
            panelTitulo = 'Ocupació Aula ' +  aulaText + " Semestre " +  semestre;
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

        var eventos = Ext.create('HOR.store.StoreAulasDetalle');
        Extensible.calendar.data.EventModel.reconfigure();

        var inicio = this.getInicioSemestre();
        if (!inicio)
        {
            inicio = new Date();
        }
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
                    ref.getPanelCalendarioPorAula().setStartDate(inicio);
                    eventos.load();
                }
            }
        });
    },
    
    refreshEventsCalendarFromSelectorAulas : function(button)
    {
        this.refreshEventsCalendar(button.aulaId, button.text);
    },
    
    refreshEventsCalendarFromSelectorCalendarios : function()
    {
        var store = this.getPanelCalendarioPorAula().store;
        if (store.getProxy().extraParams['aulaId'] != null)
        {
            var aulaId = store.getProxy().extraParams['aulaId'];
            this.refreshEventsCalendar(aulaId);
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
    },
    
    imprimirCalendario : function()
    {    	
        var aula = this.getPanelCalendarioPorAula().store.getProxy().extraParams['aulaId'];        
        var semestre = this.getComboSemestre().getValue();
        

       window.open("http://www.uji.es/cocoon/xxxx/" + aula + "/" + semestre +  "/ocupacion-aula-detalle.pdf");

    }
});