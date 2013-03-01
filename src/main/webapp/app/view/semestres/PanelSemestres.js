Ext.define('HOR.view.semestres.PanelSemestres',
{
    extend : 'Ext.panel.Panel',
    title : 'Dates curs acadèmic',
    requires : [ 'HOR.view.semestres.GridSemestres', 'HOR.view.semestres.GridFestivos' ],
    alias : 'widget.panelSemestres',
    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'center',
        padding : 10
    },
    items : [
    {
        xtype : 'gridSemestres',
        width : 650
    },
    {
        xtype : 'gridFestivos',
        width : 250,
        margins : '20px;'
    } ]
});