Ext.define('Ext.ux.uji.form.LookupWindow',
{
    extend : 'Ext.Window',

    version : "0.0.2",
    appPrefix : '',
    bean : 'base',
    lastQuery : '',
    queryField : '',
    formularioBusqueda : '',
    gridBusqueda : '',
    storeResultados : '',
    botonBuscar : '',
    botonCancelar : '',
    extraFields : [],
    title : 'Cercar registres',
    layout : 'border',
    modal : true,
    hidden : true,
    width : 400,
    height : 400,
    closeAction : 'hide',
    clearAfterSearch : true,

    initComponent : function()
    {
        this.callParent(arguments);

        this.storeResultados = Ext.create('Ext.data.ArrayStore',
        {
            fields : this.fields
        });

        this.initUI();
        this.add(this.formularioBusqueda);
        this.add(this.gridBusqueda);

        this.addEvents('LookoupWindowClickSeleccion');
    },

    initUI : function()
    {
        this.buildQueryField();
        this.buildBotonBuscar();
        this.buildFormularioBusqueda();
        this.buildBotonCancelar();
        this.buildBotonSeleccionar();
        this.buildGridBusqueda();
    },

    executeSearch : function(query)
    {
        this.gridBusqueda.store.load(
        {
            params :
            {
                query : query,
                bean : this.bean
            }
        });
    },

    buildQueryField : function()
    {
        var ref = this;
        this.queryField = Ext.create('Ext.form.TextField',
        {
            name : 'query',
            value : '',
            listeners :
            {
                specialkey : function(field, e)
                {
                    if (e.getKey() == e.ENTER)
                    {
                        ref.botonBuscar.handler.call(ref.botonBuscar.scope);
                    }
                }
            }
        });
    },

    buildBotonBuscar : function()
    {
        var ref = this;
        this.botonBuscar = Ext.create('Ext.Button',
        {
            text : 'Cerca',
            handler : function(boton, event)
            {
                if (ref.queryField.getValue().length < 3)
                {
                    Ext.Msg.alert("Error", "Per fer una cerca cal introduir al menys 3 caracters.");
                }
                else
                {
                    ref.lastQuery = ref.queryField.getValue();
                    ref.executeSearch(ref.queryField.getValue());
                }
            }
        });
    },

    buildFormularioBusqueda : function()
    {
        this.formularioBusqueda = Ext.create('Ext.Panel',
        {
            layout : 'hbox',
            region : 'north',
            height : 40,
            frame : true,
            items : [
            {
                xtype : 'label',
                text : 'Expressió: ',
                width : 100
            }, this.queryField, this.botonBuscar ]
        });
    },

    buildBotonCancelar : function()
    {
        var ref = this;

        this.botonCancelar = Ext.create('Ext.Button',
        {
            text : 'Cancel.lar',
            handler : function(e)
            {
                ref.storeResultados.removeAll();
                ref.hide();
            }
        });
    },

    buildBotonSeleccionar : function()
    {
        var ref = this;

        this.botonSeleccionar = Ext.create('Ext.Button',
        {
            text : 'Seleccionar',
            handler : function(e)
            {
                var record = ref.gridBusqueda.getSelectionModel().getSelections()[0];

                if (record)
                {
                    ref.fireEvent('LookoupWindowClickSeleccion', record);

                    if (ref.clearAfterSearch)
                    {
                        var query = ref.queryField;
                        query.setValue('');
                        ref.gridBusqueda.store.removeAll(true);
                        ref.gridBusqueda.getView().refresh();
                        ref.storeResultados.removeAll();
                    }
                    ref.hide();
                }
            }
        });
    },

    buildGridBusqueda : function()
    {
        var ref = this;

        var resultListFields = [ 'id', 'nombre' ];
        var resultColumnList = [
        {
            header : 'Codi',
            width : 50,
            dataIndex : 'id'
        },
        {
            header : 'Nom',
            width : 300,
            dataIndex : 'nombre'
        } ];

        for ( var extraField in this.extraFields)
        {
            if (this.extraFields.hasOwnProperty(extraField))
            {
                resultListFields.push(
                {
                    name : this.extraFields[extraField],
                    mapping : "extraParam[key='" + this.extraFields[extraField] + "']/value"
                });
                resultColumnList.push(
                {
                    header : this.extraFields[extraField],
                    width : 300,
                    dataIndex : this.extraFields[extraField]
                });
            }
        }

        this.gridBusqueda = Ext.create('Ext.grid.Panel',
        {
            region : 'center',
            flex : 1,
            frame : true,
            loadMask : true,
            store : Ext.create('Ext.data.Store',
            {
                model : 'Ext.ux.uji.form.model.Lookup',

                autoSync : true,

                proxy :
                {
                    type : 'rest',
                    url : '/' + ref.appPrefix + '/rest/lookup',

                    reader :
                    {
                        type : 'json',
                        root : 'data'
                    },

                    writer :
                    {
                        type : 'json'
                    }
                },
                listeners :
                {
                    load : function()
                    {
                        console.debug(ref.gridBusqueda.store.data.length);
                        
                        if (ref.gridBusqueda.store.data.length === 0)
                        {
                            Ext.Msg.alert("Aviso", "La búsqueda realitzada no ha produït cap resultat");
                        }
                    }
                }
            }),
            columns : resultColumnList,
            viewConfig :
            {
                forceFit : true
            },
            listeners :
            {
                dblclick : function(event)
                {
                    ref.botonSeleccionar.handler.call(ref.botonSeleccionar.scope);
                }
            },
            buttons : [ this.botonSeleccionar, this.botonCancelar ]

        });
    },

    onEsc : function()
    {
        this.botonCancelar.handler.call(this.botonCancelar.scope);
    },

    listeners :
    {
        'show' : function(window)
        {
            window.queryField.focus(true, 200);
        }
    }
});