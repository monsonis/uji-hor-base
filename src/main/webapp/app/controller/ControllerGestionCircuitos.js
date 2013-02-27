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
    } ],

    init : function()
    {
        this.control(
        {
            'panelGestionCircuitos button[name=nuevo-circuito]' :
            {
                click : this.showVentanaNuevoCircuito
            },
            'ventanaEdicionCircuitos button[action=cancelar]' :
            {
                click : this.closeVentanaEdicionCircuitos
            },
            'ventanaEdicionCircuitos button[action=guardar-circuito]' :
            {
                click : this.guardarCircuito
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

    closeVentanaEdicionCircuitos : function()
    {
        this.ventanaEdicionCircuitos.destroy();
    },

    guardarCircuito : function()
    {
        var form = this.getFormEdicionCircuitos().getForm();

        // var circuitoId = form.findField('')
    }
});