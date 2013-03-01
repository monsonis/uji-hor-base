Ext.define('HOR.view.semestres.GridFestivos',
{
    extend : 'Ext.grid.Panel',
    title: 'Dies no lectius',
    requires : [],
    store : 'StoreFestivos',
    alias : 'widget.gridFestivos',
    disableSelection : true,
    height : 200,

    columns : [
    {
        text : 'Dia',
        dataIndex : 'diaSemana',
        menuDisabled : true
    },
    {
        text : 'Data',
        dataIndex : 'fecha',
        menuDisabled : true,
        renderer : function(value)
        {
            return Ext.Date.format(value, "d/m/Y");
        }
    } ]
});
