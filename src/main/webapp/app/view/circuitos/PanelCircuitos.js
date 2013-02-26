Ext.define('HOR.view.circuitos.PanelCircuitos',
{
    extend : 'Ext.panel.Panel',
    title : 'Ocupació d\'aules',
    requires : [ 'HOR.view.circuitos.FiltroCircuitos' ],
    alias : 'widget.panelCircuitos',
    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 5
    },

    items : [
    {
        xtype : 'filtroCircuitos',
        height : 60
    // },
    // {
    // xtype : 'panel',
    // flex : 1,
    // border : 0,
    // layout :
    // {
    // type : 'hbox',
    // align : 'stretch'
    // },
    // items : [
    // {
    // width : 150,
    // border : 0,
    // layout :
    // {
    // type : 'vbox',
    // align : 'stretch'
    // },
    // items : [
    // {
    // xtype : 'selectorCircuitos'
    // },
    // {
    // xtype : 'selectorCalendarios'
    // } ]
    // },
    // {
    // xtype : 'panelCalendarioCircuitos',
    // flex : 1
    // } ]
    } ]

});