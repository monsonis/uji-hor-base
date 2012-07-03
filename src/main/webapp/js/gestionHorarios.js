Ext.Loader.setConfig(
{
    enabled : true,
    paths :
    {
        "Extensible" : "/hor/js/extensible-1.5.1/src",
    }
});

Ext.require([ 'Ext.Viewport', 'Ext.layout.container.Border', 'Ext.data.proxy.Rest', 'Extensible.calendar.data.MemoryCalendarStore', 'Extensible.calendar.data.EventStore',
        'Extensible.calendar.CalendarPanel' ]);

Ext.onReady(function()
{
    Ext.QuickTips.init();

    Ext.define('UJI.HOR.GestionHorarios',
    {
        extend : 'Ext.panel.Panel',
        title : 'Gestió d\'horaris per semestre i grup',
        region : 'center',
        storeCalendarios : null,
        formFiltrosCalendario : null,
        panelGrupos : null,
        panelSelectorCalendarios : null,
        panelCalendario : null,
        panelLateralIzquierdo : null,
        panelPrincipal : null,
        selectorCalendarios : null,
        comboTitulacion : null,
        comboCurso : null,
        comboSemestre : null,
        comboGrupo : null,
        layout :
        {
            type : 'vbox',
            align : 'stretch',
            padding : 5
        },
        constructor : function()
        {
            var config = {};

            Ext.apply(this, Ext.apply(this.initialConfig, config));
            this.callParent();

            this.initUI();
            this.add(this.formFiltrosCalendario);
            this.add(this.panelPrincipal);
        },

        initUI : function()
        {
            this.buildStoreCalendarios();
            this.buildComboTitulacion();
            this.buildComboCurso();
            this.buildComboSemestre();
            this.buildComboGrupo();
            this.buildFormFiltrosCalendario();
            this.buildSelectorCalendarios();
            this.buildPanelGrupos();
            this.buildPanelSelectorCalendarios();
            this.buildPanelCalendario();
            this.buildPanelLateralIzquierdo();
            this.buildPanelPrincipal();

        },

        buildStoreCalendarios : function()
        {
            this.storeCalendarios = Ext.create('Extensible.calendar.data.MemoryCalendarStore',
            {
                autoLoad : true,
                proxy :
                {
                    type : 'ajax',
                    url : '/hor/rest/calendario',
                    noCache : false,

                    reader :
                    {
                        type : 'json',
                        root : 'data'
                    }
                }
            });
        },

        buildPanelPrincipal : function()
        {
            this.panelPrincipal = Ext.create("Ext.panel.Panel",
            {
                border : 0,
                flex : 1,
                layout :
                {
                    type : 'hbox',
                    align : 'stretch'
                },

                items : [ this.panelLateralIzquierdo, this.panelCalendario ]
            });
        },

        buildPanelLateralIzquierdo : function()
        {
            this.panelLateralIzquierdo = Ext.create("Ext.panel.Panel",
            {
                border : false,
                width : 300,
                layout :
                {
                    type : 'vbox',
                    align : 'stretch'
                },
                items : [ this.panelGrupos, this.panelSelectorCalendarios ]

            });
        },

        buildPanelCalendario : function()
        {

            var eventStore = Ext.create('Extensible.calendar.data.EventStore',
            {
                autoLoad : true,
                proxy :
                {
                    type : 'rest',
                    url : '/hor/rest/calendario/eventos',
                    noCache : false,

                    reader :
                    {
                        type : 'json',
                        root : 'data'
                    },

                    writer :
                    {
                        type : 'json',
                        nameProperty : 'mapping'
                    },

                    listeners :
                    {
                        exception : function(proxy, response, operation, options)
                        {
                            var msg = response.message ? response.message : Ext.decode(response.responseText).message;
                            Ext.Msg.alert('Server Error', msg);
                        }
                    }
                },
                listeners :
                {
                    'write' : function(store, operation)
                    {
                        var title = Ext.value(operation.records[0].data[Extensible.calendar.data.EventMappings.Title.name], '(No title)');
                        switch (operation.action)
                        {
                        case 'create':
                            Extensible.example.msg('Add', 'Added "' + title + '"');
                            break;
                        case 'update':
                            Extensible.example.msg('Update', 'Updated "' + title + '"');
                            break;
                        case 'destroy':
                            Extensible.example.msg('Delete', 'Deleted "' + title + '"');
                            break;
                        }
                    }
                }
            });

            this.panelCalendario = Ext.create('Extensible.calendar.CalendarPanel',
            {
                id : 'calendar-remote',
                region : 'center',
                eventStore : eventStore,
                calendarStore : this.storeCalendarios,
                title : 'Calendari',
                editModal : true,
                flex : 1,
                padding : 5,
                weekViewCfg :
                {
                    dayCount : 5,
                    startDay : 1,
                    startDayIsStatic : true,
                    viewStartHour : 8,
                    viewEndHour : 22,
                // hourHeight: 84
                },
                dayViewCfg :
                {
                    viewStartHour : 8,
                    viewEndHour : 22
                },
                showMultiDayView : true,
                showMultiWeekView : false,
                multiDayViewCfg :
                {
                    dayCount : 5,
                    viewStartHour : 8,
                    viewEndHour : 22
                },
                getMultiDayText : function()
                {
                    return 'Setmana genèrica';
                }
            });

        },

        buildPanelSelectorCalendarios : function()
        {
            this.panelSelectorCalendarios = Ext.create("Ext.panel.Panel",
            {
                title : 'Calendaris',
                flex : 1,
                padding : 5,
                layout :
                {
                    type : 'vbox',
                    align : 'stretch'
                },
                items : [ this.selectorCalendarios ]
            });
        },

        buildSelectorCalendarios : function()
        {
            this.selectorCalendarios = Ext.create("Ext.form.CheckboxGroup",
            {
                columns : 1,
                padding : 10,
                vertical : true,
                items : []
            });

            var ref = this;
            this.storeCalendarios.on("load", function(store, records, success, opts)
            {
                store.data.each(function(item)
                {
                    ref.selectorCalendarios.add(
                    {
                        boxLabel : item.get("Title"),
                        inputValue : item.get("CalendarId"),
                        listeners :
                        {
                            'change' : function(cb, checked)
                            {
                                console.log(cb);
                            }
                        }
                    });
                });

            });
        },

        buildPanelGrupos : function()
        {
            this.panelGrupos = Ext.create("Ext.panel.Panel",
            {
                title : 'Grups sense asignar',
                width : 300,
                padding : 5,
                flex : 1,
                layout :
                {
                    type : 'vbox',
                    align : 'stretch'
                },
                items : [
                {
                    xtype : 'button',
                    text : 'AE1012 - TE1',
                    padding : 5,
                    margin : '25 30 0 30'
                },
                {
                    xtype : 'button',
                    text : 'AE1012 - TE2',
                    padding : 5,
                    margin : '5 30 0 30'
                },
                {
                    xtype : 'button',
                    text : 'AE1012 - PR1',
                    padding : 5,
                    margin : '5 30 0 30'
                },
                {
                    xtype : 'button',
                    text : 'AE1012 - PR2',
                    padding : 5,
                    margin : '5 30 0 30'
                },
                {
                    xtype : 'button',
                    text : 'AE1012 - SE2',
                    padding : 5,
                    margin : '5 30 0 30'
                }, ]
            });
        },

        buildFormFiltrosCalendario : function()
        {
            this.formFiltrosCalendario = Ext.create("Ext.panel.Panel",
            {
                border : false,
                padding : 5,
                layout :
                {
                    align : 'stretch'
                },
                items : [ this.comboTitulacion, this.comboCurso, this.comboSemestre, this.comboGrupo ]
            });
        },

        buildComboTitulacion : function()
        {
            var testStore = Ext.create("Ext.data.Store",
            {
                fields : [ "id", "name" ],
                data : [
                {
                    "id" : "A01",
                    "name" : "Ingenieria Informàtica"
                },
                {
                    "id" : "I02",
                    "name" : "Dret Civil"
                } ]
            });

            this.comboTitulacion = Ext.create("Ext.form.ComboBox",
            {
                fieldLabel : 'Titulació',
                store : testStore,
                queryModel : 'local',
                displayField : 'name',
                valueField : 'id'
            });
        },

        buildComboCurso : function()
        {
            var testStore = Ext.create("Ext.data.Store",
            {
                fields : [ "id", "name" ],
                data : [
                {
                    "id" : "A01",
                    "name" : "Ingenieria Informàtica"
                },
                {
                    "id" : "I02",
                    "name" : "Dret Civil"
                } ]
            });

            this.comboCurso = Ext.create("Ext.form.ComboBox",
            {
                fieldLabel : 'Curso',
                store : testStore,
                queryModel : 'local',
                displayField : 'name',
                valueField : 'id'
            });
        },

        buildComboSemestre : function()
        {
            var testStore = Ext.create("Ext.data.Store",
            {
                fields : [ "id", "name" ],
                data : [
                {
                    "id" : "A01",
                    "name" : "Ingenieria Informàtica"
                },
                {
                    "id" : "I02",
                    "name" : "Dret Civil"
                } ]
            });

            this.comboSemestre = Ext.create("Ext.form.ComboBox",
            {
                fieldLabel : 'Semestre',
                store : testStore,
                queryModel : 'local',
                displayField : 'name',
                valueField : 'id'
            });
        },

        buildComboGrupo : function()
        {
            var testStore = Ext.create("Ext.data.Store",
            {
                fields : [ "id", "name" ],
                data : [
                {
                    "id" : "A01",
                    "name" : "Ingenieria Informàtica"
                },
                {
                    "id" : "I02",
                    "name" : "Dret Civil"
                } ]
            });

            this.comboGrupo = Ext.create("Ext.form.ComboBox",
            {
                fieldLabel : 'Grupo',
                store : testStore,
                queryModel : 'local',
                displayField : 'name',
                valueField : 'id'
            });
        }
    });

    Ext.Ajax.request(
    {
        url : 'extensible-1.5.1/src/locale/extensible-lang-ca.js',
        disableCaching : false,
        success : function(rest, opts)
        {
            eval(rest.responseText);
            var gestionHorarios = new UJI.HOR.GestionHorarios();

            Ext.create('Ext.Viewport',
            {
                layout : 'border',
                items : [ gestionHorarios ]
            });
        }
    });

});