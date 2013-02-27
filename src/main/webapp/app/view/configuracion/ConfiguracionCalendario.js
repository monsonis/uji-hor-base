Ext.define('HOR.view.configuracion.ConfiguracionCalendario',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.configuracionCalendario',
    requires : [ 'HOR.view.configuracion.SelectorHoras' ],

    border : false,
    layout : 'anchor',
    hidden : true,

    items : [
    {
        xtype : 'selectorHoras',
        anchor : '50%',
    } ],

    buttonAlign : 'left',
    buttons : [
    {
        text : 'Guardar',
        action : 'save',
    } ]
});
