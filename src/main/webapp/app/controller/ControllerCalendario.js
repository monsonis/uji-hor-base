Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos', 'StoreEventosDetalle', 'StoreGruposAsignaturasSinAsignar', 'StoreConfiguracion' ],
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
    } ],

    init : function()
    {
        var ref = this;

        this.control(
        {
            'panelHorarios filtroGrupos combobox[name=grupo]' :
            {
                select : function()
                {
                    if (ref.getFiltroGrupos().down('button[name=calendarioDetalle]').pressed)
                    {
                        ref.refreshCalendarDetalle();
                    } else {
                        ref.refreshCalendar();
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
        var grupos = this.getFiltroGrupos().down('combobox[name=grupo]');

        if (grupos.getValue() != null)
        {
            var calendarios = this.getSelectorCalendarios().getCalendarsSelected();
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
                                    enableEditDetails: false,
                                    viewConfig :
                                    {
                                        viewStartHour : horaInicio,
                                        viewEndHour : horaFin
                                    },
                                    initComponent : function()
                                    {
                                        this.superclass.initComponent.call(this);
                                        ref.desactivaMenuContextualYEdicionDetalle();
                                    },
                                    listeners :
                                    {
                                        afterrender : function()
                                        {
                                            params =
                                            {
                                                estudioId : titulaciones.getValue(),
                                                cursoId : cursos.getValue(),
                                                semestreId : semestres.getValue(),
                                                calendariosIds : calendarios,
                                                grupoId : grupos.getValue(),
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

                        params =
                        {
                            estudioId : titulaciones.getValue(),
                            cursoId : cursos.getValue(),
                            semestreId : semestres.getValue(),
                            calendariosIds : calendarios,
                            grupoId : grupos.getValue()
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
                            initComponent : function()
                            {
                                this.superclass.initComponent.call(this);
                                ref.activaMenuContextualYEdicionDetalle();
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
    },
    
    desactivaMenuContextualYEdicionDetalle : function() {
    	Extensible.calendar.menu.Event.override(
    			{
    			    buildMenu : function()
    			    {
    			        var me = this;

    			        if (me.rendered)
    			        {
    			            return;
    			        }
    			        Ext.apply(me,
    			        {
    			            items : [ ]
    			        });
    			    },

    			    showForEvent : function(rec, el, xy)
    			    {
    			       
    			    }
    			});
    	
    	Extensible.calendar.form.EventWindow.override(
    			{
    				getFooterBarConfig : function()
    			    {
    					var cfg = ['->', {
    		                text: this.saveButtonText,
    		                itemId: this.id + '-save-btn',
    		                disabled: false,
    		                handler: this.onSave,
    		                scope: this
    		            },{
    		                text: this.deleteButtonText,
    		                itemId: this.id + '-delete-btn',
    		                disabled: true,
    		                handler: this.onDelete,
    		                scope: this,
    		                hideMode: 'offsets' // IE requires this
    		            },{
    		                text: this.cancelButtonText,
    		                itemId: this.id + '-cancel-btn',
    		                disabled: false,
    		                handler: this.onCancel,
    		                scope: this
    		            }];
    		        
    		        if(this.enableEditDetails !== false){
    		            cfg.unshift({
    		                xtype: 'tbtext',
    		                itemId: this.id + '-details-btn',
    		                text: '<a href="#" class="' + this.editDetailsLinkClass + '">' + this.detailsLinkText + '</a>'
    		            });
    		        }
    		        return cfg;
    			    	
    			    }
    			} 
         );
    },
    activaMenuContextualYEdicionDetalle : function() {
    	Extensible.calendar.menu.Event.override(
    			{
    			    buildMenu : function()
    			    {
    			        var me = this;

    			        if (me.rendered)
    			        {
    			            return;
    			        }
    			        Ext.apply(me,
    			        {
    			            items : [
    			            {
    			                text : me.editDetailsText,
    			                iconCls : 'extensible-cal-icon-evt-edit',
    			                scope : me,
    			                handler : function()
    			                {
    			                    me.fireEvent('editdetails', me, me.rec, me.ctxEl);
    			                }
    			            },
    			            {
    			                text : 'Assignar aula',
    			                iconCls : 'extensible-cal-icon-evt-edit',
    			                scope : me,
    			                handler : function()
    			                {
    			                    Ext.ComponentQuery.query("panelCalendario")[0].fireEvent('eventasignaaula', me, me.rec);
    			                }
    			            },
    			            {
    			                text : 'Assignar a circuit',
    			                iconCls : 'extensible-cal-icon-evt-edit',
    			                menu : me.copyMenu

    			            }, '-',
    			            {
    			                text : 'Dividir',
    			                iconCls : 'extensible-cal-icon-evt-copy',
    			                scope : me,
    			                handler : function()
    			                {
    			                    Ext.ComponentQuery.query("panelCalendario")[0].fireEvent('eventdivide', me, me.rec);
    			                }
    			            },
    			            {
    			                text : me.deleteText,
    			                iconCls : 'extensible-cal-icon-evt-del',
    			                scope : me,
    			                handler : function()
    			                {
    			                    me.fireEvent('eventdelete', me, me.rec, me.ctxEl);
    			                }
    			            } ]
    			        });
    			    },

    			    showForEvent : function(rec, el, xy)
    			    {
    			        var me = this;
    			        me.rec = rec;
    			        me.ctxEl = el;
    			        me.showAt(xy);
    			    }
    			});
    	
    	Extensible.calendar.form.EventWindow.override(
    			{
    				getFooterBarConfig : function()
    			    {
    			    	var cfg = ['->', {
    		                text: this.saveButtonText,
    		                itemId: this.id + '-save-btn',
    		                disabled: false,
    		                handler: this.onSave,
    		                scope: this
    		            },{
    		                text: this.deleteButtonText,
    		                itemId: this.id + '-delete-btn',
    		                disabled: false,
    		                handler: this.onDelete,
    		                scope: this,
    		                hideMode: 'offsets' // IE requires this
    		            },{
    		                text: this.cancelButtonText,
    		                itemId: this.id + '-cancel-btn',
    		                disabled: false,
    		                handler: this.onCancel,
    		                scope: this
    		            }];
    		        
    		        if(this.enableEditDetails !== false){
    		            cfg.unshift({
    		                xtype: 'tbtext',
    		                itemId: this.id + '-details-btn',
    		                text: '<a href="#" class="' + this.editDetailsLinkClass + '">' + this.detailsLinkText + '</a>'
    		            });
    		        }
    		        return cfg;
    			    	
    			    }
    			} 
         );
    },
    imprimirCalendario : function()
    {
        var titulacion = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();

        if (this.getBotonCalendarioGenerica().pressed)
        {
            window.open("http://www.uji.es/cocoon/xxxx/" + titulacion + "/" + curso + "/" + semestre + "/" + grupo + "/horario-semana-generica.pdf");
        }
        else
        {
            window.open("http://www.uji.es/cocoon/xxxx/" + titulacion + "/" + curso + "/" + semestre + "/" + grupo + "/horario-semana-detalle.pdf");
        }
    },
    
    mostrarValidaciones : function()
    {
    	 var titulacion = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
         var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
         var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
         var grupo = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();
         
         window.open("http://www.uji.es/cocoon/xxxx/" + titulacion + "/" + curso + "/" + semestre + "/" + grupo + "/validaciones-horarios.pdf");
    }

});