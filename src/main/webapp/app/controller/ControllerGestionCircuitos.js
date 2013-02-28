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
        var ref = this;
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
                    ref.loadCircuitos();
                }
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
        var circuito = this.getStoreCircuitos().find('id', this.getCircuitoSeleccionadoId());
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
                estudio : estudio,
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
                var record = store.find('id', circuitoId);
                record.set('nombre', nombre);
                record.set('plazas', plazas);
            }

            store.sync(
            {
                success : function()
                {
                    ref.closeVentanaEdicionCircuitos();

                    ref.actualizaSelectorCircuitos();
                }
            });
        }
    },

    eliminarCircuito : function()
    {
        var ref = this;
        var circuito = this.getStoreCircuitos().find('id', this.getCircuitoSeleccionadoId());

        Ext.Msg.confirm('Eliminar circuit', 'Totes les dades d\'aquest circuit s\'esborraràn. Estàs segur de voler continuar?', function(btn, text)
        {
            if (btn == 'yes')
            {
                ref.getStoreCircuitos().remove([ circuito ]);

                ref.getStoreCircuitos().sync(
                {
                    success : function()
                    {
                        ref.actualizaSelectorCircuitos();
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
            if (circuitos[i].active == true)
            {
                return circuito[i].circuitoId;
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

    actualizaSelectorCircuitos : function()
    {
        var view = this.getSelectorCircuitos();

        view.removeAll();

        var store = this.getStoreCircuitosStore();
        var botones = new Array();

        for ( var i = 0; i < store.count(); i++)
        {
            var circuito = store.getAt(i);

            var button =
            {
                xtype : 'button',
                text : circuito.get('nombre'),
                padding : '2 5 2 5',
                margin : '10 10 0 10',
                circuitoId : circuito.get('id')
            };

            botones.push(button);
        }

        view.add(botones);
    }
});