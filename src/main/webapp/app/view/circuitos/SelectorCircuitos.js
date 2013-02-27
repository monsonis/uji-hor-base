Ext.define('HOR.view.aulas.circuitos.SelectorCircuitos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorCircuitos',
    title : 'Circuitos',
    autoScroll : true,
    padding : 5,
    flex : 1,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },

    limpiaGrupos : function()
    {
        this.removeAll();
    }
});