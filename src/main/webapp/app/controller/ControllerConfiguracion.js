Ext.define('HOR.controller.ControllerConfiguracion',
{
    extend : 'Ext.app.Controller',
    ref : [
    {
        selector : 'panelConfiguracion',
        ref : 'panelConfiguracion'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    } ],

    init : function()
    {
        this.control(
        {
            'panelConfiguracion' :
            {
                show : this.cierraPanelCalendario
            }
        });
    },

    cierraPanelCalendario : function()
    {
        console.log('Entro???');
        if (this.getPanelCalendario() !== undefined)
        {
            this.getPanelCalendario().up().remove(this.getPanelCalendario(), true);
        }
    }
});