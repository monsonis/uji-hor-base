Ext.define('HOR.view.aulas.calendar.SelectorAulas',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorAulas',
    title : 'Aules',
    autoScroll : true,
    padding : 5,
    flex : 1,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    
    limpiaGrupos: function() {
        this.removeAll();
    }
});