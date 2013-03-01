Ext.define('HOR.controller.ControllerCalendarioCircuitos',
{
    extend : 'Ext.app.Controller',
    stores : [ /* 'StoreCalendariosCircuitos', */'StoreEventosCircuito', 'StoreConfiguracionAjustada' ],
    refs : [
    {
        selector : 'panelCircuitos filtroCircuitos',
        ref : 'filtroCircuitos'
    },
    {
        selector : 'panelCircuitos selectorCircuitos',
        ref : 'selectorCircuitos'
    },
    {
        selector : 'panelCircuitos panelCalendarioCircuito',
        ref : 'panelCalendarioCircuito'
    },
    {
        selector : 'panelCircuitos panelCalendarioDetalleCircuito',
        ref : 'panelCalendarioDetalleCircuito'
    },
    {
        selector : 'panelCircuitos selectorCalendariosCircuitos',
        ref : 'selectorCalendariosCircuitos'
    },
    {
        selector : 'panelCircuitos filtroCircuitos combobox[name=grupo]',
        ref : 'comboGrupos'
    },
    {
        selector : 'filtroCircuitos button[name=calendarioCircuitosDetalle]',
        ref : 'botonCalendarioDetalle'
    },
    {
        selector : 'filtroCircuitos button[name=calendarioCircuitosGenerica]',
        ref : 'botonCalendarioGenerica'
    },
    {
        selector : 'filtroCircuitos button[name=imprimir]',
        ref : 'botonImprimir'
    },
    {
        selector : 'filtroCircuitos button[name=validar]',
        ref : 'botonValidar'
    },
    {
        selector : 'panelCalendarioCircuitos',
        ref : 'panelCalendarioCircuitos'
    },
    {
        selector : 'panelCalendarioCircuitosDetalle',
        ref : 'panelCalendarioCircuitosDetalle'
    } ],

    init : function()
    {
        this.control(
        {
            'filtroCircuitos combobox[name=grupo]' :
            {
                select : function()
                {
                    if (this.getFiltroCircuitos().down('button[name=calendarioCircuitosDetalle]').pressed)
                    {
                        this.refreshCalendarDetalle();
                    }
                    else
                    {
                        this.refreshCalendar();
                    }
                }
            }
        });
    },

    showBotonesCalendario : function()
    {
        this.getBotonCalendarioDetalle().show();
        this.getBotonCalendarioGenerica().show();
        this.getBotonImprimir().show();
        this.getBotonValidar().show();
    },

    refreshCalendarDetalle : function()
    {
        var titulaciones = this.getFiltroCircuitos().down('combobox[name=estudio]');
        var cursos = this.getFiltroCircuitos().down('combobox[name=curso]');
        var semestres = this.getFiltroCircuitos().down('combobox[name=semestre]');
        var grupos = this.getFiltroCircuitos().down('combobox[name=grupo]');

        var calendarios = this.getSelectorCalendariosCircuitos().getCalendarsSelected();
        var storeConfiguracion = this.getStoreConfiguracionAjustadaStore();

        var ref = this;
        storeConfiguracion.load(
        {
            params :
            {
                estudioId : titulaciones.getValue(),
                cursoId : 1,
                semestreId : semestres.getValue(),
                gruposId : grupos.getValue()
            },
            scope : this,
            callback : function(records, operation, success)
            {
                if (success)
                {
                    var record = records[0];
                    var fechaInicio = record.get('horaInicio');
                    var fechaFin = record.get('horaFin');

                    var inicio = Ext.Date.parse(fechaInicio, 'd/m/Y H:i:s', true);
                    var fin = Ext.Date.parse(fechaFin, 'd/m/Y H:i:s', true);
                    var horaInicio = Ext.Date.format(inicio, 'H');
                    var horaFin = Ext.Date.format(fin, 'H');

                    var panelCalendario = ref.getPanelCalendario();
                    if (!panelCalendario)
                    {
                        panelCalendario = ref.getPanelCalendarioCircuitosDetalle();
                    }
                    var panelPadre = panelCalendario.up('panel');

                    Ext.Array.each(Ext.ComponentQuery.query('panelCalendario'), function(panel)
                    {
                        panel.destroy();
                    });

                    Ext.Array.each(Ext.ComponentQuery.query('panelCalendarioDetalle'), function(panel)
                    {
                        panel.destroy();
                    });

                    var eventos = Ext.create('HOR.store.StoreEventosDetalle');

                    Extensible.calendar.data.EventModel.reconfigure();
                    Ext.Ajax.request(
                    {
                        url : '/hor/rest/semestredetalle/estudio/' + titulaciones.getValue() + "/semestre/" + semestres.getValue(),
                        method : 'GET',
                        success : function(response)
                        {
                            var jsonResp = Ext.decode(response.responseText);
                            var cadenaFechaInicio = jsonResp.data[0].fechaInicio;
                            var inicio = Ext.Date.parse(cadenaFechaInicio, 'd/m/Y H:i:s', true);
                            var fin = new Date();
                            fin.setDate(inicio.getDate() + 7);

                            panelPadre.add(
                            {
                                xtype : 'panelCalendarioDetalle',
                                eventStore : eventos,
                                showMultiDayView : true,
                                startDate : inicio,
                                enableEditDetails : false,
                                viewConfig :
                                {
                                    viewStartHour : horaInicio,
                                    viewEndHour : horaFin
                                },

                                onInitDrag : function()
                                {

                                },
                                listeners :
                                {

                                    dayclick : function(dt, allday, el)
                                    {
                                        return false;
                                    },

                                    eventclick : function()
                                    {
                                        return false;
                                    },

                                    rangeselect : function()
                                    {
                                        return false;
                                    },

                                    afterrender : function()
                                    {
                                        params =
                                        {
                                            estudioId : titulaciones.getValue(),
                                            cursoId : cursos.getValue(),
                                            semestreId : semestres.getValue(),
                                            calendariosIds : calendarios,
                                            gruposId : grupos.getValue(),
                                            startDate : inicio,
                                            endDate : fin
                                        };

                                        eventos.getProxy().extraParams = params;
                                        this.setStartDate(inicio);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    },

    refreshCalendar : function()
    {
        var titulaciones = this.getFiltroCircuitos().down('combobox[name=estudio]');
        var semestres = this.getFiltroCircuitos().down('combobox[name=semestre]');
        var grupos = this.getFiltroCircuitos().down('combobox[name=grupo]');

        var calendarios = this.getSelectorCalendariosCircuitos().getCalendarsSelected();
        var storeConfiguracion = this.getStoreConfiguracionAjustadaStore();

        var ref = this;
        storeConfiguracion.load(
        {
            params :
            {
                estudioId : titulaciones.getValue(),
                cursoId : 1,
                semestreId : semestres.getValue(),
                gruposId : grupos.getValue()
            },
            scope : this,
            callback : function(records, operation, success)
            {
                if (success)
                {
                    var record = records[0];
                    var fechaInicio = record.get('horaInicio');
                    var fechaFin = record.get('horaFin');

                    var inicio = Ext.Date.parse(fechaInicio, 'd/m/Y H:i:s', true);
                    var fin = Ext.Date.parse(fechaFin, 'd/m/Y H:i:s', true);
                    var horaInicio = Ext.Date.format(inicio, 'H');
                    var horaFin = Ext.Date.format(fin, 'H');

                    var panelCalendario = ref.getPanelCalendarioCircuitos();
                    if (!panelCalendario)
                    {
                        panelCalendario = ref.getPanelCalendarioDetalle();
                    }
                    var panelPadre = panelCalendario.up('panel');

                    Ext.Array.each(Ext.ComponentQuery.query('panelCalendarioCircuitos'), function(panel)
                    {
                        panel.destroy();
                    });

                    Ext.Array.each(Ext.ComponentQuery.query('panelCalendarioCircuitosDetalle'), function(panel)
                    {
                        panel.destroy();
                    });

                    var eventos = Ext.create('HOR.store.StoreEventosCircuito');
                    Extensible.calendar.data.EventModel.reconfigure();

                    params =
                    {
                        estudioId : titulaciones.getValue(),
                        semestreId : semestres.getValue(),
                        calendariosIds : calendarios,
                        grupoId : grupos.getValue()
                    };
                    eventos.getProxy().extraParams = params;
                    panelPadre.add(
                    {
                        xtype : 'panelCalendarioCircuitos',
                        eventStore : eventos,
                        showMultiDayView : true,
                        viewConfig :
                        {
                            viewStartHour : horaInicio,
                            viewEndHour : horaFin
                        },
                        listeners :
                        {
                            eventover : function(calendar, event, el)
                            {
                                ref.mostrarInfoAdicionalEvento(event, el);
                                return true;
                            },

                            afterrender : function()
                            {
                                eventos.load();
                            }
                        }
                    });
                }
            }
        });
    }

});