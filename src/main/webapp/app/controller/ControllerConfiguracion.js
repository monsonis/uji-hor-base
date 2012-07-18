Ext.define('HOR.controller.ControllerConfiguracion',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreHoras' ],
    ref : [
    {
        selector : 'panelConfiguracion',
        ref : 'panelConfiguracion'
    } ],

    init : function()
    {
        this.control(
        {
            /*'panelConfiguracion filtroGrupos combobox[name=grupo]' :
            {
                select : function()
                {
                    console.log(this.getStoreHorasStore());
                }
            }*/
        });
    }
});