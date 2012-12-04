Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos', 'StoreGruposAsignaturasSinAsignar', 'StoreConfiguracion' ],
    model : [ 'Configuracion' ],
    refs : [
    {
        selector : 'panelHorarios filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector : 'selectorGrupos',
        ref : 'selectorGrupos'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    },
    {
        selector : 'panelCalendarioDetalle',
        ref : 'panelCalendarioDetalle'
    },
    {
        selector : 'selectorCalendarios',
        ref : 'selectorCalendarios'
    } ],

    init : function()
    {
        this.control(
        {
            'panelHorarios filtroGrupos combobox[name=grupo]' :
            {
                select : this.refreshCalendar
            },

            'panelHorarios' :
            {
                refreshCalendar : this.refreshCalendar,
                render : this.onPanelCalendarioRendered
            },
            'selectorCalendarios checkbox' :
            {
                change : this.refreshCalendar
            },
            'selectorGrupos button' :
            {
                click : this.addEvento
            },
            'panelCalendario' :
            {
                eventresize : this.updateEvento,
                dayclick : function()
                {
                    return false;
                },
                beforeeventdelete : function(cal, rec)
                {
                    this.deleteEvento(cal, rec);
                    return false;
                },
                eventdivide : function(cal, rec)
                {
                    this.duplicarEvento(cal, rec);
                },
                rangeselect : function()
                {
                    return false;
                },
                eventasignaaula : function(cal, rec)
                {
                    this.mostrarVentanaAsignarAulaAEvento();
                }
            },
            'panelHorarios button[name=calendarioDetalle]' :
            {
                click : this.refreshCalendarDetalle

            },
            'panelHorarios button[name=calendarioGenerica]' :
            {
                click : this.refreshCalendar
            }
        });
    },

    refreshCalendarDetalle : function()
    {
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().down('combobox[name=grupo]');

        if (grupos.getValue() != null)
        {
            var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

            var storeEventos = this.getStoreEventosStore();
            storeEventos.getProxy().extraParams["estudioId"] = titulaciones.getValue();
            storeEventos.getProxy().extraParams["cursoId"] = cursos.getValue();
            storeEventos.getProxy().extraParams["semestreId"] = semestres.getValue();
            storeEventos.getProxy().extraParams["grupoId"] = grupos.getValue();
            storeEventos.getProxy().extraParams["calendariosIds"] = calendarios;

            var storeConfiguracion = this.getStoreConfiguracionStore();

            var ref = this;
            storeConfiguracion.load(
            {
                params :
                {
                    estudioId : titulaciones.getValue(),
                    cursoId : cursos.getValue(),
                    semestreId : semestres.getValue(),
                    grupoId : grupos.getValue()
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
                            panelCalendario = ref.getPanelCalendarioDetalle();
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

                        var eventos = Ext.create('HOR.store.StoreEventos');
                        Extensible.calendar.data.EventModel.reconfigure();

                        panelPadre.add(
                        {
                            xtype : 'panelCalendarioDetalle',
                            eventStore : eventos,
                            showMultiDayView : true,
                            viewConfig :
                            {
                                viewStartHour : horaInicio,
                                viewEndHour : horaFin
                            },
                            listeners :
                            {
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
    },

    refreshCalendar : function()
    {
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().down('combobox[name=grupo]');

        if (grupos.getValue() != null)
        {
            var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

            var storeEventos = this.getStoreEventosStore();
            storeEventos.getProxy().extraParams["estudioId"] = titulaciones.getValue();
            storeEventos.getProxy().extraParams["cursoId"] = cursos.getValue();
            storeEventos.getProxy().extraParams["semestreId"] = semestres.getValue();
            storeEventos.getProxy().extraParams["grupoId"] = grupos.getValue();
            storeEventos.getProxy().extraParams["calendariosIds"] = calendarios;

            var storeConfiguracion = this.getStoreConfiguracionStore();

            var ref = this;
            storeConfiguracion.load(
            {
                params :
                {
                    estudioId : titulaciones.getValue(),
                    cursoId : cursos.getValue(),
                    semestreId : semestres.getValue(),
                    grupoId : grupos.getValue()
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
                            panelCalendario = ref.getPanelCalendarioDetalle();
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

                        var eventos = Ext.create('HOR.store.StoreEventos');
                        Extensible.calendar.data.EventModel.reconfigure();

                        panelPadre.add(
                        {
                            xtype : 'panelCalendario',
                            eventStore : eventos,
                            showMultiDayView : true,
                            viewConfig :
                            {
                                viewStartHour : horaInicio,
                                viewEndHour : horaFin
                            },
                            listeners :
                            {
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
    },

    addEvento : function(button)
    {
        var grupoId = button.grupoAsignaturaId;
        var storeGrupo = this.getStoreGruposAsignaturasSinAsignarStore();
        var record = storeGrupo.getById(grupoId);
        record.set('asignado', true);
        var ref = this;
        storeGrupo.sync(
        {
            callback : function()
            {
                button.destroy();
                ref.getPanelCalendario().getActiveView().refresh(true);
            }
        });
    },

    updateEvento : function(calendario, registro)
    {
        var storeEventos = this.getStoreEventosStore();
        storeEventos.sync();
    },

    deleteEvento : function(calendario, registro)
    {
        var calendario = this.getPanelCalendario();
        var store = calendario.store;
        store.remove(registro);

        store.sync(
        {
            success : function()
            {
                this.getSelectorGrupos().fireEvent("updateGrupos");
            },
            scope : this
        });
    },

    duplicarEvento : function(calendario, registro)
    {
        var calendario = this.getPanelCalendario();

        Ext.Ajax.request(
        {
            url : '/hor/rest/calendario/eventos/generica/divide/' + registro.get("EventId"),
            method : 'POST',
            success : function(response)
            {
                calendario.getActiveView().refresh(true);
            },
            failure : function(response)
            {
                if (response.responseXML)
                {
                    var msgList = response.responseXML.getElementsByTagName("msg");

                    if (msgList && msgList[0] && msgList[0].firstChild)
                    {
                        Ext.MessageBox.show(
                        {
                            title : 'Server error',
                            msg : msgList[0].firstChild.nodeValue,
                            icon : Ext.MessageBox.ERROR,
                            buttons : Ext.Msg.OK
                        });
                    }
                }
            }
        });
    },
    
    onPanelCalendarioRendered : function()
    {
        var ref = this;

        var panelCalendario = ref.getPanelCalendario();
        var panelPadre = panelCalendario.up('panel');

        Ext.Array.each(Ext.ComponentQuery.query('panelCalendario'), function(panel)
        {
            panel.destroy();
        });

        var eventos = Ext.create('HOR.store.StoreEventos');
        Extensible.calendar.data.EventModel.reconfigure();

        panelPadre.add(
        {
            xtype : 'panelCalendario',
            eventStore : eventos,
            showMultiDayView : true,
            viewConfig :
            {
                viewStartHour : 8,
                viewEndHour : 22
            }
        });
    },
    
    mostrarVentanaAsignarAulaAEvento : function()
    {
        Ext.ComponentQuery.query('panelCalendario')[0].showAsignarAulaView();
        
    }

});