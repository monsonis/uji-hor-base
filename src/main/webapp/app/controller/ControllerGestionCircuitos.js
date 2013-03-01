Ext.define('HOR.controller.ControllerGestionCircuitos',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCircuitos' ],
    views : [ 'circuitos.VentanaEdicionCircuitos' ],
    ventanaEdicionCircuitos : {},

    refs : [
    {
        selector : 'panelCircuitos panelGestionCircuitos',
        ref : 'panelGestionCircuitos'
    },
    {
        selector : 'panelCircuitos filtroCircuitos',
        ref : 'filtroCircuitos'
    },
    {
        selector : 'ventanaEdicionCircuitos',
        ref : 'ventanaEdicionCircuitos'
    },
    {
        selector : 'ventanaEdicionCircuitos form[name=formEdicionCircuitos]',
        ref : 'formEdicionCircuitos'
    },
    {
        selector : 'panelCircuitos selectorCircuitos',
        ref : 'selectorCircuitos',
    },
    {
        selector : 'panelCircuitos filtroCircuitos combobox[name=grupo]',
        ref : 'comboGrupos'
    } ],

    init : function()
    {
        this.control(
        {
            'panelGestionCircuitos button[name=nuevo-circuito]' :
            {
                click : this.showVentanaNuevoCircuito
            },
            'panelGestionCircuitos button[name=editar-circuito]' :
            {
                click : this.showVentanaEdicionCircuito
            },
            'panelGestionCircuitos button[name=eliminar-circuito]' :
            {
                click : this.eliminarCircuito
            },
            'ventanaEdicionCircuitos button[action=cancelar]' :
            {
                click : this.closeVentanaEdicionCircuitos
            },
            'ventanaEdicionCircuitos button[action=guardar]' :
            {
                click : this.guardarCircuito
            },
            'filtroCircuitos combobox[name=grupo]' :
            {
                select : function()
                {
                    this.loadCircuitos();
                    this.getPanelGestionCircuitos().down('button[name=nuevo-circuito]').show();
                    this.getPanelGestionCircuitos().down('button[name=editar-circuito]').hide();
                    this.getPanelGestionCircuitos().down('button[name=eliminar-circuito]').hide();
                }
            },
            'panelCircuitos selectorCircuitos button' :
            {
                click : this.clickOnCircuito
            }
        });
    },

    getVentanaEdicionCurcuitosView : function()
    {
        return this.getView('circuitos.VentanaEdicionCircuitos').create();
    },

    showVentanaNuevoCircuito : function()
    {
        this.ventanaEdicionCircuitos = this.getVentanaEdicionCurcuitosView();
        this.ventanaEdicionCircuitos.show();
    },

    showVentanaEdicionCircuito : function()
    {
        var store = this.getStoreCircuitosStore();
        var circuito = store.getAt(store.find('id', this.getCircuitoSeleccionadoId()));
        this.ventanaEdicionCircuitos = this.getVentanaEdicionCurcuitosView();

        var form = this.getFormEdicionCircuitos().getForm();

        form.findField('id-circuito').setValue(circuito.get('id'));
        form.findField('nombre').setValue(circuito.get('nombre'));
        form.findField('plazas').setValue(circuito.get('plazas'));

        this.ventanaEdicionCircuitos.show();
    },

    closeVentanaEdicionCircuitos : function()
    {
        this.ventanaEdicionCircuitos.destroy();
    },

    guardarCircuito : function()
    {
        var ref = this;
        var form = this.getFormEdicionCircuitos().getForm();

        var circuitoId = form.findField('id-circuito').getValue();
        var nombre = form.findField('nombre').getValue();
        var plazas = form.findField('plazas').getValue();

        var estudio = this.getFiltroCircuitos().down('combobox[name=estudio]').getValue();
        var semestre = this.getFiltroCircuitos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroCircuitos().down('combobox[name=grupo]').getValue();

        if (form.isValid())
        {
            var circuito = Ext.ModelManager.create(
            {
                id : circuitoId,
                nombre : nombre,
                plazas : plazas,
                estudioId : estudio,
                semestre : semestre,
                grupo : grupo
            }, "HOR.model.Circuito");

            var store = this.getStoreCircuitosStore();

            if (circuitoId == '')
            {
                store.add(circuito);
            }
            else
            {
                var record = store.getAt(store.find('id', circuitoId));
                record.set('nombre', nombre);
                record.set('plazas', plazas);

                store.getProxy().extraParams['estudioId'] = estudio;
            }

            store.sync(
            {
                success : function(response)
                {
                    ref.closeVentanaEdicionCircuitos();
                    var circuitoId = response.operations[0].records[0].data.id;
                    ref.actualizaSelectorCircuitos(circuitoId);
                }
            });
        }
    },

    eliminarCircuito : function()
    {
        var ref = this;
        var store = this.getStoreCircuitosStore();
        var circuito = store.getAt(store.find('id', this.getCircuitoSeleccionadoId()));
        var estudio = this.getFiltroCircuitos().down('combobox[name=estudio]').getValue();

        Ext.Msg.confirm('Eliminar circuit', 'Totes les dades d\'aquest circuit s\'esborraràn. Estàs segur de voler continuar?', function(btn, text)
        {
            if (btn == 'yes')
            {
                store.getProxy().extraParams['estudioId'] = estudio;
                store.remove([ circuito ]);

                store.sync(
                {
                    success : function()
                    {
                        ref.actualizaSelectorCircuitos();
                        ref.getPanelGestionCircuitos().down('button[name=editar-circuito]').hide();
                        ref.getPanelGestionCircuitos().down('button[name=eliminar-circuito]').hide();
                    }
                });
            }
        });
    },

    getCircuitoSeleccionadoId : function()
    {
        var circuitos = Ext.ComponentQuery.query('panelCircuitos selectorCircuitos button');

        for ( var i = 0; i < circuitos.length; i++)
        {
            if (circuitos[i].pressed == true)
            {
                return circuitos[i].circuitoId;
            }
        }
    },

    loadCircuitos : function()
    {
        var estudio = this.getFiltroCircuitos().down('combobox[name=estudio]').getValue();
        var semestre = this.getFiltroCircuitos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroCircuitos().down('combobox[name=grupo]').getValue();

        var store = this.getStoreCircuitosStore();

        store.load(
        {
            callback : this.actualizaSelectorCircuitos,
            params :
            {
                estudioId : estudio,
                semestreId : semestre,
                grupoId : grupo
            },
            scope : this
        });
    },

    actualizaSelectorCircuitos : function(circuitoAMostrar)
    {
        var view = this.getSelectorCircuitos();

        view.removeAll();

        var store = this.getStoreCircuitosStore();
        store.sort('nombre', 'ASC');

        var botones = new Array();

        for ( var i = 0; i < store.count(); i++)
        {
            var circuito = store.getAt(i);

            var pressed = false;
            if (circuitoAMostrar && circuitoAMostrar == circuito.get('id'))
            {
                pressed = true;
            }

            var button =
            {
                xtype : 'button',
                text : circuito.get('nombre'),
                padding : '2 5 2 5',
                margin : '10 10 0 10',
                circuitoId : circuito.get('id'),
                enableToggle : true,
                toggleGroup : 'circuitos',
                pressed : pressed
            };

            botones.push(button);
        }

        view.add(botones);
    },

    clickOnCircuito : function(button)
    {
        if (button.pressed)
        {
            this.getPanelGestionCircuitos().down('button[name=editar-circuito]').show();
            this.getPanelGestionCircuitos().down('button[name=eliminar-circuito]').show();
        }
        else
        {
            this.getPanelGestionCircuitos().down('button[name=editar-circuito]').hide();
            this.getPanelGestionCircuitos().down('button[name=eliminar-circuito]').hide();
        }
    }
});