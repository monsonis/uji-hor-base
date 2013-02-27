Ext.define('HOR.view.semestres.GridSemestres',
{
    extend : 'Ext.grid.Panel',
    requires : [],
    store : 'StoreSemestreDetalles',
    alias : 'widget.gridSemestres',

    disableSelection : true,
    sortableColumns : false,

    columns : [
    {
        text : 'Tipo estudio',
        dataIndex : 'nombreTipoEstudio',
        menuDisabled : true,
        flex : 1
    },
    {
        text : 'Nombre Semestre',
        dataIndex : 'nombreSemestre',
        menuDisabled : true
    },
    {
        text : 'Inicio docencia',
        dataIndex : 'fechaInicio',
        menuDisabled : true,
        renderer : function(value)
        {
            return Ext.Date.format(value, "d/m/Y");
        }
    },
    {
        text : 'Fin docencia',
        dataIndex : 'fechaFin',
        menuDisabled : true,
        renderer : function(value)
        {
            return Ext.Date.format(value, "d/m/Y");
        }
    },
    {
        text : 'Inicio exámenes',
        dataIndex : 'fechaExamenesInicio',
        menuDisabled : true,
        renderer : function(value)
        {
            return Ext.Date.format(value, "d/m/Y");
        }
    },
    {
        text : 'Fin exámenes',
        dataIndex : 'fechaExamenesFin',
        menuDisabled : true,
        renderer : function(value)
        {
            return Ext.Date.format(value, "d/m/Y");
        }
    } ]

});
