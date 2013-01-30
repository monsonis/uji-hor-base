Ext.define('HOR.controller.ControllerPermisos',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StorePermisos', 'StoreEstudios', 'StoreCargos' ],
    model : [ 'Permiso' ],
    views : [ 'permisos.VentanaNewPermiso' ],
    ventanaPermiso : {},

    getVentanaNewPermisoView : function()
    {
        return this.getView('permisos.VentanaNewPermiso').create();
    },

    refs : [
    {
        selector : 'ventanaNewPermiso',
        ref : 'ventanaNewPermiso'
    },
    {
        selector : 'ventanaNewPermiso combo[name=comboPersona]',
        ref : 'comboPersona'
    },
    {
        selector : 'ventanaNewPermiso form[name=formNewPermiso]',
        ref : 'formNewPermiso'
    },
    {
        selector : 'panelPermisos grid',
        ref : 'gridPermisos'
    },
    {
        selector : 'ventanaNewPermiso combo[name=comboTitulacion]',
        ref : 'comboTitulacion'
    },
    {
        selector : 'ventanaNewPermiso combo[name=comboCargo]',
        ref : 'comboCargo'
    } ],

    init : function()
    {
        this.control(
        {
            'panelPermisos button[action=add-permiso]' :
            {
                click : function(button)
                {
                    this.showVentanaAddPermiso();
                }
            },

            'panelPermisos button[action=borrar-permiso]' :
            {
                click : function(button)
                {
                    this.borraPermisos();
                }
            },

            'ventanaNewPermiso button[action=cancelar]' :
            {
                click : function(button)
                {
                    this.closeVentanaAddPermiso();
                }
            },

            'ventanaNewPermiso button[action=guardar-permiso]' :
            {
                click : function(button)
                {
                    this.guardarPermiso();
                }
            },

        });
    },

    showVentanaAddPermiso : function()
    {
        this.ventanaPermiso = this.getVentanaNewPermisoView();
        this.ventanaPermiso.show();
    },

    closeVentanaAddPermiso : function()
    {
        this.ventanaPermiso.destroy();
    },

    guardarPermiso : function()
    {
        var ref = this;
        var formNewPermiso = this.getFormNewPermiso().getForm();
        var comboPersona = this.getComboPersona();
        var comboTitulacion = this.getComboTitulacion();
        var comboCargo = this.getComboCargo();

        var personaId = comboPersona.getValue();
        var estudioId = comboTitulacion.getValue();
        var tipoCargoId = comboCargo.getValue();

        if (formNewPermiso.isValid())
        {
            var permiso = Ext.ModelManager.create(
            {
                personaId : personaId,
                persona : comboPersona.getRawValue(),
                estudioId : estudioId,
                estudio : comboTitulacion.getRawValue(),
                tipoCargo : comboCargo.getRawValue(),
                tipoCargoId : tipoCargoId
            }, "HOR.model.Permiso");

            var gridPermisos = this.getGridPermisos();
            gridPermisos.getStore().add(permiso);
            gridPermisos.getStore().sync(
            {
                success : function()
                {
                    ref.closeVentanaAddPermiso();
                }
            });
        }
    },
    borraPermisos: function() {
        var gridPermisos = this.getGridPermisos();
        var registros = gridPermisos.getSelectionModel().getSelection();
        gridPermisos.getStore().remove(registros);
        gridPermisos.getStore().sync();
    }
});