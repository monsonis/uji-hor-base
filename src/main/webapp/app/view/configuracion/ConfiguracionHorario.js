Ext.define('HOR.view.configuracion.ConfiguracionHorario',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.configuracionHorario',
    requires : [ 'HOR.view.configuracion.SelectorHoras' ],

    border : false,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
    },

    items : [
    {
        xtype : 'selectorHoras'
    } ]
});
