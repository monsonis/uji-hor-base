Ext.define('HOR.controller.ControllerPermisos',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StorePermisos' ],
    model : [ 'Permiso' ],

    refs : [
    {
        selector : 'ventanaNewPermiso',
        ref : 'ventanaNewPermiso'
    } ],
    
    init : function()
    {
        this.control(
        {
            'selectorIntervaloHorario button[action=cancel]' :
            {
                click : function(button)
                {
                    button.up("selectorIntervaloHorario").destroy();
                }
            },
        });
    },


});