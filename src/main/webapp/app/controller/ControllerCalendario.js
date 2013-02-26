Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos', 'StoreEventosDetalle', 'StoreGruposAsignaturasSinAsignar', 'StoreConfiguracionAjustada' ],
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
        selector : 'panelHorarios selectorCalendarios',
        ref : 'selectorCalendarios'
    },
    {
        selector : 'button[name=calendarioDetalle]',
        ref : 'botonCalendarioDetalle'
    },
    {
        selector : 'button[name=calendarioGenerica]',
        ref : 'botonCalendarioGenerica'
    },
    {
        selector : 'panelHorarios filtroGrupos combobox[name=grupo]',
        ref : 'comboGrupos'
    } ],

    init : function()
    {
        var ref = this;

        this.control(
        {
            'panelHorarios filtroGrupos combobox[name=grupo]' :
            {
                blur : function()
                {
                    if (ref.getComboGrupos().getValue() != '')
                    {
                        if (ref.getFiltroGrupos().down('button[name=calendarioDetalle]').pressed)
                        {
                            ref.refreshCalendarDetalle();
                        }
                        else
                        {
                            ref.refreshCalendar();
                        }
                    }
                }
            },

            'panelHorarios' :
            {
                refreshCalendar : this.refreshCalendar,
                refreshCalendarDetalle : this.refreshCalendarDetalle,
                render : this.onPanelCalendarioRendered
            },
            'panelHorarios selectorCalendarios checkbox' :
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
                },
                beforeeventmove : function(cal, rec, date)
                {
                    if (rec.get(Extensible.calendar.data.EventMappings.DetalleManual.name) == 'true')
                    {
                        return this.moverEventoConDetalleManual(cal, rec, date);
                    }
                }
            },
            'panelHorarios button[name=calendarioDetalle]' :
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
                    this.getSelectorGrupos().setVisible(false);
                    this.refreshCalendarDetalle();
                }

            },
            'panelHorarios button[name=calendarioGenerica]' :
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
                    this.getSelectorGrupos().setVisible(true);
                    this.refreshCalendar();
                }
            },
            'panelHorarios button[name=imprimir]' :
            {
                click : this.imprimirCalendario
            },
            'panelHorarios button[name=validar]' :
            {
                click : this.mostrarValidaciones
            }
        });
    },

    refreshCalendarDetalle : function()
    {
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().getGruposSelected();

        var calendarios = this.getSelectorCalendarios().getCalendarsSelected();
        var storeConfiguracion = this.getStoreConfiguracionAjustadaStore();

        var ref = this;
        storeConfiguracion.load(
        {
            params :
            {
                estudioId : titulaciones.getValue(),
                cursoId : cursos.getValue(),
                semestreId : semestres.getValue(),
                gruposId : grupos
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
                                            gruposId : grupos,
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
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().getGruposSelected();

        var calendarios = this.getSelectorCalendarios().getCalendarsSelected();
        var storeConfiguracion = this.getStoreConfiguracionAjustadaStore();

        var ref = this;
        storeConfiguracion.load(
        {
            params :
            {
                estudioId : titulaciones.getValue(),
                cursoId : cursos.getValue(),
                semestreId : semestres.getValue(),
                gruposId : grupos
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

                    params =
                    {
                        estudioId : titulaciones.getValue(),
                        cursoId : cursos.getValue(),
                        semestreId : semestres.getValue(),
                        calendariosIds : calendarios,
                        gruposId : grupos
                    };
                    eventos.getProxy().extraParams = params;

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
    },

    mostrarInfoAdicionalEvento : function(event, el)
    {
        var nombre = event.data.NombreAsignatura;
        var plazas = event.data.PlazasAula;
        var tooltipHtml = 'Assignatura: ' + nombre;
        if (plazas)
        {
            tooltipHtml += '<br/>Plazas aula: ' + plazas;
        }
        Ext.create('Ext.tip.ToolTip',
        {
            target : el,
            showDelay : 100,
            html : tooltipHtml
        });
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
    },

    imprimirCalendario : function()
    {
        var titulacion = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();

        if (this.getBotonCalendarioGenerica().pressed)
        {
            window.open("http://www.uji.es/cocoon/" + session + "/" + titulacion + "/" + curso + "/" + semestre + "/" + grupo + "/horario-semana-generica.pdf");
        }
        else
        {
            window.open("http://www.uji.es/cocoon/" + session + "/" + titulacion + "/" + curso + "/" + semestre + "/" + grupo + "/horario-semana-detalle.pdf");
        }
    },

    mostrarValidaciones : function()
    {
        var titulacion = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();

        window.open("http://www.uji.es/cocoon/" + session + "/" + titulacion + "/" + curso + "/" + semestre + "/" + grupo + "/validaciones-horarios.pdf");
    },

    moverEventoConDetalleManual : function(cal, rec, date)
    {
        var horaInicio = rec.get(Extensible.calendar.data.EventMappings.StartDate.name);
        var horaFin = rec.get(Extensible.calendar.data.EventMappings.EndDate.name);
        
        var me = this.getPanelCalendario();

        if (Extensible.Date.diffDays(horaInicio, date) != 0)
        {
            Ext.Msg.confirm('Event amb detall manual', 'Aquest event té detall manual, si el cambies de dia es perdrà aquest detall. Estàs segur de voler continuar?', function(btn, text)
            {
                if (btn == 'yes')
                {
                    rec.set(Extensible.calendar.data.EventMappings.StartDate.name, date);
                    
                    var diff = Extensible.Date.diff(horaFin, horaInicio);
                    var endDate = Extensible.Date.add(date, diff);
                    rec.set(Extensible.calendar.data.EventMappings.EndDate.name, endDate);
                    
                    me.getEventStore().save();
                }
            });
            
            return false;
        }
        
        return true;
    }

});