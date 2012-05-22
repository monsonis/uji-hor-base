Ext.ns('Ext.ux.uji.grid');

Ext.ux.uji.grid.GridPanel = Ext.extend(Ext.grid.GridPanel,
{
    version : "0.0.1",
    frame : true,
    border : false,
    searchField : false,
    allowEdit : true,
    subResource : "", // trozo de url para un grid detalle "http://urlbase/id/ + this.subResource"
    viewConfig :
    {
        forceFit : true
    },
    tbar : [],
    loadMask :
    {
        msg : "Carregant..."
    },
    listeners :
    {
        rowclick : function(grid, index, evt)
        {
            this.rowClick(grid, index, evt);
        },

        "storeload" : function(grid, rowIndex, evt, params)
        {
            this.storeLoad(grid, rowIndex, evt, params);
        }
    },
    rowClick : function(grid, index, evt)
    {
        this.getTopToolbar().get('delete').enable();

        var params = {};
        store = this.store;
        elementosSeleccionados = this.getSelectionModel().getSelections().length;

        if (elementosSeleccionados == 1)
        {
            var id = this.getSelectionModel().getSelected().id;
            if (!this.selModel.getSelected().dirty)
            {
                params.id = id;
                params.store = this.getStore();
            }
        }

        this.fireEvent("storeload", grid, index, evt, params);
    },
    storeLoad : function(grid, rowIndex, evt, params)
    {
        if (this.getId() != grid.getId())
        {
            store = this.store;
            if (params.id)
            {
                store.proxy.setUrl(params.store.proxy.url + "/" + params.id + '/' + this.subResource, true);
                store.load();
            }
        }
    },
    addButton : function(ref)
    {
        var object =
        {
            text : "Afegir",
            iconCls : 'application-add',
            handler : function(btn, ev)
            {
                var u = new ref.store.recordType({});

                ref.editor.stopEditing();
                ref.store.insert(0, u);
                ref.editor.startEditing(0);
            }
        };

        return object;
    },
    deleteButton : function(ref)
    {
        var object =
        {
            text : 'Esborrar',
            iconCls : 'application-delete',
            disabled : true,
            itemId : 'delete',
            handler : function()
            {
                Ext.Msg.confirm('Esborrat', 'Esteu segur/a de voler esborrar el registre ?',
                        function(btn, text)
                        {
                            if (btn == 'yes')
                            {
                                var rec = ref.getSelectionModel().getSelected();
                                if (!rec)
                                {
                                    return false;
                                }
                                ref.store.remove(rec);
                            }
                        });
            }
        };

        return object;
    },
    initComponent : function()
    {

        var config = {};
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.ux.uji.grid.GridPanel.superclass.initComponent.call(this);

        this.mainInit();

    },
    buildSelectionModel : function()
    {
        this.selModel = new Ext.grid.RowSelectionModel(
        {
            singleSelect : true
        });
    },
    mainInit : function()
    {
        var ref = this;

        this.buildSelectionModel();
        this.editor = new Ext.ux.grid.RowEditor(
        {
            saveText : 'Desar',
            cancelText : 'Cancel·lar',
            errorSummary : false,
            listeners :
            {
                canceledit : function(rowEditor)
                {
                    // para que funcione, alguno de los campos del Store debe ser obligatorio
                    var record = rowEditor.record;

                    if ((record.phantom))
                    {
                        ref.store.remove(rowEditor.record);
                    }
                },

                beforeedit : function(editor, rowIndex)
                {
                    var administradorRecord = editor.grid.getStore().getAt(rowIndex);
                    if (!ref.allowEdit && !administradorRecord.phantom)
                    {
                        return false;
                    }
                }
            }
        });

        this.plugins = [ this.editor ];

        tb = this.getTopToolbar();

        if (this.searchField)
        {
            tb.add([ new Ext.ux.form.SearchField(
            {
                emptyText : 'Recerca...',
                store : this.store,
                width : 180
            }), ' ', '-' ]);
        }

        tb.add([ ref.addButton(ref), '-', ref.deleteButton(ref), '-' ]);
    }
});