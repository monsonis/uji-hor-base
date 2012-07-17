Ext.define('HOR.view.horarios.ConfiguracionHorario',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.configuracionHorario',
    requires : [ 'HOR.view.horarios.SelectorHoras' ],

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
